package at.tobias.schule.textadventurebackend.controller;


import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;
import at.tobias.schule.textadventurebackend.dto.adventure.actions.TextOutputAction;
import at.tobias.schule.textadventurebackend.dto.request.ChatRoomMessageDTO;
import at.tobias.schule.textadventurebackend.dto.response.OutputDTO;
import at.tobias.schule.textadventurebackend.dto.response.UserChangedDTO;
import at.tobias.schule.textadventurebackend.service.GameManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j
public class GameController {
    private final GameManager gameManager;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameController(GameManager gameManager, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameManager = gameManager;
        this.messagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/textadventure.send/{gameId}")
    public void processTextadventureResponse(@Payload ChatRoomMessageDTO chatRoomMessageDTO, @DestinationVariable String gameId, SimpMessageHeaderAccessor headerAccessor) {
        if (chatRoomMessageDTO == null) return;
        if (gameManager.findById(gameId) == null) return;
        log.info("Processing message: " + chatRoomMessageDTO.message());
        if (chatRoomMessageDTO.type() == ChatRoomMessageDTO.MessageType.LEAVE || chatRoomMessageDTO.type() == ChatRoomMessageDTO.MessageType.JOIN)
            handleJoinsLeaves(chatRoomMessageDTO, gameId);
        else
            handleInputTextAdventure(chatRoomMessageDTO, gameId);
    }

    public void handleInputTextAdventure(ChatRoomMessageDTO chatRoomMessageDTO, String gameId) {
        TextOutputAction textOutputAction = new TextOutputAction();
        textOutputAction.setText(String.format("%s: %s ", chatRoomMessageDTO.user(), chatRoomMessageDTO.message()));
        List<TextActionData> data = new ArrayList<>();
        data.add(textOutputAction);
        messagingTemplate.convertAndSend("/topic/" + gameId, new OutputDTO(data));
        if (!chatRoomMessageDTO.message().startsWith("#"))
            gameManager.onInput(gameId, chatRoomMessageDTO);


    }

    public void handleJoinsLeaves(ChatRoomMessageDTO chatRoomMessageDTO, String gameId) {
        TextOutputAction textOutputAction = new TextOutputAction();
        if (chatRoomMessageDTO.type() == ChatRoomMessageDTO.MessageType.JOIN)
            textOutputAction.setText(String.format("%s ist gejoined", chatRoomMessageDTO.user()));
        else
            textOutputAction.setText(String.format("%s ist geleaved", chatRoomMessageDTO.user()));

        List<TextActionData> data = new ArrayList<>();
        data.add(textOutputAction);
        messagingTemplate.convertAndSend("/topic/" + gameId, new OutputDTO(data));
        gameManager.createTextAdventure(gameId);

    }
}
