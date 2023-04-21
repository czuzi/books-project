package training360.booksproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training360.booksproject.dtos.shelfdtos.ShelfDto;
import training360.booksproject.repositories.ShelfRepository;
import training360.booksproject.repositories.ShelvedBookRepository;

@Service
@AllArgsConstructor
public class ShelvedBookShelfService {

    private ShelfRepository shelfRepository;
    private ShelvedBookRepository shelvedBookRepository;

    public ShelfDto createShelf(){
        return null;
    }
}
