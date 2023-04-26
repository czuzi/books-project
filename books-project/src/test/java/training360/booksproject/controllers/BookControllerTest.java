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
import training360.booksproject.dtos.bookdtos.UpdateBookCommand;
import training360.booksproject.model.Genre;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from book_id",
        "delete from shelved_books",
        "delete from shelves",
        "delete from users",
        "delete from books"})
class BookControllerTest {

    @Autowired
    WebTestClient webClient;
    ProblemDetail problem;
    BookDto bookDto;

    @BeforeEach
    void init() {
        CreateBookCommand command = new CreateBookCommand("Jonathan Franzen",
                "The Corrections",
                "1234567890",
                871,
                1993,
                Genre.CONTEMPORARY);
        bookDto = webClient.post()
                .uri("api/books")
                .bodyValue(command)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();
    }

    @Test
    void testCreateBook() {
        assertNotNull(bookDto.getId());
        assertEquals(Genre.CONTEMPORARY, bookDto.getGenre());
        assertEquals("The Corrections", bookDto.getTitle());
    }

    @Test
    void testCreateBookWithInvalidData() {
        CreateBookCommand command = new CreateBookCommand("Jonathan Franzen",
                null,
                "1234567890",
                871,
                1993,
                Genre.CONTEMPORARY);
        problem = webClient.post()
                .uri("api/books")
                .bodyValue(command)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("validation/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testFindAllBooks() {
        CreateBookCommand command = new CreateBookCommand(
                "Jonathan Franzen",
                "The Corrections",
                "1234567890",
                871,
                1993,
                Genre.CONTEMPORARY);
        CreateBookCommand command2 = new CreateBookCommand(
                "Haruki Murakami",
                "1Q84",
                "12351123",
                421,
                2021,
                Genre.HORROR);
        webClient.post()
                .uri("api/books")
                .bodyValue(command)
                .exchange()
                .expectStatus().isCreated();
        webClient.post()
                .uri("api/books")
                .bodyValue(command2)
                .exchange()
                .expectStatus().isCreated();

        List<BookDto> books = webClient.get()
                .uri("api/books")
                .exchange()
                .expectBodyList(BookDto.class)
                .returnResult().getResponseBody();
        assertThat(books)
                .hasSize(3);
        List<BookDto> filteredBooks = webClient.get()
                .uri("api/books?searchTerm=Franzen")
                .exchange()
                .expectBodyList(BookDto.class)
                .returnResult().getResponseBody();
        assertThat(filteredBooks)
                .hasSize(2);
    }

    @Test
    void testFindBookById() {
        BookDto found = webClient.get()
                .uri((uriBuilder -> uriBuilder.path("api/books/{id}").build(bookDto.getId())))
                .exchange()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();
        assertEquals("The Corrections", found.getTitle());
    }

    @Test
    void testFindBookByIdWithInvalidId() {
        problem = webClient.get()
                .uri("api/books/234")
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();
        assertEquals(URI.create("books/not-found"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Cannot find a book by this id: 234"));
    }

    @Test
    void testUpdateBook() {
        BookDto updated = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/books/{id}").build(bookDto.getId()))
                .bodyValue(new UpdateBookCommand(null, null, "000888888", 0, 0, null))
                .exchange()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();
        assertEquals("000888888", updated.getIsbn());
    }

    @Test
    void deleteBook() {
        webClient.delete()
                .uri(uriBuilder -> uriBuilder.path("api/books/{id}").build(bookDto.getId()))
                .exchange();
        List<BookDto> books = webClient.get()
                .uri("api/books")
                .exchange()
                .expectBodyList(BookDto.class)
                .returnResult().getResponseBody();
        assertThat(books).hasSize(0);
    }
}