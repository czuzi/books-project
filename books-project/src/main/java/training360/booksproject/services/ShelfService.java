package training360.booksproject.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training360.booksproject.dtos.BooksConverter;
import training360.booksproject.dtos.shelfdtos.CreateUpdateShelfCommand;
import training360.booksproject.dtos.shelfdtos.ShelfDto;
import training360.booksproject.exceptions.CollectionNotEmptyException;
import training360.booksproject.exceptions.ShelfNotFoundException;
import training360.booksproject.exceptions.UserNotFoundException;
import training360.booksproject.model.Shelf;
import training360.booksproject.model.User;
import training360.booksproject.repositories.ShelfRepository;
import training360.booksproject.repositories.UserRepository;

import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ShelfService {

    private UserRepository userRepository;
    private ShelfRepository shelfRepository;
    private BooksConverter converter;

    @Transactional
    public ShelfDto createShelf(long userId, CreateUpdateShelfCommand command){
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Cannot find user with this id: " + userId));
        Shelf shelf = new Shelf();
        shelf.setShelfName(command.getShelfName());
        shelf.setUser(user);
        shelfRepository.save(shelf);
        return converter.convert(shelf);
    }

    @Transactional
    public ShelfDto updateShelf(long userId, long shelfId, CreateUpdateShelfCommand command) {
        validateUser(userId);
        Shelf shelf = shelfRepository.findById(shelfId).orElseThrow(() ->
                new ShelfNotFoundException("Cannot find shelf with this id: " + shelfId));
        shelf.setShelfName(command.getShelfName());
        shelfRepository.save(shelf);
        return converter.convert(shelf);
    }

    @Transactional
    public void deleteShelf(long userId, long shelfId) {
        validateUser(userId);
        Shelf shelf = shelfRepository.findById(shelfId).orElseThrow(() ->
                new ShelfNotFoundException("Cannot find shelf with this id: " + shelfId));
        if (!shelf.getShelvedBooks().isEmpty()) {
            throw new CollectionNotEmptyException("The shelf must be empty to delete");
        }
        shelfRepository.delete(shelf);
    }

    private void validateUser(long userId) {
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("Cannot find user with this id: " + userId);
        }
    }

    public Set<ShelfDto> getShelves(long userId, Optional<String> shelfName) {
        User user = userRepository.findById(userId).orElseThrow(() ->
                new UserNotFoundException("Cannot find user with this id: " + userId));
        Set<Shelf> shelves = shelfRepository.findShelves(userId, shelfName);
        return converter.convertShelves(shelves);
    }
}
