package at.tobias.schule.textadventurebackend.dto.adventure;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)

public class TextAdventureData {
    private long id;
    private List<TextStageData> adventure = new ArrayList<>();
    private String gameId;
    private int currentTextActionIndex = 0;
//    public List<TextActionData> next() {
//        List<TextActionData> textActionData = new ArrayList<>();
//        TextActionData currentTextAction =
//        while (!(currentTextAction instanceof IInputAction)) {
//            int currentStageIndex = i;
//            TextStageData textStage = adventure.stream().filter(element -> element.get() == currentStageIndex)
//                    .filter(TextStageData::checkRequirements)
//                    .findFirst().orElse(null);
//            if (textStage != null)
//                textStage.getActions().forEach(action -> {
//                    action.onAction();
//                    action.executeSetVariables();
//                });
//            else
//                break;
//        }
//    }
//    public TextStageData getTextStageById(long index){
//
//    }
//    public TextStageData nextStage(String gameId){
//        this.gameId = gameId;
//        TextStageData textStage = adventure.stream().filter(element -> element.getStage() == currentStageIndex)
//                .filter(TextStageData::checkRequirements)
//                .findFirst().orElse(null);
//        if (textStage != null)
//            textStage.getActions().forEach(action -> {
//                action.onAction();
//            });
//        else
//            break;
//    }
//
//    public void executeVariables(String gameId){
//
//    }
}
