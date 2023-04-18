package training360.booksproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training360.booksproject.dtos.BookDto;
import training360.booksproject.dtos.BooksConverter;
import training360.booksproject.dtos.CreateBookCommand;
import training360.booksproject.repositories.BookRepository;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;
    private BooksConverter booksConverter;

    public BookDto createBook(CreateBookCommand command) {
        return null;
    }
}
