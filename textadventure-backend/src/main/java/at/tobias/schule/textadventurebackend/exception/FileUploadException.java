package at.tobias.schule.textadventurebackend.exception;

public class FileUploadException extends EnhancedRuntimeException{
    public FileUploadException(String message) {
        super("FILE_UPLOAD", message);
    }
}
