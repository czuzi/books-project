package training360.booksproject.dtos.shelfdtos;

import lombok.Data;
import training360.booksproject.model.User;

@Data
public class CreateShelfCommand {
    private String shelfName;
}
