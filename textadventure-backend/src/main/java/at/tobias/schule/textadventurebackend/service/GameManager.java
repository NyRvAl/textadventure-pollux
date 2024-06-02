package at.tobias.schule.textadventurebackend.service;


import at.tobias.schule.textadventurebackend.dto.adventure.IInputAction;
import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;
import at.tobias.schule.textadventurebackend.dto.adventure.TextAdventureData;
import at.tobias.schule.textadventurebackend.dto.adventure.actions.ActionContext;
import at.tobias.schule.textadventurebackend.dto.request.ChatRoomMessageDTO;
import at.tobias.schule.textadventurebackend.dto.request.CreateGameRoomDTO;
import at.tobias.schule.textadventurebackend.dto.response.OutputDTO;
import at.tobias.schule.textadventurebackend.exception.AdventureNotAvailableException;
import at.tobias.schule.textadventurebackend.exception.AdventureNotFoundException;
import at.tobias.schule.textadventurebackend.model.AdventureGameModel;
import at.tobias.schule.textadventurebackend.model.GameroomModel;
import at.tobias.schule.textadventurebackend.repo.GameRoomRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameManager {

    private final GameRoomRepository gameRoomRepository;
    private final AdventureGameService adventureGameService;
    private final GameProcessService gameProcessService;
    private final ObjectMapper objectMapper;
    private final Map<String, TextAdventureData> adventures = new ConcurrentHashMap<>();
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public GameManager(GameRoomRepository gameRoomRepository, AdventureGameService adventureGameService, ObjectMapper objectMapper, GameProcessService gameProcessService, SimpMessagingTemplate simpMessagingTemplate) {
        this.gameRoomRepository = gameRoomRepository;
        this.adventureGameService = adventureGameService;
        this.objectMapper = objectMapper;
        this.gameProcessService = gameProcessService;
        this.messagingTemplate = simpMessagingTemplate;
    }

    public String createGameRoom(CreateGameRoomDTO createGameRoomDTO) {
        AdventureGameModel adventureGameModel = adventureGameService.findById(createGameRoomDTO.gameId());
        if (adventureGameModel == null)
            throw new AdventureNotFoundException(String.format("Adventure with ID %s not found", createGameRoomDTO.gameId()));
        else if (!adventureGameModel.isAvailable())
            throw new AdventureNotAvailableException(String.format("Adventure with ID %s is deactivated", createGameRoomDTO.gameId()));

        GameroomModel gameroomModel = new GameroomModel();
        gameroomModel.setAdventureGameModel(adventureGameModel);
        gameroomModel.setOwner(createGameRoomDTO.username());
        return gameRoomRepository.save(gameroomModel).getId().toString();
    }


    public void createTextAdventure(String gameId) {
        if (adventures.containsKey(gameId))
            return;
        GameroomModel gameroomModel = gameRoomRepository.findById(UUID.fromString(gameId)).orElse(null);
        if (gameroomModel == null) return;

        String content = adventureGameService.getFileService().readFile(
                         adventureGameService.getFileService()
                        .getFileNameByName(gameroomModel.getAdventureGameModel().getName())
        );
        try {
            TextAdventureData textAdventureData = objectMapper.readValue(content, TextAdventureData.class);
            adventures.put(gameId, textAdventureData);
            messagingTemplate.convertAndSend("/topic/" + gameId, new OutputDTO(continueGame(gameId)));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public GameroomModel findById(String uuid) {
        try {
            return gameRoomRepository.findById(UUID.fromString(uuid)).orElse(null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    public List<GameroomModel> allGamerooms() {
        return gameRoomRepository.findAll();
    }

    public List<TextActionData> continueGame(String gameId) {
        TextAdventureData textAdventureData = adventures.get(gameId);
        if (textAdventureData == null) return null;
        return gameProcessService.continueAdventure(textAdventureData, gameId);
    }

    public void onInput(String gameId, ChatRoomMessageDTO chatRoomMessageDTO) {
        TextAdventureData textAdventureData = adventures.get(gameId);
        if (textAdventureData == null) return;
        TextActionData textActionData = gameProcessService.handleInput(textAdventureData, gameId, chatRoomMessageDTO);
        if (textActionData != null) {
            List<TextActionData> game = new ArrayList<>(gameProcessService.continueAdventure(textAdventureData, gameId));
            messagingTemplate.convertAndSend("/topic/" + gameId, new OutputDTO(game));
        }

    }

}
