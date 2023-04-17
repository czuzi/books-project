package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import training360.booksproject.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
