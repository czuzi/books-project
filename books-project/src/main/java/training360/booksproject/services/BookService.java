package training360.booksproject.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training360.booksproject.dtos.BooksConverter;
import training360.booksproject.dtos.bookdtos.BookDto;
import training360.booksproject.dtos.bookdtos.CreateBookCommand;
import training360.booksproject.dtos.bookdtos.UpdateBookCommand;
import training360.booksproject.exceptions.BookNotFoundException;
import training360.booksproject.model.Book;
import training360.booksproject.repositories.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;
    private BooksConverter booksConverter;

    @Transactional
    public BookDto createBook(CreateBookCommand command) {
        Book book = new Book();
        makeBookByCreateCommand(command, book);
        bookRepository.save(book);
        return booksConverter.convert(book);
    }

    public BookDto findBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException("Cannot find a book by this id: " + id));
        return booksConverter.convert(book);
    }

    public List<BookDto> findAllBooks(Optional<String> searchTerm) {
        List<Book> books = bookRepository.findAllBooks(searchTerm);
        return booksConverter.convertBooks(books);
    }

    @Transactional
    public BookDto updateBookById(long id, UpdateBookCommand command) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Cannot find a book by this id: " + id));
        makeBookByUpdateCommand(command, book);
        bookRepository.save(book);
        return booksConverter.convert(book);
    }

    @Transactional
    public void deleteBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new BookNotFoundException("Cannot find a book by this id: " + id));
        bookRepository.delete(book);
    }

    private void makeBookByCreateCommand(CreateBookCommand command, Book book) {
        book.setAuthor(command.getAuthor());
        book.setTitle(command.getTitle());
        book.setIsbn(command.getIsbn());
        book.setNumberOfPages(command.getNumberOfPages());
        book.setYearOfPublish(command.getYearOfPublish());
        book.setGenre(command.getGenre());
    }

    private void makeBookByUpdateCommand(UpdateBookCommand command, Book book) {
        if (command.getAuthor() != null){
            book.setAuthor(command.getAuthor());
        }
        if (command.getTitle() != null) {
            book.setTitle(command.getTitle());
        }
        if (command.getIsbn() != null) {
            book.setIsbn(command.getIsbn());
        }
        if (command.getNumberOfPages() != null) {
            book.setNumberOfPages(command.getNumberOfPages());
        }
        if (command.getYearOfPublish() != null) {
            book.setNumberOfPages(command.getNumberOfPages());
        }
        if (command.getGenre() != null) {
            book.setGenre(command.getGenre());
        }
    }
}
