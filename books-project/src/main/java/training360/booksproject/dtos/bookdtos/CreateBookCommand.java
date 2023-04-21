package training360.booksproject.dtos.bookdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import training360.booksproject.model.Genre;

@Data
public class CreateBookCommand {

    @NotNull
    @NotBlank
    private String author;
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String isbn;
    @NotNull
    @NotBlank
    private int numberOfPages;
    @NotNull
    @NotBlank
    private int yearOfPublish;
    @NotNull
    @NotBlank
    private Genre genre;
}
