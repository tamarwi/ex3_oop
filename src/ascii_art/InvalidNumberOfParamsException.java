package ascii_art;

public class InvalidNumberOfParamsException extends Exception{
    public InvalidNumberOfParamsException(String errorMessage){
        super(errorMessage);
    }
}
