package ernesto.the_last_dance.entities;

import jakarta.persistence.*;
import jdk.jfr.Event;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "prenotazioni")
@NoArgsConstructor
@Data
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prenotazioni_id")
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(name = "data_prenotazioni", nullable = false)
    private LocalDateTime dataPrenotazioni;

    @ManyToOne
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @ManyToOne
    @JoinColumn(name = "utente_id", nullable = false)
    private Utente utente;

    public Prenotazione(LocalDateTime dataPrenotazioni, Evento evento, Utente utente) {
        this.dataPrenotazioni = dataPrenotazioni;
        this.evento = evento;
        this.utente = utente;
    }
}
