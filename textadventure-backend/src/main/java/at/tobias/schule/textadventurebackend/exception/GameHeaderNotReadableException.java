package at.tobias.schule.textadventurebackend.exception;

public class GameHeaderNotReadableException extends EnhancedRuntimeException{
    public GameHeaderNotReadableException(String message) {
        super("GAME_HEADER_NOT_READABLE", message);
    }
}
