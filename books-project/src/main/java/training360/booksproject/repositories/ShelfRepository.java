package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import training360.booksproject.model.Shelf;

public interface ShelfRepository extends JpaRepository<Shelf, Long> {
}
