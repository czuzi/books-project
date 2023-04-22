package training360.booksproject.dtos.bookdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import training360.booksproject.model.Genre;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateBookCommand {

    @NotNull
    @NotBlank(message = "Validation failed")
    private String author;
    @NotNull
    @NotBlank(message = "Validation failed")
    private String title;
    @NotNull
    @NotBlank(message = "Validation failed")
    private String isbn;
    @NotNull
    private int numberOfPages;
    @NotNull
    private int yearOfPublish;
    @NotNull
    private Genre genre;
}
