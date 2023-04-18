package training360.booksproject.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import training360.booksproject.model.User;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShelfDto {

    private Long id;
    private String shelfName;
    private User user;
}
