package training360.booksproject.dtos;

import org.mapstruct.Mapper;
import training360.booksproject.dtos.bookdtos.BookDto;
import training360.booksproject.dtos.shelfdtos.ShelfDto;
import training360.booksproject.dtos.shelvedbookdtos.ShelvedBookDto;
import training360.booksproject.dtos.userdtos.UserDto;
import training360.booksproject.model.Book;
import training360.booksproject.model.Shelf;
import training360.booksproject.model.ShelvedBook;
import training360.booksproject.model.User;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BooksConverter {

    UserDto convert(User user);
    BookDto convert(Book book);
    ShelfDto convert(Shelf shelf);
    ShelvedBookDto convert (ShelvedBook shelvedBook);
    List<BookDto> convertBooks(List<Book> books);
    List<ShelfDto> convertShelves(List<Shelf> shelves);
    List<UserDto> convertUsers(List<User> users);
    List<ShelvedBookDto> convertShelvedBooks(List<ShelvedBook> shelvedBooks);

}
