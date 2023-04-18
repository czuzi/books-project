package training360.booksproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import training360.booksproject.dtos.bookdtos.BookDto;
import training360.booksproject.dtos.BooksConverter;
import training360.booksproject.dtos.bookdtos.CreateBookCommand;
import training360.booksproject.dtos.bookdtos.UpdateBookCommand;
import training360.booksproject.model.Book;
import training360.booksproject.repositories.BookRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {

    private BookRepository bookRepository;
    private BooksConverter booksConverter;

    public BookDto createBook(CreateBookCommand command) {
        Book book = new Book();
        makeBookByCreateCommand(command, book);
        bookRepository.save(book);
        return booksConverter.convert(book);
    }

    public BookDto findBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Cannot find a book by this id: " + id));
        return booksConverter.convert(book);
    }

    public List<BookDto> findAllBooks() {
        List<Book> books = bookRepository.findAll();
        return booksConverter.convertBooks(books);
    }

    public BookDto updateBookById(long id, UpdateBookCommand command) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResourceAccessException("Cannot find a book by this id: " + id));
        makeBookByUpdateCommand(command, book);
        bookRepository.save(book);
        return booksConverter.convert(book);
    }

    public void deleteBookById(long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new ResourceAccessException("Cannot find a book by this id: " + id));
        bookRepository.delete(book);
    }

    private void makeBookByCreateCommand(CreateBookCommand command, Book book) {
        book.setAuthor(command.getAuthor());
        book.setTitle(command.getTitle());
        book.setIsbn(command.getIsbn());
        book.setNumberOfPages(command.getNumberOfPages());
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
        if (command.getNumberOfPages() != 0) {
            book.setNumberOfPages(command.getNumberOfPages());
        }
        if (command.getGenre() != null) {
            book.setGenre(command.getGenre());
        }
    }
}
