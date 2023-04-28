package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import training360.booksproject.model.Shelf;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
    @Query("SELECT s FROM Shelf s WHERE (:shelfName is null or lower(s.shelfName) like CONCAT('%', lower(:shelfName), '%')) and s.user.id = :userId")
    List<Shelf> findShelves(long userId, Optional<String> shelfName);
}
