package ernesto.the_last_dance.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "eventi")
@NoArgsConstructor
@Data
public class Evento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evento_id")
    @Setter(AccessLevel.NONE)
    private UUID id;

    @Column(nullable = false)
    private String titolo;
    @Column(nullable = false)
    private String descrizione;
    @Column(nullable = false)
    private LocalDate data;
    @Column(nullable = false)
    private String luogo;
    @Column(name = "posti_disponibili", nullable = false)
    private int postiDisponibili;

    @ManyToMany
    @JoinColumn(name = "organizzatore_id")
    private Utente organizzatore;

    public Evento( String titolo, LocalDate data, String descrizione, String luogo, Utente organizzatore, int postiDisponibili) {
        this.titolo = titolo;
        this.data = data;
        this.descrizione = descrizione;
        this.luogo = luogo;
        this.organizzatore = organizzatore;
        this.postiDisponibili = postiDisponibili;
    }
}
