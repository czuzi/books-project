package training360.booksproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    @ManyToMany(mappedBy = "authors")
    private List<Author> authors;
    private String title;
    private String isbn;
    @Column(name = "number_of_pages")
    private int numberOfPages;

    public Book(List<Author> authors, String title, String isbn, int numberOfPages) {
        this.authors = authors;
        this.title = title;
        this.isbn = isbn;
        this.numberOfPages = numberOfPages;
    }
}
