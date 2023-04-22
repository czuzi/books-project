package training360.booksproject.dtos.userdtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
