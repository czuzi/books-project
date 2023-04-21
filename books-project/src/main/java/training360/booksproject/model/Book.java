package training360.booksproject.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String author;
    private String title;
    private String isbn;
    @Column(name = "number_of_pages")
    private int numberOfPages;
    @Column(name = "year_of_publish")
    private int yearOfPublish;
    @Enumerated(EnumType.STRING)
    private Genre genre;
}
