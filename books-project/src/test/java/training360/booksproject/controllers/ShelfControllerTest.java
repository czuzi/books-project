package training360.booksproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import training360.booksproject.dtos.bookdtos.BookDto;
import training360.booksproject.dtos.bookdtos.CreateBookCommand;
import training360.booksproject.dtos.shelfdtos.CreateUpdateShelfCommand;
import training360.booksproject.dtos.shelfdtos.ShelfDto;
import training360.booksproject.dtos.userdtos.CreateUserCommand;
import training360.booksproject.dtos.userdtos.UserDto;
import training360.booksproject.model.Genre;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from shelved_books",
        "delete from shelves",
        "delete from users",
        "delete from books"})
class ShelfControllerTest {

    @Autowired
    WebTestClient webClient;
    ProblemDetail problem;
    UserDto user;
    ShelfDto shelf;

    @BeforeEach
    void init() {
        user = webClient.post()
                .uri("api/users")
                .bodyValue(new CreateUserCommand("johndoe", "johndoe@something.hu", "A12as!214"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testCreateShelf() {
        shelf = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves").build(user.getId()))
                .bodyValue(new CreateUpdateShelfCommand("favourites"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ShelfDto.class)
                .returnResult().getResponseBody();

        assertEquals("favourites", shelf.getShelfName());
    }
    @Test
    void testCreateShelfWithInvalidData() {
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves").build(user.getId()))
                .bodyValue(new CreateUpdateShelfCommand(""))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();
        assertEquals(URI.create("validation/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testCreateShelfThatAlreadyExists() {
        createShelves();
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves").build(user.getId()))
                .bodyValue(new CreateUpdateShelfCommand("others"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();
        assertEquals(URI.create("validation/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Shelf name already taken"));

        UserDto user2 = webClient.post()
                .uri("api/users")
                .bodyValue(new CreateUserCommand("janedoe", "janedoe@something.hu", "A12as!214"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class)
                .returnResult().getResponseBody();
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves").build(user2.getId()))
                .bodyValue(new CreateUpdateShelfCommand("others"))
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    void getShelves() {
        createShelves();
        UserDto user2 = webClient.post()
                .uri("api/users")
                .bodyValue(new CreateUserCommand("janedoe", "janedoe@something.hu", "A12as!214"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class)
                .returnResult().getResponseBody();
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves").build(user2.getId()))
                .bodyValue(new CreateUpdateShelfCommand("horrors"))
                .exchange()
                .expectStatus().isCreated();
        List<ShelfDto> shelves = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves").build(user.getId()))
                .exchange()
                .expectBodyList(ShelfDto.class)
                .returnResult().getResponseBody();

        assertThat(shelves).hasSize(2)
                .map(ShelfDto::getShelfName)
                .contains("favourites");
    }

    @Test
    void testDeleteShelf() {
        createShelves();
        webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}").build(user.getId(), shelf.getId()))
                .exchange()
                .expectStatus().isOk();
        List<ShelfDto> shelves = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves").build(user.getId()))
                .exchange()
                .expectBodyList(ShelfDto.class)
                .returnResult().getResponseBody();
        assertThat(shelves).hasSize(1)
                .map(ShelfDto::getShelfName)
                .startsWith("others");
    }

    @Test
    void testDeleteNotEmptyShelf() {
        createShelves();
        CreateBookCommand command = new CreateBookCommand("Jonathan Franzen",
                "The Corrections",
                "1234561234564",
                871,
                1993,
                Genre.CONTEMPORARY);
        BookDto book = webClient.post()
                .uri("api/books")
                .bodyValue(command)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();
        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}/books")
                        .queryParam("bookId", book.getId())
                        .build(user.getId(), shelf.getId()))
                .exchange()
                .expectStatus().isCreated();
        problem = webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}").build(user.getId(), shelf.getId()))
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();
        assertEquals(URI.create("shelves/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("The shelf must be empty to delete"));
    }

    @Test
    void testDeleteShelfWithInvalidId() {
        problem = webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}").build(user.getId(), -1L))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();
        assertEquals(URI.create("shelf/not-found"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Cannot find shelf with"));
    }

    @Test
    void testFindShelvesBySearchTerm() {
        createShelves();
        List<ShelfDto> shelves = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves")
                        .queryParam("shelfName", "oth")
                        .build(user.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShelfDto.class)
                .returnResult().getResponseBody();
        assertThat(shelves).hasSize(1)
                .map(ShelfDto::getShelfName)
                .startsWith("others");

    }

    @Test
    void testUpdateShelf() {
        createShelves();
        webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}").build(user.getId(), shelf.getId()))
                .bodyValue(new CreateUpdateShelfCommand("some good books"))
                .exchange()
                .expectStatus().isAccepted();
        ShelfDto updated = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{userId}/shelves/{shelfId}").build(user.getId(), shelf.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBody(ShelfDto.class)
                .returnResult().getResponseBody();
        assertEquals("some good books", updated.getShelfName());
    }

    private void createShelves() {
        shelf = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves").build(user.getId()))
                .bodyValue(new CreateUpdateShelfCommand("favourites"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ShelfDto.class)
                .returnResult().getResponseBody();

        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves").build(user.getId()))
                .bodyValue(new CreateUpdateShelfCommand("others"))
                .exchange()
                .expectStatus().isCreated();
    }
}