package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import training360.booksproject.model.Shelf;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    @Query("select s from Shelf s where (:shelfName is null or s.shelfName like concat('%', :shelfName, '%'))")
    List<Shelf> findShelves(Optional<String> shelfName);
}
