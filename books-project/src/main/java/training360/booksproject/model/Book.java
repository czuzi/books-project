package training360.booksproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    private String author;
    private String title;
    private String isbn;
    @ManyToMany
    @Column(name = "shelves_with_this_book")
    private List<Shelf> shelvesWithThisBook;
}
