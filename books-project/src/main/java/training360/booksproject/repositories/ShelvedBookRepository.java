package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import training360.booksproject.model.ShelvedBook;

@Repository
public interface ShelvedBookRepository extends JpaRepository<ShelvedBook, Long> {

//    @Query("select sb from ShelvedBook sb where :searchTerm is null or CONCAT(lower(sb.book.author), ' ',lower(sb.book.title)) like concat('%', lower(:searchTerm), '%') and sb.shelf.id = :shelfId and sb.book.id = :bookId")
//    List<ShelvedBook> findAllShelvesBooks(long userId, long shelfId, Optional<String> searchTerm);
}
