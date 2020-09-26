package tacs.wololo.model.exceptions;

public class InvalidEmailException extends RuntimeException {
    public InvalidEmailException(String mesage) {
        super(mesage);
    }
}
