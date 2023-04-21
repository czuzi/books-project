package training360.booksproject.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "shelves")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shelf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "shelf_name")
    private String shelfName;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "shelf", cascade = CascadeType.ALL)
    private Set<ShelvedBook> shelvedBooks;
}
