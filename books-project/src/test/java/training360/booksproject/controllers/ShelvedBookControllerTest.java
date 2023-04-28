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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from books_on_shelves",
        "delete from shelved_books",
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
                .bodyValue(command)
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

}