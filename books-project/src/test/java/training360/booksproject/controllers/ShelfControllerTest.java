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

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(statements = {"delete from shelves", "delete from users"})
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
//    @Test
//    void testCreateShelfProblem() {
//
//    }
}