package training360.booksproject.exceptions;

public class ShelvedBookNotFoundException extends RuntimeException {

    public ShelvedBookNotFoundException(String message) {
        super(message);
    }
}
