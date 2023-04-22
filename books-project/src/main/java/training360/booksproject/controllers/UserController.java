package training360.booksproject.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody CreateUserCommand command) {
        UserDto createdUser = userService.createUser(command);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @GetMapping
    public List<UserDto> findAllUsers(@Valid @RequestParam Optional<String> username) {
        return userService.findAllUser(username);
    }
    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable long id) {
        return userService.findUserById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable long id, @RequestBody UpdateUserCommand command) {
        UserDto updatedUser = userService.updateUser(id, command);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@Valid @PathVariable long id) {
        userService.deleteUser(id);
    }
}
