package training360.booksproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training360.booksproject.dtos.BookDto;
import training360.booksproject.dtos.BooksConverter;
import training360.booksproject.dtos.CreateBookCommand;
import training360.booksproject.model.Author;
import training360.booksproject.model.Book;
import training360.booksproject.repositories.AuthorRepository;
import training360.booksproject.repositories.BookRepository;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;
    private AuthorRepository authorRepository;
    private BooksConverter booksConverter;

    public BookDto createBook(CreateBookCommand command) {
        Book book = new Book();
        book.setTitle(command.getTitle());
        book.setIsbn(command.getIsbn());
        book.setNumberOfPages(command.getNumberOfPages());
        for (long authorId : command.getAuthorIds()) {
            Author author = authorRepository.findById(authorId).orElseThrow(()-> new IllegalArgumentException("Invalid author id"));
            book.getAuthors().add(author);
        }
        bookRepository.save(book);
        return booksConverter.convert(book);
    }
}
