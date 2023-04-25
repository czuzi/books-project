package training360.booksproject.dtos.shelfdtos;

import jakarta.validation.constraints.NotBlank;
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
    private String shelfName;
}
