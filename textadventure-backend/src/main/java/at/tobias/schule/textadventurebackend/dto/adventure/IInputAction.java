package at.tobias.schule.textadventurebackend.dto.adventure;

import at.tobias.schule.textadventurebackend.dto.request.ChatRoomMessageDTO;

public interface IInputAction {
    boolean input(ChatRoomMessageDTO text);
}
