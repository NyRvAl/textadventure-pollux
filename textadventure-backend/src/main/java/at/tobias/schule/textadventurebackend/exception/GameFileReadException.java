package at.tobias.schule.textadventurebackend.exception;

public class GameFileReadException extends EnhancedRuntimeException{
    public GameFileReadException(String message) {
        super("GAME_FILE_READ", message);
    }
}
