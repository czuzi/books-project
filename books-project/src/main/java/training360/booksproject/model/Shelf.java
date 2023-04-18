package training360.booksproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "shelves")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "shelf_id")
    private Long shelfId;
    @Column(name = "shelf_name")
    private String shelfName;
    @ManyToOne
    private User user;
    @ManyToMany
    private List<Book> booksOnThisShelf;
}
