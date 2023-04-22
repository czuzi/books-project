package training360.booksproject.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleInvalidBookArguments(MethodArgumentNotValidException e){
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,e.getMessage());
        detail.setType(URI.create("books/not-valid"));
        return detail;
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ProblemDetail handleBookNotFound(BookNotFoundException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        detail.setType(URI.create("books/not-found"));
        return detail;
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ProblemDetail handleBookNotFound(UserNotFoundException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        detail.setType(URI.create("users/not-found"));
        return detail;
    }

    @ExceptionHandler(ShelfNotFoundException.class)
    public ProblemDetail handleBookNotFound(ShelfNotFoundException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        detail.setType(URI.create("shelf/not-found"));
        return detail;
    }

    @ExceptionHandler(ShelvedBookNotFoundException.class)
    public ProblemDetail handleBookNotFound(ShelvedBookNotFoundException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, e.getMessage());
        detail.setType(URI.create("shelved-book/not-found"));
        return detail;
    }

    @ExceptionHandler(CollectionNotEmptyException.class)
    public ProblemDetail handleBookNotFound(CollectionNotEmptyException e) {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, e.getMessage());
        detail.setType(URI.create("shelves/not-valid"));
        return detail;
    }
}
