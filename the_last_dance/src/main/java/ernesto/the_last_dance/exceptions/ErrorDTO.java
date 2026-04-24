package ernesto.the_last_dance.exceptions;

public class ErrorDTO extends RuntimeException {
    public ErrorDTO(String message) {
        super(message);
    }
}
