package training360.booksproject.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ProblemDetail;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;
import training360.booksproject.dtos.userdtos.CreateUserCommand;
import training360.booksproject.dtos.userdtos.UpdateUserCommand;
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
class UserControllerTest {

    @Autowired
    WebTestClient webClient;
    ProblemDetail problem;
    UserDto user;

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
    void testCreateUser() {
        assertEquals("johndoe", user.getUsername());
    }

    @Test
    void testCreateUserWithInvalidPassword() {
        problem = webClient.post()
                .uri("api/users")
                .bodyValue(new CreateUserCommand("johndoe", "johndoe@something.hu", "A123"))
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();

        assertEquals(URI.create("validation/not-valid"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Validation failed"));
    }

    @Test
    void testFindUserById() {
        UserDto found = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}").build(user.getId()))
                .exchange()
                .expectBody(UserDto.class)
                .returnResult().getResponseBody();
        assertEquals("johndoe@something.hu", found.getEmail());
    }

    @Test
    void testFindUserByIdWithInvalidId() {
        problem = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}").build(444l))
                .exchange()
                .expectBody(ProblemDetail.class)
                .returnResult().getResponseBody();
        assertEquals(URI.create("users/not-found"), problem.getType());
        assertTrue(problem.getDetail().startsWith("Cannot find user"));
    }

    @Test
    void testfindAllUser() {
        List<UserDto> users = webClient.get()
                .uri("api/users?username=john")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(UserDto.class)
                .returnResult().getResponseBody();
        assertThat(users).hasSize(1).map(UserDto::getUsername).contains("johndoe");
    }

    @Test
    void testUpdateUser() {
        UserDto updated = webClient.put()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}").build(user.getId()))
                .bodyValue(new UpdateUserCommand(null, "johndoesmith@something.hu", null))
                .exchange()
                .expectBody(UserDto.class)
                .returnResult().getResponseBody();
        assertEquals("johndoesmith@something.hu", updated.getEmail());
        UserDto found = webClient.get()
                .uri(uriBuilder -> uriBuilder.path("api/users/{id}").build(user.getId()))
                .exchange()
                .expectBody(UserDto.class)
                .returnResult().getResponseBody();
        assertEquals("johndoesmith@something.hu", found.getEmail());
    }
}