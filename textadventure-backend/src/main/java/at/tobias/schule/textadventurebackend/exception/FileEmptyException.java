package at.tobias.schule.textadventurebackend.exception;

public class FileEmptyException extends EnhancedRuntimeException{
    public FileEmptyException(String message) {
        super("FILE_EMPTY", message);
    }


}
