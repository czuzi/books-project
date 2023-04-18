package training360.booksproject.dtos;

import lombok.Data;

@Data
public class UpdateUserCommand {

    private String userName;
    private String email;
}
