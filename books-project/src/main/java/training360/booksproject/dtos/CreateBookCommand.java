package training360.booksproject.dtos;

import lombok.Data;

import java.util.List;

@Data
public class CreateBookCommand {

    private String author;
    private String title;
    private String isbn;
}
