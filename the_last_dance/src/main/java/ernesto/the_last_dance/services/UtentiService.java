package ernesto.the_last_dance.services;

import ernesto.the_last_dance.controllers.UtentiController;
import ernesto.the_last_dance.entities.Ruolo;
import ernesto.the_last_dance.entities.Utente;
import ernesto.the_last_dance.exceptions.BadRequestException;
import ernesto.the_last_dance.exceptions.NotFoundException;
import ernesto.the_last_dance.payloads.UtenteDTO;
import ernesto.the_last_dance.repositories.UtentiRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class UtentiService {
    private final UtentiRepository utentiRepository;
    private final PasswordEncoder passwordEncoder;

    public UtentiService(UtentiRepository utentiRepository, PasswordEncoder passwordEncoder) {
        this.utentiRepository = utentiRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Utente saveUtente(UtenteDTO body){
        if (body.dataNascita().isAfter(LocalDate.now())) throw new BadRequestException("La data di nascita e' errata");
        if (this.utentiRepository.existsByEmail(body.email())) throw new BadRequestException("Esiste già un account con questa email");
        Ruolo ruolo;
        if (body.ruolo().equals("utente")){
            ruolo = Ruolo.UTENTE;
        } else if (body.ruolo().equals("organizzatore")) {
            ruolo = Ruolo.ORGANIZZATORE;
        } else {
            throw new BadRequestException("Il ruolo può essere organizzatore o utente");
        }
        Utente nuovoUtente = new Utente(body.nome(), body.cognome(), body.email(), passwordEncoder.encode(body.password()), ruolo, body.dataNascita());
        this.utentiRepository.save(nuovoUtente);
        System.out.println("Utente salvato");
        return nuovoUtente;
    }

    public Utente findById (UUID utenteId){
        return this.utentiRepository.findById(utenteId).orElseThrow(() -> new NotFoundException(utenteId));
    }
}
