package training360.booksproject.dtos.bookdtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import training360.booksproject.model.Genre;

@Data
public class CreateUpdateBookCommand {

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
    private int numberOfPages;
    @NotNull
    private int yearOfPublish;
    @NotNull
    private Genre genre;
}
