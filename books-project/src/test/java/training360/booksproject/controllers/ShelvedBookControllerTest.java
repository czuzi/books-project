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
import training360.booksproject.dtos.shelvedbookdtos.ShelvedBookDto;
import training360.booksproject.dtos.shelvedbookdtos.UpdateShelvedBookCommand;
import training360.booksproject.dtos.userdtos.CreateUserCommand;
import training360.booksproject.dtos.userdtos.UserDto;
import training360.booksproject.model.Genre;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from shelved_books",
        "delete from shelves",
        "delete from users",
        "delete from books"})
class ShelvedBookControllerTest {

    @Autowired
    WebTestClient webClient;
    ProblemDetail problem;
    UserDto user;
    ShelfDto shelf;
    ShelvedBookDto shelvedBook;
    ShelvedBookDto shelvedBook2;
    BookDto book1;
    BookDto book2;

    @BeforeEach
    void init() {
        CreateBookCommand command = new CreateBookCommand("Jonathan Franzen",
                "The Corrections",
                "1234561234564",
                871,
                1993,
                Genre.CONTEMPORARY);
        book1 = webClient.post()
                .uri("api/books")
                .bodyValue(command)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();
        CreateBookCommand command2 = new CreateBookCommand("Veres Attila",
                "A valosag helyreallitasa",
                "1234561234564",
                421,
                2022,
                Genre.HORROR);
        book2 = webClient.post()
                .uri("api/books")
                .bodyValue(command2)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();
        user = webClient.post()
                .uri("api/users")
                .bodyValue(new CreateUserCommand("johndoe", "johndoe@something.hu", "A12as!214"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(UserDto.class)
                .returnResult().getResponseBody();
        shelf = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves").build(user.getId()))
                .bodyValue(new CreateUpdateShelfCommand("favourites"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ShelfDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testCreateShelvedBook() {
        shelvedBook = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}/books")
                        .queryParam("bookId", book1.getId())
                        .build(user.getId(), shelf.getId()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ShelvedBookDto.class)
                .returnResult().getResponseBody();

        assertEquals("Jonathan Franzen", shelvedBook.getBook().getAuthor());
    }

    @Test
    void testCreateShelvedBookAlreadyExists() {
        createShelvedBooks();
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}/books")
                        .queryParam("bookId", book1.getId())
                        .build(user.getId(), shelf.getId()))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("validation/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Book " + book1.getTitle() + " already on shelf "));
    }

    @Test
    void testCreateShelvedBookWithInvalidUserId() {
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}/books")
                        .queryParam("bookId", book1.getId())
                        .build(-1L, shelf.getId()))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();
        assertEquals(URI.create("users/not-found"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Cannot find user with id"));
    }

    @Test
    void testCreateShelvedBookWithInvalidShelfId() {
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}/books")
                        .queryParam("bookId", book1.getId())
                        .build(user.getId(), -2L))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();
        assertEquals(URI.create("shelf/not-found"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Cannot find shelf with id:"));
    }

    @Test
    void testCreateShelvedBookThatAlreadyExists() {
        createShelvedBooks();
        problem = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}/books")
                        .queryParam("bookId", book1.getId())
                        .build(user.getId(), shelf.getId()))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();
        assertEquals(URI.create("validation/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Book " + book1.getTitle() + " already on shelf"));
    }

    @Test
    void testFindShelvedBookById() {
        createShelvedBooks();
        ShelvedBookDto found = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{userId}/shelves/{shelfId}/books/{shelvedBookId}")
                        .build(user.getId(), shelf.getId(), shelvedBook.getId()))
                .exchange()
                .expectBody(ShelvedBookDto.class)
                .returnResult().getResponseBody();
        assertEquals("The Corrections", found.getBook().getTitle());
    }

    @Test
    void testFindAllBooksOnShelf() {
        createShelvedBooks();

        List<ShelvedBookDto> shelves = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}/books").
                        build(user.getId(), shelf.getId()))
                .exchange()
                .expectBodyList(ShelvedBookDto.class)
                .returnResult().getResponseBody();

        assertThat(shelves).hasSize(2)
                .map(ShelvedBookDto::getBook)
                .map(BookDto::getTitle)
                .startsWith("The Corrections");
    }

    private void createShelvedBooks() {
        shelvedBook = webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}/books")
                        .queryParam("bookId", book1.getId())
                        .build(user.getId(), shelf.getId()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ShelvedBookDto.class)
                .returnResult().getResponseBody();

        webClient.post()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}/books")
                        .queryParam("bookId", book2.getId())
                        .build(user.getId(), shelf.getId()))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(ShelvedBookDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testUpdateShelvedBook() {
        createShelvedBooks();
        webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/users/{userId}/shelves/{shelfId}/books/{shelvedBookId}").
                        build(user.getId(), shelf.getId(), shelvedBook.getId()))
                .bodyValue(new UpdateShelvedBookCommand(LocalDate.now()))
                .exchange()
                .expectStatus().isAccepted();
        ShelvedBookDto updated = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{userId}/shelves/{shelfId}/books/{shelvedBookId}")
                        .build(user.getId(), shelf.getId(), shelvedBook.getId()))
                .exchange()
                .expectBody(ShelvedBookDto.class)
                .returnResult().getResponseBody();
        assertEquals(LocalDate.now(), updated.getReadDate());
    }

    @Test
    void testDeleteShelvedBook() {
        createShelvedBooks();

        webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("api/users/{userId}/shelves/{shelfId}/books/{shelvedBookId}")
                .build(user.getId(), shelf.getId(), shelvedBook.getId()))
                .exchange()
                .expectStatus().isOk();

        List<ShelvedBookDto> shelvedBooks = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}/shelves/{shelfId}/books").
                        build(user.getId(), shelf.getId()))
                .exchange()
                .expectBodyList(ShelvedBookDto.class)
                .returnResult().getResponseBody();
        assertThat(shelvedBooks).hasSize(1)
                .map(ShelvedBookDto::getBook)
                .map(BookDto::getTitle)
                .startsWith("A valosag helyreallitasa");
    }

    @Test
    void testDeleteShelvedBookWithInvalidId() {
        problem = webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("api/users/{userId}/shelves/{shelfId}/books/{shelvedBookId}")
                        .build(user.getId(), shelf.getId(), -1L))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();
        assertEquals(URI.create("shelved-book/not-found"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Cannot find"));
    }
}