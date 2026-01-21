package ClassConnect.exception;

public class RegisterAlreadyExistsException  extends RuntimeException{

    public RegisterAlreadyExistsException(String message) {
        super(message);
    }
}
