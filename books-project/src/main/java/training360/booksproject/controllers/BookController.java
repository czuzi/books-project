package training360.booksproject.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import training360.booksproject.dtos.bookdtos.BookDto;
import training360.booksproject.dtos.bookdtos.CreateBookCommand;
import training360.booksproject.dtos.bookdtos.UpdateBookCommand;
import training360.booksproject.services.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@Valid @RequestBody CreateBookCommand command) {
        return bookService.createBook(command);
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable long id) {
        return bookService.findBookById(id);
    }

    @GetMapping
    public List<BookDto> findAllBooks(@Valid @RequestParam Optional<String> author) {
        return bookService.findAllBooks(author);
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable long id, @RequestBody UpdateBookCommand command) {
        return bookService.updateBookById(id, command);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable long id) {
        bookService.deleteBookById(id);
    }
}
