package ernesto.the_last_dance.controllers;

import ernesto.the_last_dance.entities.Prenotazione;
import ernesto.the_last_dance.entities.Utente;
import ernesto.the_last_dance.payloads.PrenotazioneDTO;
import ernesto.the_last_dance.services.PrenotazioneService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenota")
public class PrenotazioniController {

    private final PrenotazioneService prenotazioniService;

    public PrenotazioniController(PrenotazioneService prenotazioniService) {
        this.prenotazioniService = prenotazioniService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('UTENTE', 'ORGANIZZATORE')")
    public Prenotazione postPrenotazione(@RequestBody PrenotazioneDTO body, @AuthenticationPrincipal Utente utenteAutenticato){
        return this.prenotazioniService.postPrenotazione(body, utenteAutenticato.getId());
    }
}
