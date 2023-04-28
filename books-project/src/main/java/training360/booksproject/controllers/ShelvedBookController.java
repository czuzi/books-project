package training360.booksproject.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training360.booksproject.dtos.shelvedbookdtos.ShelvedBookDto;
import training360.booksproject.dtos.shelvedbookdtos.UpdateShelvedBookCommand;
import training360.booksproject.services.ShelvedBookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class ShelvedBookController {

    private ShelvedBookService shelvedBookService;

    @PostMapping("/{userId}/shelves/{shelfId}/books")
    @ResponseStatus(HttpStatus.CREATED)
    public ShelvedBookDto createShelvedBook(@PathVariable("userId") long userId,
                                            @PathVariable("shelfId") long shelfId,
                                            @Valid @RequestParam long bookId) {
        return shelvedBookService.createShelvedBook(userId, shelfId, bookId);
    }

    @GetMapping("/{userId}/shelves/{shelfId}/books")
    public List<ShelvedBookDto> findAllShelvedBooks(@PathVariable("userId") long userId,
                                                    @PathVariable("shelfId") long shelfId,
                                                    @RequestParam Optional<String> searchTerm) {
        return shelvedBookService.findAllShelvedBooksByShelf(userId, shelfId, searchTerm);
    }

    @GetMapping("/{userId}/shelves/{shelfId}/books/{shelvedBookId}")
    public ShelvedBookDto findShelvedBookById(@PathVariable("userId") long userId,
                                              @PathVariable("shelfId") long shelfId,
                                              @PathVariable("shelvedBookId") long shelvedBookId) {
        return shelvedBookService.findShelvedBookById(userId, shelfId, shelvedBookId);
    }

    @GetMapping("/{userId}/books")
    public List<ShelvedBookDto> findAllBooksByUser(@PathVariable long userId,
                                                   @RequestParam Optional<Integer> year) {
        return shelvedBookService.findAllShelvedBooksByUser(userId, year);
    }

    @PutMapping("/{userId}/shelves/{shelfId}/books/{shelvedBookId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ShelvedBookDto updateShelvedBook(@PathVariable("userId") long userId,
                                            @PathVariable("shelfId") long shelfId,
                                            @PathVariable("shelvedBookId") long shelvedBookId,
                                            @Valid @RequestBody UpdateShelvedBookCommand command) {
        return shelvedBookService.updateShelvedBook(userId, shelfId, shelvedBookId, command);
    }

    @DeleteMapping("/{userId}/shelves/{shelfId}/books/{shelvedBookId}")
    public void deleteShelvedBook(@PathVariable("userId") long userId,
                                  @PathVariable("shelfId") long shelfId,
                                  @PathVariable("shelvedBookId") long shelvedBookId) {
        shelvedBookService.deleteShelvedBook(userId, shelfId, shelvedBookId);
    }
}
