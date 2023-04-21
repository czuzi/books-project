package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import training360.booksproject.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
