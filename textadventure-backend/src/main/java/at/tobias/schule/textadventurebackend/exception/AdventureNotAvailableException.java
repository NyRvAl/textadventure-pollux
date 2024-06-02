package at.tobias.schule.textadventurebackend.exception;

public class AdventureNotAvailableException extends EnhancedRuntimeException{
    public AdventureNotAvailableException(String message) {
        super("NOT_AVAILABLE", message);
    }
}
