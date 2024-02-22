package ascii_art;

public class InvalidCharToRemoveException extends Exception{
    public InvalidCharToRemoveException(String errorMessage){
        super(errorMessage);
    }
}
