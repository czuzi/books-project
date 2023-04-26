package training360.booksproject.dtos.bookdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import training360.booksproject.model.Genre;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBookCommand {

    @NotBlank(message = "Validation failed")
    private String author;
    @NotBlank(message = "Validation failed")
    private String title;
    @NotBlank(message = "Validation failed")
    @Size(min = 13, max = 13)
    private String isbn;
    @NotNull
    private Integer numberOfPages;
    @NotNull
    private Integer yearOfPublish;
    @NotNull
    private Genre genre;
}
