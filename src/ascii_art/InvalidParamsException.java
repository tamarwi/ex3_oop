package ascii_art;

public class InvalidParamsException extends Exception{
    public InvalidParamsException(String errorMessage){
        super(errorMessage);
    }
}
