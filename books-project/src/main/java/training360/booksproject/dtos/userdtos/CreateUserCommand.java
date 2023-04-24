package training360.booksproject.dtos.userdtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import training360.booksproject.validators.ValidPassword;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserCommand {

    @NotBlank(message = "username is mandatory")
    @NotNull
    @Size(min = 6, max = 16)
    private String username;
    @NotNull
    @NotBlank(message = "email is mandatory")
    @Email
    private String email;
    @NotNull
    @NotBlank(message = "password is mandatory")
    @ValidPassword
    private String password;
}
