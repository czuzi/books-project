package training360.booksproject.dtos.userdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateUserCommand {

    @NotBlank(message = "username is mandatory")
    @NotNull
    private String username;
    @NotBlank(message = "email is mandatory")
    @NotNull
    private String email;
}
