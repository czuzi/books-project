package training360.booksproject.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training360.booksproject.dtos.shelvedbookdtos.ShelvedBookDto;
import training360.booksproject.dtos.shelvedbookdtos.UpdateShelvedBookCommand;
import training360.booksproject.services.ShelvedBookService;

import java.util.List;

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

    @PutMapping("/{userId}/shelves/{shelfId}/books/{shelvedBookId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ShelvedBookDto updateShelvedBook(@PathVariable("userId") long userId,
                                            @PathVariable("shelfId") long shelfId,
                                            @PathVariable("shelvedBookId") long shelvedBookId,
                                            @Valid @RequestBody UpdateShelvedBookCommand command) {
        return shelvedBookService.updateShelvedBook(userId, shelfId, shelvedBookId, command);
    }

    @DeleteMapping("/{userId}/shelves/{shelfId}/books/{shelvedBookId}")
    public void deleteShelvedBook(@PathVariable("userId") long userid,
                                  @PathVariable("shelfId") long shelfId,
                                  @PathVariable("shelvedBookId") long shelvedBookId) {
        shelvedBookService.deleteShelvedBook(userid, shelfId, shelvedBookId);
    }

    @GetMapping("/{userId}/shelves/{shelfId}/books")
    public List<ShelvedBookDto> findAllShelvedBooks(@PathVariable("userId") long userId,
                                                    @PathVariable("shelfId") long shelfId) {
        return shelvedBookService.findAllShelvedBooks(userId, shelfId);
    }

    @GetMapping("/{userId}/shelves/{shelfId}/books/{shelvedBookId}")
    public ShelvedBookDto findShelvedBookById(@PathVariable("userId") long userId,
                                              @PathVariable("shelfId") long shelfId,
                                              @PathVariable("shelvedBookId") long shelvedBookId) {
        return shelvedBookService.findShelvedBookById(userId, shelfId, shelvedBookId);
    }
}
