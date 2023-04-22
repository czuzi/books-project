package training360.booksproject.dtos.shelfdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUpdateShelfCommand {
    @NotBlank(message = "name cannot be empty")
    @NotNull
    private String shelfName;
}
