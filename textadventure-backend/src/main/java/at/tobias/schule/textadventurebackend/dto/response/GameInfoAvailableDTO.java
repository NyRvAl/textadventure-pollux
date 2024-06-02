package at.tobias.schule.textadventurebackend.dto.response;

import at.tobias.schule.textadventurebackend.model.AdventureGameModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameInfoAvailableDTO {
    private GameInfoDTO gameInfoDTO;
    private AdventureGameModel adventureGameModel;
}
