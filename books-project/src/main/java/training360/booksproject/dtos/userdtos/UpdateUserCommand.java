package training360.booksproject.dtos.userdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import training360.booksproject.validators.ValidPassword;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserCommand {

    @NotBlank(message = "username is mandatory")
    private String username;
    @NotBlank(message = "email is mandatory")
    private String email;
    @NotBlank(message = "password is mandatory")
    @ValidPassword
    private String password;
}
