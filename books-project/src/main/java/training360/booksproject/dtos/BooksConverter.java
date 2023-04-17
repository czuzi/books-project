package training360.booksproject.dtos;

import org.mapstruct.Mapper;
import training360.booksproject.model.Author;
import training360.booksproject.model.Book;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BooksConverter {

    AuthorDto convert(Author author);
    BookDto convert(Book book);
    List<AuthorDto> convert(List<Author> all);
    List<BookDto> convertBooks(List<Book> books);
}
