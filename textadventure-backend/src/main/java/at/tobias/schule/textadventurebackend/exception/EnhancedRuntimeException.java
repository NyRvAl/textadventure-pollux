package at.tobias.schule.textadventurebackend.exception;

import lombok.Getter;

public class EnhancedRuntimeException extends RuntimeException{
    @Getter
    private final String errorCode;

    public EnhancedRuntimeException(String errorCode, String message){
        super(message);
        this.errorCode = errorCode;
    }

}

