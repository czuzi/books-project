package training360.booksproject.dtos.userdtos;

import lombok.Data;

@Data
public class CreateUserCommand {

    private String userName;
    private String email;
    private String password;
}
