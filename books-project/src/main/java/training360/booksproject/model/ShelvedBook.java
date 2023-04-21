package training360.booksproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "shelved_books")
public class ShelvedBook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinTable(name = "book_id")
    private Book book;
    @Column(name = "read_data")
    private LocalDate readDate;
    @Column(name = "add_date")
    private LocalDate addDate;
    @ManyToOne
    private Shelf shelf;
}
