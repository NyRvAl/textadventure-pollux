package at.tobias.schule.textadventurebackend.dto.request;

public record ChatRoomMessageDTO(String user, MessageType type, String message) {

    public enum MessageType{
        JOIN,LEAVE,RESPONSE
    }
}
