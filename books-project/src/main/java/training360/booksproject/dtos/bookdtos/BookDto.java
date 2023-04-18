package training360.booksproject.dtos.bookdtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.booksproject.model.Genre;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private Long id;
    private String author;
    private String title;
    private String isbn;
    private int numberOfPages;
    private Genre genre;
}
