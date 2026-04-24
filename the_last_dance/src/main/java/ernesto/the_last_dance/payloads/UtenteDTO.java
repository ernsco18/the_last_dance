package ernesto.the_last_dance.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public record UtenteDTO (
        @NotBlank(message = "Inserisci il nome")
                        @Size(min = 2, max = 20)
                        String nome,

                                @NotBlank(message = "Inserisci il cognome")
                                @Size(min = 2, max = 20)
                                        String cognome,

                                @Past(message = "Inserisci la data di nascita")
                                        LocalDate dataNascita,

                                @NotBlank(message = "L'email deve essere inserita")
                                @Email(message = "email non valido")
                                        String email,
                                @NotBlank(message = "La passqword deve essere inserita")
                                @Size(min = 6, max = 20)
                                        String password,

                                @NotBlank(message = "Inserisci il ruolo")
                                @Size (min = 6, max = 20)
                                        String ruolo)
{}
