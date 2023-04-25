package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import training360.booksproject.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from User u where (:username is null or u.username like concat('%', :username, '%'))")
    List<User> findAllUser(Optional<String> username);

//    @Query("select distinct u from User u left join fetch u.userSelves where u.id = :id")
//    List<Shelf> getAllShelvesByUser(long id);
}
