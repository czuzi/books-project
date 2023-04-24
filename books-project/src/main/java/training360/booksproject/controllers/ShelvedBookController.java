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

    @PostMapping("/{userId}/shelves/{shelfId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ShelvedBookDto createShelvedBook(@Valid @PathVariable("userId") long userId,
                                            @PathVariable("shelfId") long shelfId,
                                            @RequestParam long bookId) {
        return shelvedBookService.createShelvedBook(userId, shelfId, bookId);
    }

    @PutMapping("/{userId}/shelves/{shelfId}/shelfbooks/{shelvedBookId}")
    @ResponseStatus(HttpStatus.OK)
    public ShelvedBookDto updateShelvedBook(@Valid @PathVariable("userId") long userid,
                                            @PathVariable("shelfId") long shelfId,
                                            @PathVariable("shelvedBookId") long shelvedBookId,
                                            @RequestBody UpdateShelvedBookCommand command) {
        return shelvedBookService.updateShelvedBook(userid, shelfId, shelvedBookId, command);
    }

    @DeleteMapping("/{userId}/shelves/{shelfId}/shelfbooks/{shelvedBookId}")
    public void deleteShelvedBook(@Valid @PathVariable("userId") long userid,
                                  @PathVariable("shelfId") long shelfId,
                                  @PathVariable("shelvedBookId") long shelvedBookId) {
        shelvedBookService.deleteShelvedBook(userid, shelfId, shelvedBookId);
    }

    @GetMapping("/{userId}/shelves/{shelfId}/shelfbooks")
    public List<ShelvedBookDto> findAllShelvedBooks(@Valid @PathVariable("userId") long userId,
                                                    @PathVariable("shelfId") long shelfId) {
        return shelvedBookService.findAllShelvedBooks(userId, shelfId);
    }

    @GetMapping("/{userId}/shelves/{shelfId}/shelfbooks/{shelvedBookId}")
    public ShelvedBookDto findShelvedBookById(@Valid @PathVariable("userId") long userId,
                                              @PathVariable("shelfId") long shelfId,
                                              @PathVariable("shelvedBookId") long shelvedBookId) {
        return shelvedBookService.findShelvedBookById(userId, shelfId, shelvedBookId);
    }
}
