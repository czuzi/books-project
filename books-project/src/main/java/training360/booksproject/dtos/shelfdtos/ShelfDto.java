package training360.booksproject.dtos.shelfdtos;

import lombok.*;
import training360.booksproject.model.ShelvedBook;
import training360.booksproject.model.User;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShelfDto {

    private Long id;
    private String shelfName;
    private User user;
    private Set<ShelvedBook> shelvedBooks = new HashSet<>();
}
