package training360.booksproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import training360.booksproject.model.Author;

import java.util.List;

@Data
public class CreateBookCommand {

    private List<Long> authorIds;
    private String title;
    private String isbn;
    private int numberOfPages;
}
