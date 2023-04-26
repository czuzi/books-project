package training360.booksproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training360.booksproject.dtos.BooksConverter;
import training360.booksproject.dtos.userdtos.CreateUserCommand;
import training360.booksproject.dtos.userdtos.UpdateUserCommand;
import training360.booksproject.dtos.userdtos.UserDto;
import training360.booksproject.exceptions.UserNotFoundException;
import training360.booksproject.model.User;
import training360.booksproject.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private BooksConverter converter;

    public UserDto createUser(CreateUserCommand command) {
        User user = new User();
        user.setUsername(command.getUsername());
        user.setEmail(command.getEmail());
        user.setPassword(command.getPassword());
        userRepository.save(user);
        return converter.convert(user);
    }

    public UserDto findUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UserNotFoundException("Cannot find user with id: " + id));
        return converter.convert(user);
    }

    public List<UserDto> findAllUser(Optional<String> username) {
        List<User> users = userRepository.findAllUser(username);
        return converter.convertUsers(users);
    }

    public UserDto updateUser(long id, UpdateUserCommand command) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + id));
        if (command.getUsername() != null) {
            user.setUsername(command.getUsername());
        }
        if (command.getEmail() != null) {
            user.setEmail(command.getEmail());
        }
        if (command.getPassword() != null) {
            user.setPassword(command.getPassword());
        }
        userRepository.save(user);
        return converter.convert(user);
    }

    public void deleteUser(long id) {
        userRepository.deleteById(id);
    }
}
