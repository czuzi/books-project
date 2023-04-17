package training360.booksproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.booksproject.model.Author;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Author author;
    private String title;
    private String isbn;
    private int numberOfPages;
}
