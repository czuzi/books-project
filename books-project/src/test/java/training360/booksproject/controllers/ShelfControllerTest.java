package training360.booksproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import training360.booksproject.dtos.shelfdtos.CreateUpdateShelfCommand;
import training360.booksproject.dtos.shelfdtos.ShelfDto;
import training360.booksproject.dtos.userdtos.CreateUserCommand;
import training360.booksproject.dtos.userdtos.UserDto;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from book_id",
        "delete from shelved_books",
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
    void getShelvesWithSearchTerm() {
        createShelves();
        List<ShelfDto> shelves = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves?shelfName=others").build(user.getId()))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ShelfDto.class)
                .returnResult().getResponseBody();

        assertThat(shelves).hasSize(1)
                .map(ShelfDto::getShelfName)
                .contains("others");
    }

    private void createShelves() {
        webClient.post()
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
                .expectStatus().isCreated()
                .expectBody(ShelfDto.class)
                .returnResult().getResponseBody();
    }
}