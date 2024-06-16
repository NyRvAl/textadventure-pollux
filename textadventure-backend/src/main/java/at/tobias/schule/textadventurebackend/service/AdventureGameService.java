package at.tobias.schule.textadventurebackend.service;


import at.tobias.schule.textadventurebackend.dto.request.ReviewDTO;
import at.tobias.schule.textadventurebackend.dto.response.GameInfoAvailableDTO;
import at.tobias.schule.textadventurebackend.exception.AdventureNotFoundException;
import at.tobias.schule.textadventurebackend.model.AdventureGameModel;
import at.tobias.schule.textadventurebackend.repo.AdventureRepository;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
public class AdventureGameService implements IAdventureService {
    private final AdventureRepository adventureRepository;
    @Getter
    private final FileService fileService;

    @Autowired
    public AdventureGameService(AdventureRepository adventureRepository, FileService fileService) {
        this.adventureRepository = adventureRepository;
        this.fileService = fileService;
    }

    public AdventureGameModel create(String name) {
        AdventureGameModel adventureGameModel = new AdventureGameModel();
        adventureGameModel.setName(name);
        adventureGameModel.setLastTimeChanged(LocalDateTime.now(Clock.systemUTC()));
        return adventureGameModel;
    }

    public void newAdventure(MultipartFile multipartFile) {
        String content = fileService.processFile(multipartFile);
        save(create(fileService.readHeaderOfFileFromString(content).getDisplayName()));
    }

    @Override
    public void save(AdventureGameModel gameModel) {
        AdventureGameModel saved = findByName(gameModel.getName());
        if (saved != null)
            update(saved);
        else
            adventureRepository.save(gameModel);
    }

    @Override
    public AdventureGameModel findByName(String name) {
        return adventureRepository.findByName(name);
    }

    @Override
    public AdventureGameModel findById(long id) {
        return adventureRepository.findById(id);
    }

    @Override
    public void update(AdventureGameModel gameModel) {
        gameModel.setLastTimeChanged(LocalDateTime.now(Clock.systemUTC()));
        adventureRepository.save(gameModel);
    }

    public List<GameInfoAvailableDTO> getAvailableGames() {
        return fileService.readAllFiles().stream().map(file -> {
                    AdventureGameModel adventureGameModel = findByName(file.getDisplayName());
                    if (adventureGameModel == null) {
                        return null; // If adventureGameModel is null, return null
                    }
                    return new GameInfoAvailableDTO(file, adventureGameModel);
                }).filter(Objects::nonNull) // Filter out null results
                .toList();
    }

    public void addReview(ReviewDTO reviewDTO){
        long stars = reviewDTO.stars();
        if(reviewDTO.stars() > 5)
            stars = 5;
        else if(reviewDTO.stars() < 1)
            stars = 1;

        AdventureGameModel adventureGameModel = findById(reviewDTO.id());
        if(adventureGameModel == null)
            throw new AdventureNotFoundException(String.format("Adventure with ID %s not found",reviewDTO.id()));
        long oldAmountReviews = adventureGameModel.getAmountRating();
        long oldReviewSummed = adventureGameModel.getRatingSummedUp();
        adventureGameModel.setAmountRating(oldAmountReviews+1);
        adventureGameModel.setRatingSummedUp(oldReviewSummed+stars);
        update(adventureGameModel);
    }
}
