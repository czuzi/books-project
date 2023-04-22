package training360.booksproject.dtos.shelvedbookdtos;

import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateShelvedBookCommand {

    @PastOrPresent
    private LocalDate readDate;
}
