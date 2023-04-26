package training360.booksproject.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training360.booksproject.dtos.shelfdtos.CreateUpdateShelfCommand;
import training360.booksproject.dtos.shelfdtos.ShelfDto;
import training360.booksproject.services.ShelfService;
import training360.booksproject.services.ShelvedBookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class ShelfController {

    private ShelfService shelfService;
    private ShelvedBookService shelvedBookService;

    @PostMapping("/{userId}/shelves")
    @ResponseStatus(HttpStatus.CREATED)
    public ShelfDto createShelf(@PathVariable long userId, @Valid @RequestBody CreateUpdateShelfCommand command) {
        return shelfService.createShelf(userId, command);
    }

    @GetMapping("/{userId}/shelves")
    public List<ShelfDto> getShelves(@PathVariable("userId") long userId, @RequestParam Optional<String> shelfName) {
        return shelfService.getShelves(userId, shelfName);
    }

    @PutMapping("/{userId}/shelves/{shelfId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ShelfDto updateShelf(@Valid @PathVariable("userId") long userId,
                                @PathVariable("shelfId")long shelfId,
                                @Valid @RequestBody CreateUpdateShelfCommand command) {
        return shelfService.updateShelf(userId, shelfId, command);
    }

    @DeleteMapping("/{userId}/shelves/{shelfId}")
    public void deleteShelf(@Valid @PathVariable("userId") long userId, @PathVariable("shelfId") long shelfId) {
        shelfService.deleteShelf(userId, shelfId);
    }
}
