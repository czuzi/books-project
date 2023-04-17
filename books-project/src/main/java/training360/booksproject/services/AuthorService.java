package training360.booksproject.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import training360.booksproject.dtos.AuthorDto;
import training360.booksproject.dtos.BooksConverter;
import training360.booksproject.dtos.CreateAuthorCommand;
import training360.booksproject.model.Author;
import training360.booksproject.repositories.AuthorRepository;
import training360.booksproject.repositories.BookRepository;

@Service
@AllArgsConstructor
public class AuthorService {

    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    private BooksConverter booksConverter;

    public AuthorDto createAuthor(CreateAuthorCommand command) {
        Author author = new Author();
        author.setName(command.getName());
        authorRepository.save(author);
        return booksConverter.convert(author);
    }
}
