package ascii_art;

// Custom exception class to handle invalid parameters in ASCII art generation or manipulation.
public class InvalidParamsException extends Exception {
    // Constructor that takes an error message as input and passes it to the superclass constructor.
    public InvalidParamsException(String errorMessage) {
        super(errorMessage);
    }
}
