package training360.booksproject.controllers;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import training360.booksproject.dtos.bookdtos.BookDto;
import training360.booksproject.dtos.bookdtos.CreateBookCommand;
import training360.booksproject.dtos.bookdtos.UpdateBookCommand;
import training360.booksproject.services.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @PostMapping
    public BookDto createBook(@RequestBody CreateBookCommand command) {
        return bookService.createBook(command);
    }

    @GetMapping("/{bookId}")
    public BookDto getBookById(@PathVariable long bookId) {
        return bookService.findBookById(bookId);
    }

    @GetMapping
    public List<BookDto> findAllBooks() {
        return bookService.findAllBooks();
    }

    @PutMapping("/{id}")
    public BookDto updateBook(@PathVariable long id, @RequestBody UpdateBookCommand command) {
        return bookService.updateBookById(id, command);
    }
}
