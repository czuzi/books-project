package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import training360.booksproject.model.Shelf;
import training360.booksproject.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where (:username is null or u.username like concat('%', :username, '%'))")
    List<User> findAllUser(Optional<String> username);

    @Query("SELECT s FROM User u left JOIN fetch u.userSelves s WHERE u.id = :userId AND LOWER(s.shelfName) LIKE CONCAT('%', LOWER(:shelfName), '%')")
    Set<Shelf> getUserShelves(long userId, Optional<String> shelfName);
}
