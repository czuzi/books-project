package training360.booksproject.dtos.shelvedbookdtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import training360.booksproject.dtos.bookdtos.BookDto;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShelvedBookDto {

    private Long id;
    private BookDto book;
    private LocalDate readDate;
    private LocalDate addDate;
}
