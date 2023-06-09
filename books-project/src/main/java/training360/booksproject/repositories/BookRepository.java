package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import training360.booksproject.model.Book;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("select b from Book b where :searchTerm is null or concat(lower(b.author), ' ',lower(b.title)) like concat('%', lower(:searchTerm), '%')")
    List<Book> findAllBooks(Optional<String> searchTerm);
}
