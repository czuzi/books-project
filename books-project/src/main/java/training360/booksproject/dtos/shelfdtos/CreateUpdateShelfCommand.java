package training360.booksproject.dtos.shelfdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUpdateShelfCommand {
    @NotBlank(message = "name cannot be empty")
    @NotNull
    private String shelfName;
}
