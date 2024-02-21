package ascii_art;

public class InvalidCharToAddException extends Exception{
    public InvalidCharToAddException(String errorMessage){
        super(errorMessage);
    }
}
