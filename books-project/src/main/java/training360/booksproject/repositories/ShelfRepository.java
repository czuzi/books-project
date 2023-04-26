package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import training360.booksproject.model.Shelf;

import java.util.Optional;
import java.util.Set;

@Repository
public interface ShelfRepository extends JpaRepository<Shelf, Long> {
//    @Query("select s from Shelf s where (:shelfName is null or s.shelfName like concat('%', :shelfName, '%') and s.user.id = :userId)")
    @Query("SELECT s FROM User u left JOIN fetch u.userSelves s WHERE u.id = :userId AND LOWER(s.shelfName) LIKE CONCAT('%', LOWER(:shelfName), '%')")
    Set<Shelf> findShelves(long userId, Optional<String> shelfName);
}
