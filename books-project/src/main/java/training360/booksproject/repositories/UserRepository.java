package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import training360.booksproject.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
