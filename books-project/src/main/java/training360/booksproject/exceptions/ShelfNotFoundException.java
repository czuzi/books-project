package training360.booksproject.exceptions;

public class ShelfNotFoundException extends RuntimeException{

    public ShelfNotFoundException(String message) {
        super(message);
    }
}
