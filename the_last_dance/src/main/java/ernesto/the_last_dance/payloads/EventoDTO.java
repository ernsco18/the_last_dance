package ernesto.the_last_dance.payloads;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record EventoDTO(
        @NotBlank(message = "Inserisci il titolo")
        @Size (min = 2, max = 20 )
        String titolo,

        @NotBlank(message = "inserisci la descrizione")
        @Size (min = 2, max = 50)
        String descrizione,

        @Future(message = "Inserisci la data dell'evento")
        LocalDate data,

        @NotBlank(message = "Innserisci il luogo")
        @Size(min = 2, max = 20)
        String luogo,

        @Min(value = 10)
        @Max(value = 100)
        int postiDisponibili,

        @NotBlank(message = "inserisci l'id dell'organizzatore")
        UUID organizzatoreId

        ) {
}
