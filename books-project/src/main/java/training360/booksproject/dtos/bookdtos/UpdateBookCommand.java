package training360.booksproject.dtos.bookdtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import training360.booksproject.model.Genre;

@Getter
@Setter
public class UpdateBookCommand {

    private String author;
    private String title;
    private String isbn;
    private int numberOfPages;
    private int yearOfPublish;
    private Genre genre;
}
