package training360.booksproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import training360.booksproject.dtos.CreateUserCommand;
import training360.booksproject.dtos.UpdateUserCommand;
import training360.booksproject.dtos.UserDto;
import training360.booksproject.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserCommand command) {
        UserDto createdUser = userService.createUser(command);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    @GetMapping
    public List<UserDto> findAllUsers() {
        return userService.findAllUser();
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
}
