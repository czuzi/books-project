package training360.booksproject.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training360.booksproject.dtos.BooksConverter;
import training360.booksproject.dtos.shelvedbookdtos.ShelvedBookDto;
import training360.booksproject.dtos.shelvedbookdtos.UpdateShelvedBookCommand;
import training360.booksproject.exceptions.*;
import training360.booksproject.model.Book;
import training360.booksproject.model.Shelf;
import training360.booksproject.model.ShelvedBook;
import training360.booksproject.model.User;
import training360.booksproject.repositories.BookRepository;
import training360.booksproject.repositories.ShelfRepository;
import training360.booksproject.repositories.ShelvedBookRepository;
import training360.booksproject.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

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
        validateUserAndShelf(userId, shelfId);
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new BookNotFoundException("Cannot find book with id: " + bookId));
        Shelf shelf = shelfRepository.findById(shelfId).orElseThrow(() ->
                new ShelfNotFoundException("Cannot find shelf with id: " + shelfId));
        if (shelvedBookRepository.existsByBook_IdAndShelf_Id(bookId, shelfId)) {
            throw new AlreadyExistsException("Book " + book.getTitle() + " already on shelf " + shelf.getShelfName());
        }
        ShelvedBook shelvedBook = new ShelvedBook();
        shelvedBook.setBook(book);
        shelvedBook.setShelf(shelf);
        shelvedBook.setAddDate(LocalDate.now());
        shelvedBookRepository.save(shelvedBook);
        return converter.convert(shelvedBook);
    }

    @Transactional
    public ShelvedBookDto updateShelvedBook(long userId, long shelfId, long shelvedBookId, UpdateShelvedBookCommand command) {
        validateUserAndShelf(userId, shelfId);
        ShelvedBook shelvedBook = shelvedBookRepository.findById(shelvedBookId).orElseThrow(() ->
                new ShelvedBookNotFoundException("Cannot find shelved book with id: " + shelvedBookId));
        shelvedBook.setReadDate(command.getReadDate());
        shelvedBookRepository.save(shelvedBook);
        return converter.convert(shelvedBook);
    }

    private void validateUserAndShelf(long userId, long shelfId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + userId));
        user.getUserSelves().stream()
                .map(Shelf::getId)
                .findAny()
                .orElseThrow(() -> new ShelfNotFoundException("Cannot find shelf with id: " + shelfId + " for user: " +user.getUsername()));
    }

    @Transactional
    public void deleteShelvedBook(long userId, long shelfId, long shelvedBookId) {
        validateUserAndShelf(userId, shelfId);
        ShelvedBook shelvedBook = shelvedBookRepository.findById(shelvedBookId)
                        .orElseThrow(() ->
                                new ShelvedBookNotFoundException("Cannot find book with id " + shelvedBookId + "on shelf " + shelfId));
        shelvedBook.setBook(null);
        shelvedBook.setShelf(null);
        shelvedBookRepository.deleteShelvedBook(shelvedBookId);
    }

    public List<ShelvedBookDto> findAllShelvedBooksByShelf(long userId, long shelfId, Optional<String> searchTerm) {
        validateUserAndShelf(userId, shelfId);
        List<ShelvedBook> books = shelvedBookRepository.findAllShelvedBooksByShelf(shelfId, searchTerm);
        return converter.convertShelvedBooks(books);
    }

    public ShelvedBookDto findShelvedBookById(long userId, long shelfId, long shelvedBookId) {
        validateUserAndShelf(userId, shelfId);
        ShelvedBook book = shelvedBookRepository.findById(shelvedBookId).orElseThrow(() ->
                new ShelvedBookNotFoundException("Cannot find book on this shelf with id: " + shelvedBookId));
        return converter.convert(book);
    }

    public List<ShelvedBookDto> findAllShelvedBooksByUser(long userId, Optional<Integer> year) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("Cannot find user with id: " + userId);
        }
        List<ShelvedBook> books = shelvedBookRepository.findAllShelvedBooksByUser(userId, year);
        return converter.convertShelvedBooks(books);
    }
}
