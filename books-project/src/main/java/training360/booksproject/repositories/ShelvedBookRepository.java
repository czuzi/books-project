package training360.booksproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import training360.booksproject.model.ShelvedBook;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShelvedBookRepository extends JpaRepository<ShelvedBook, Long> {

    @Query("select sb from ShelvedBook sb where (:year is null or year(sb.readDate) = :year) and sb.shelf.user.id = :userId")
    List<ShelvedBook> findAllShelvedBooksByUser(long userId, Optional<Integer> year);

    @Query("select sb " +
            "from ShelvedBook sb " +
            "where (:searchTerm is null or CONCAT(lower(sb.book.author), ' ',lower(sb.book.title))" +
            "like concat('%', lower(:searchTerm), '%'))" +
            "and sb.shelf.id = :shelfId")
    List<ShelvedBook> findAllShelvedBooksByShelf(long shelfId, Optional<String> searchTerm);

    boolean existsByBook_IdAndShelf_Id(long bookId, long ShelfId);

    @Modifying
    @Query("delete from ShelvedBook sb where sb.id = :shelvedBookId")
    void deleteShelvedBook(long shelvedBookId);
}
