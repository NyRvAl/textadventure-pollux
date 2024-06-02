package at.tobias.schule.textadventurebackend.controller;


import at.tobias.schule.textadventurebackend.dto.request.CreateGameRoomDTO;
import at.tobias.schule.textadventurebackend.dto.response.ChatRoomCreatedDTO;
import at.tobias.schule.textadventurebackend.model.GameroomModel;
import at.tobias.schule.textadventurebackend.service.GameManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatRoomController {
    private final GameManager gameManager;


    @Autowired
    public ChatRoomController(GameManager gameManager){
        this.gameManager = gameManager;
    }

    @PostMapping("/newChatroom")
    @ResponseStatus(HttpStatus.CREATED)
    public ChatRoomCreatedDTO createRoom(@RequestBody CreateGameRoomDTO createGameRoomDTO){
        return new ChatRoomCreatedDTO(gameManager.createGameRoom(createGameRoomDTO));
    }
    @GetMapping("/allChatrooms")
    @ResponseStatus(HttpStatus.OK)
    public List<GameroomModel> allChatrooms(){
        return gameManager.allGamerooms();
    }
}
