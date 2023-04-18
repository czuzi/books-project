package training360.booksproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import training360.booksproject.dtos.BooksConverter;
import training360.booksproject.dtos.CreateUserCommand;
import training360.booksproject.dtos.UpdateUserCommand;
import training360.booksproject.dtos.UserDto;
import training360.booksproject.model.User;
import training360.booksproject.repositories.UserRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private BooksConverter converter;

    public UserDto createUser(CreateUserCommand command) {
        User user = new User();
        user.setUserName(command.getUserName());
        user.setEmail(command.getEmail());
        user.setPassword(command.getPassword());
        userRepository.save(user);
        return converter.convert(user);
    }

    public UserDto findUserById(long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceAccessException("Cannot find user with id: " + id));
        return converter.convert(user);
    }

    public List<UserDto> findAllUser() {
        List<User> users = userRepository.findAll();
        return converter.convertUsers(users);
    }

    public UserDto updateUser(long id, UpdateUserCommand command) {
        User user = userRepository.getReferenceById(id);
        if (command.getUserName() != null) {
            user.setUserName(command.getUserName());
        }
        if (command.getEmail() != null) {
            user.setEmail(command.getEmail());
        }
        return converter.convert(user);
    }
}
