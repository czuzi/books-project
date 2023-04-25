package training360.booksproject.dtos.bookdtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import training360.booksproject.model.Genre;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateBookCommand {

    private String author;
    private String title;
    private String isbn;
    private Integer numberOfPages;
    private Integer yearOfPublish;
    private Genre genre;
}
