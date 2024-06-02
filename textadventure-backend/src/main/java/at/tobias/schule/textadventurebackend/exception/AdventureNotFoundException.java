package at.tobias.schule.textadventurebackend.exception;

public class AdventureNotFoundException extends EnhancedRuntimeException{
    public AdventureNotFoundException(String message) {
        super("ADVENTURE_NOT_FOUND", message);
    }
}
