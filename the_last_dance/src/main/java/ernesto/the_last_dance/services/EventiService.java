package ernesto.the_last_dance.services;

import ernesto.the_last_dance.entities.Evento;
import ernesto.the_last_dance.entities.Utente;
import ernesto.the_last_dance.exceptions.BadRequestException;
import ernesto.the_last_dance.exceptions.NotFoundException;
import ernesto.the_last_dance.payloads.EventoDTO;
import ernesto.the_last_dance.repositories.EventiRepository;
import ernesto.the_last_dance.security.JWTools;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class EventiService {
    private final EventiRepository eventiRepository;
    private final UtentiService utentiService;
    private final JWTools jwTools;

    public EventiService(EventiRepository eventiRepository, UtentiService utentiService, JWTools jwtTools){
        this.eventiRepository = eventiRepository;
        this.utentiService = utentiService;
        this.jwTools = jwtTools;
    }

    public Evento saveEvento(EventoDTO body){
        Utente utente = this.utentiService.findById(body.organizzatoreId());

        if(body.data().isBefore(LocalDate.now())) throw new BadRequestException("data non valido");

        Evento nuovoEvento = new Evento(body.titolo(), body.data() ,body.descrizione(), body.luogo(), body.postiDisponibili(), utente);

        this.eventiRepository.save(nuovoEvento);

        return nuovoEvento;
    }

    public Evento findById(UUID eventoId){
        return this.eventiRepository.findById(eventoId).orElseThrow(() -> new NotFoundException(eventoId));
    }

    public Evento putEvento(UUID eventoId, EventoDTO body, UUID organizzatoreId){
        Evento evento = this.findById(eventoId);
        Utente organizzatore = this.utentiService.findById(organizzatoreId);
        if (!evento.getOrganizzatore().getId().equals(organizzatore.getId()))
            throw new BadRequestException("È possibile modificare solo gli eventi pubblicati dal proprio account");
        if (body.data().isBefore(LocalDate.now())) throw new BadRequestException("La data dell'evento deve essere successiva alla data di oggi");

        evento.setTitolo(body.titolo());
        evento.setDescrizione(body.descrizione());
        evento.setData(body.data());
        evento.setLuogo(body.luogo());
        evento.setPostiDisponibili(body.postiDisponibili());

        this.eventiRepository.save(evento);

        System.out.println("Evento aggiornato");

        return evento;
    }

    public void deleteEvento(UUID eventoId, UUID organizzatoreId){
        Evento evento = this.findById(eventoId);
        Utente organizzatore = this.utentiService.findById(organizzatoreId);
        if (!evento.getOrganizzatore().getId().equals(organizzatore.getId()))
            throw new BadRequestException("È possibile eliminare solo gli eventi pubblicati dal proprio account");
        this.eventiRepository.delete(evento);
        System.out.println("Evento eliminato");
    }
}
