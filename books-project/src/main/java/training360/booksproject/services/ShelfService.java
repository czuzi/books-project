package training360.booksproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training360.booksproject.dtos.shelfdtos.ShelfDto;
import training360.booksproject.repositories.ShelfRepository;

@Service
@AllArgsConstructor
public class ShelfService {

    private ShelfRepository shelfRepository;

    public ShelfDto createShelf(){
        return null;
    }
}
