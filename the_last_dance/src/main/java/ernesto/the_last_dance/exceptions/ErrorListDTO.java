package ernesto.the_last_dance.exceptions;

public class ErrorListDTO extends RuntimeException {
    public ErrorListDTO(String message) {
        super(message);
    }
}
