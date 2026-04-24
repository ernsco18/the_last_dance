package ernesto.the_last_dance.repositories;

import ernesto.the_last_dance.entities.Prenotazione;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, UUID> {
    @Query("SELECT p FROM Prenotazione p WHERE p.utente.id = :id AND p.evento.id = :id")
    List<Prenotazione> findByUtenteEdEvento(UUID eventoId, UUID utenteId);

    @Query("SELECT p FROM Prenotazione p WHERE p.evento.id = :id")
    List<Prenotazione> findAllByEvento(UUID eventoId);

    @Query("SELECT p FROM Prenotazione p WHERE p.utente.id = :id")
    Page<Prenotazione> findAllByUtente(UUID id, Pageable pageable);
}
