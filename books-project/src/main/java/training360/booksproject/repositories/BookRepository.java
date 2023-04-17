package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import training360.booksproject.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
}
