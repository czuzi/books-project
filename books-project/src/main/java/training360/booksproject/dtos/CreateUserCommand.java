package training360.booksproject.dtos;

import lombok.Data;

@Data
public class CreateUserCommand {

    private String userName;
    private String email;
}
