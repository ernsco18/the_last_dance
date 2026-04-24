package ernesto.the_last_dance.exceptions;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(UUID id) {
        super("id: " + id + " non trovato");
    }
}
