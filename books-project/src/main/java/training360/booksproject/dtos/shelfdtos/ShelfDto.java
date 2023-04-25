package training360.booksproject.dtos.shelfdtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import training360.booksproject.model.ShelvedBook;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShelfDto {

    private Long id;
    private String shelfName;
    private Set<ShelvedBook> shelvedBooks = new HashSet<>();
}
