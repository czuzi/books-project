package training360.booksproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import training360.booksproject.dtos.bookdtos.BookDto;
import training360.booksproject.dtos.bookdtos.CreateUpdateBookCommand;
import training360.booksproject.dtos.shelfdtos.ShelfDto;
import training360.booksproject.model.Genre;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from shelves"})
class ShelfControllerTest {

    @Autowired
    WebTestClient webClient;
    ProblemDetail problem;
    ShelfDto shelfDto;
    BookDto book1;
    BookDto book2;

    @BeforeEach
    void init() {
        CreateUpdateBookCommand command = new CreateUpdateBookCommand("Jonathan Franzen",
                "The Corrections",
                "1234567890",
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
        CreateUpdateBookCommand command2 = new CreateUpdateBookCommand("Veres Attila",
                "A valosag helyreallitasa",
                "222333444",
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
    }


}