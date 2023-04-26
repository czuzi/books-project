package training360.booksproject.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training360.booksproject.dtos.userdtos.CreateUserCommand;
import training360.booksproject.dtos.userdtos.UpdateUserCommand;
import training360.booksproject.dtos.userdtos.UserDto;
import training360.booksproject.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(@Valid @RequestBody CreateUserCommand command) {
        return userService.createUser(command);
    }
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> findAllUsers(@Valid @RequestParam Optional<String> username) {
        return userService.findAllUser(username);
    }
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findUserById(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public UserDto updateUser(@PathVariable long id, @RequestBody UpdateUserCommand command) {
        return userService.updateUser(id, command);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@Valid @PathVariable long id) {
        userService.deleteUser(id);
    }
}
