package training360.booksproject.dtos.bookdtos;

import lombok.Data;
import training360.booksproject.model.Genre;

@Data
public class UpdateBookCommand {

    private String author;
    private String title;
    private String isbn;
    private int numberOfPages;
    private Genre genre;
}
