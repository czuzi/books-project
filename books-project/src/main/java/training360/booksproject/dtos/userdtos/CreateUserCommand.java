package training360.booksproject.dtos.userdtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import training360.booksproject.validators.ValidPassword;

@Data
public class CreateUserCommand {

    @NotBlank(message = "username is mandatory")
    @NotNull
    private String userName;
    @NotNull
    @NotBlank(message = "email is mandatory")
    @Email
    private String email;
    @NotNull
    @NotBlank
    @ValidPassword
    private String password;
}
