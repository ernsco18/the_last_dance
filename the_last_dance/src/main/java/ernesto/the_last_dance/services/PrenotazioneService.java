package ernesto.the_last_dance.services;

import ernesto.the_last_dance.entities.Evento;
import ernesto.the_last_dance.entities.Prenotazione;
import ernesto.the_last_dance.entities.Ruolo;
import ernesto.the_last_dance.entities.Utente;
import ernesto.the_last_dance.exceptions.BadRequestException;
import ernesto.the_last_dance.payloads.PrenotazioneDTO;
import org.springframework.stereotype.Service;
import ernesto.the_last_dance.repositories.PrenotazioneRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PrenotazioneService {
    private final PrenotazioneRepository prenotazioniRepository;
    private final EventiService eventiService;
    private final UtentiService utentiService;

    public PrenotazioneService(PrenotazioneRepository prenotazioniRepository, EventiService eventiService, UtentiService utentiService) {
        this.prenotazioniRepository = prenotazioniRepository;
        this.eventiService = eventiService;
        this.utentiService = utentiService;
    }

    public Prenotazione postPrenotazione(PrenotazioneDTO body, UUID utenteId){

        Evento evento = this.eventiService.findById(body.eventoId());
        Utente utente = this.utentiService.findById(utenteId);

        if(utente.getRuolo().equals(Ruolo.ORGANIZZATORE)){
            if (evento.getOrganizzatore().getId().equals(utente.getId()))
                throw new BadRequestException("Sei organizzatore, non puoi prenotare un evento");
        }

        List<Prenotazione> listaPrenotazioni = this.prenotazioniRepository.findByUtenteEdEvento(evento.getId(), utente.getId());
        if(!listaPrenotazioni.isEmpty()) throw new BadRequestException("Non è possibile effettuare più di una prenotazione per lo stesso utente per lo stesso evento");

        List<Prenotazione> totPrenotazioniEvento = this.prenotazioniRepository.findAllByEvento(evento.getId());
        if(totPrenotazioniEvento.size() >= evento.getPostiDisponibili())
            throw new BadRequestException("Evento SOLD OUT");

        Prenotazione nuovaPrenotazione = new Prenotazione(LocalDate.now(), evento, utente);

        this.prenotazioniRepository.save(nuovaPrenotazione);

        System.out.println("Prenotazione salvata");

        return nuovaPrenotazione;
    }

}
