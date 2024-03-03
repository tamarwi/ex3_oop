package ascii_art;

/**
 * Custom exception class to handle invalid parameters in ASCII art generation or manipulation.
 */
public class InvalidParamsException extends Exception {
    /**
     * Constructor that takes an error message as input and passes it to the superclass constructor.
     * @param errorMessage The error message describing the invalid parameters.
     */
    public InvalidParamsException(String errorMessage) {
        super(errorMessage);
    }
}
