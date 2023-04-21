package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import training360.booksproject.model.Shelf;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
}
