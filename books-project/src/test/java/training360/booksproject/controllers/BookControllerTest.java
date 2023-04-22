package training360.booksproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.reactive.server.WebTestClient;
import training360.booksproject.dtos.bookdtos.BookDto;
import training360.booksproject.dtos.bookdtos.CreateUpdateBookCommand;
import training360.booksproject.model.Genre;

import java.net.URI;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {

    @Autowired
    WebTestClient webClient;
    ProblemDetail problem;
    BookDto bookDto;

    @BeforeEach
    void init() {
        CreateUpdateBookCommand command = new CreateUpdateBookCommand("Jonathan Franzen",
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
        CreateUpdateBookCommand command = new CreateUpdateBookCommand("Jonathan Franzen",
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

        assertEquals(URI.create("books/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testFindAllBooks() {
        CreateUpdateBookCommand command = new CreateUpdateBookCommand("Jonathan Franzen",
                "The Corrections",
                "1234567890",
                871,
                1993,
                Genre.CONTEMPORARY);
        CreateUpdateBookCommand command2 = new CreateUpdateBookCommand("Haruki Murakami",
                "1Q84",
                "12351123",
                421,
                2021,
                Genre.HORROR);
        webClient.post()
                .uri("api/books")
                .bodyValue(command)
                .exchange();
        webClient.post()
                .uri("api/books")
                .bodyValue(command2)
                .exchange();

        List<BookDto> books = webClient.get()
                .uri("api/books")
                .exchange()
                .expectBodyList(BookDto.class)
                .returnResult().getResponseBody();
        assertThat(books)
                .hasSize(3);
        List<BookDto> filteredBooks = webClient.get()
                .uri("api/books?author=Jonathan Franzen")
                .exchange()
                .expectBodyList(BookDto.class)
                .returnResult().getResponseBody();
        assertThat(filteredBooks)
                .hasSize(2);
    }

    @Test
    void testFindBookById() {
        BookDto found = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/books/{id}").build(bookDto.getId()))
                .exchange()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();
        assertEquals("The Corrections", found.getTitle());
    }

    @Test
    void testUpdateBook() {
        BookDto updated = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/books/{id}").build(bookDto.getId()))
                .bodyValue(new CreateUpdateBookCommand(null, null, "000888888", 0, 0, null))
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