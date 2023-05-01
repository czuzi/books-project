package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import training360.booksproject.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where :username is null or u.username like %:username%")
    List<User> findAllUser(Optional<String> username);

    boolean existsUserByUsername(String username);

    boolean existsUserByEmail(String email);

//    @Query("SELECT s FROM User u left JOIN fetch u.userSelves s WHERE u.id = :userId AND LOWER(s.shelfName) LIKE CONCAT('%', LOWER(:shelfName), '%')")
//    Set<Shelf> getUserShelves(long userId, Optional<String> shelfName);
}
