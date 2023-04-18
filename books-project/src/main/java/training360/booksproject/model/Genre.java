package training360.booksproject.model;

public enum Genre {

    HORROR("horror"), ROMANCE("romance"), CRIME("crime"), CONTEMPORARY("contemporary");

    private String genre;

    Genre(String genre) {
        this.genre = genre;
    }
}
