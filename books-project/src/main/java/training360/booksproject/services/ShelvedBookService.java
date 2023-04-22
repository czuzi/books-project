package training360.booksproject.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training360.booksproject.dtos.BooksConverter;
import training360.booksproject.dtos.shelvedbookdtos.ShelvedBookDto;
import training360.booksproject.dtos.shelvedbookdtos.UpdateShelvedBookCommand;
import training360.booksproject.exceptions.BookNotFoundException;
import training360.booksproject.exceptions.ShelfNotFoundException;
import training360.booksproject.exceptions.ShelvedBookNotFoundException;
import training360.booksproject.exceptions.UserNotFoundException;
import training360.booksproject.model.Book;
import training360.booksproject.model.Shelf;
import training360.booksproject.model.ShelvedBook;
import training360.booksproject.repositories.BookRepository;
import training360.booksproject.repositories.ShelfRepository;
import training360.booksproject.repositories.ShelvedBookRepository;
import training360.booksproject.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ShelvedBookService {

    private UserRepository userRepository;
    private ShelvedBookRepository shelvedBookRepository;
    private BookRepository bookRepository;
    private ShelfRepository shelfRepository;
    private BooksConverter converter;

    @Transactional
    public ShelvedBookDto createShelvedBook(long userId, long shelfId, long bookId) {
        validateUser(userId);
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("Cannot find book with id: " + bookId));
        Shelf shelf = shelfRepository.findById(shelfId).orElseThrow(() ->
                new ShelfNotFoundException("Cannot find shelf with id: " + shelfId));
        ShelvedBook shelvedBook = new ShelvedBook();
        shelvedBook.setBook(book);
        shelvedBook.setShelf(shelf);
        shelvedBook.setAddDate(LocalDate.now());
        shelvedBookRepository.save(shelvedBook);
        return converter.convert(shelvedBook);
    }

    @Transactional
    public ShelvedBookDto updateShelvedBook(long userid, long shelfId, long shelvedBookId, UpdateShelvedBookCommand command) {
        validateUser(userid);
        validateShelf(shelfId);
        ShelvedBook shelvedBook = shelvedBookRepository.findById(shelfId).orElseThrow(() ->
                new ShelvedBookNotFoundException("Cannot find shelved book with id: " + shelvedBookId));
        shelvedBook.setReadDate(command.getReadDate());
        shelvedBookRepository.save(shelvedBook);
        return converter.convert(shelvedBook);
    }

    private void validateUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("Cannot find user with id: " + userId);
        }
    }

    private void validateShelf(long shelfId) {
        if (!shelfRepository.existsById(shelfId)) {
            throw new ShelfNotFoundException("Cannot find shelf with id: " + shelfId);
        }
    }

    private void validateShelvedBook(long shelvedBookId) {
        if (!shelvedBookRepository.existsById(shelvedBookId)) {
            throw new ShelfNotFoundException("Cannot find book on this shelf with id: " + shelvedBookId);
        }
    }

    @Transactional
    public void deleteShelvedBook(long userId, long shelfId, long shelvedBookId) {
        validateUser(userId);
        validateShelf(shelfId);
        validateShelvedBook(shelvedBookId);
        shelvedBookRepository.deleteById(shelvedBookId);
    }

    public List<ShelvedBookDto> findAllShelvedBooks(long userId, long shelfId) {
        validateUser(userId);
        validateShelf(shelfId);
        return converter.convertShelvedBooks(shelvedBookRepository.findAll());
    }

    public ShelvedBookDto findShelvedBookById(long userId, long shelfId, long shelvedBookId) {
        validateUser(userId);
        validateShelf(shelfId);
        ShelvedBook book = shelvedBookRepository.findById(shelvedBookId).orElseThrow(() ->
                new ShelvedBookNotFoundException("Cannot find book on this shelf with id: " + shelvedBookId));
        return converter.convert(book);
    }
}
