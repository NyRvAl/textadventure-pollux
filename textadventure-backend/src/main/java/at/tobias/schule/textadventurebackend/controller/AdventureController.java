package at.tobias.schule.textadventurebackend.controller;


import at.tobias.schule.textadventurebackend.dto.request.ReviewDTO;
import at.tobias.schule.textadventurebackend.dto.request.StatusDTO;
import at.tobias.schule.textadventurebackend.dto.response.GameInfoAvailableDTO;
import at.tobias.schule.textadventurebackend.service.AdventureGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AdventureController {

    private final AdventureGameService adventureGameService;

    @Autowired
    public AdventureController(AdventureGameService adventureGameService) {
        this.adventureGameService = adventureGameService;
    }

    @PostMapping("/newAdventure")
    @ResponseStatus(HttpStatus.CREATED)
    public void uploadFile(@RequestParam("file") MultipartFile file) {
        adventureGameService.newAdventure(file);
    }

    @GetMapping("/playAdventure")
    public ResponseEntity<String> getContent(@RequestParam("name") String name) {
        String content = adventureGameService.getFileService().readFile(name);
        return ResponseEntity.status(HttpStatus.OK).body(content);
    }
    @PostMapping("/review")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void review(@RequestBody ReviewDTO reviewDTO){
        adventureGameService.addReview(reviewDTO);
    }
    @PutMapping("/status")
    @ResponseStatus(HttpStatus.OK)
    public void changeSatus(@RequestBody StatusDTO statusDTO){
        adventureGameService.changeStatus(statusDTO);
    }
    @GetMapping("/availableGames")
    @ResponseStatus(HttpStatus.OK)
    public List<GameInfoAvailableDTO> allGames(){
        return adventureGameService.getAvailableGames();
    }
}
