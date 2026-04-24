package ernesto.the_last_dance.payloads;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

public record PrenotazioneDTO (
        @NotBlank(message = "inserisci id evento")
        UUID eventoId
){
}
