package at.tobias.schule.textadventurebackend.dto.adventure;

import at.tobias.schule.textadventurebackend.service.VariableManager;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public class TextStageData {
    private long id =0;
    private List<String> text = new ArrayList<>();
    private long stage;
    private List<RequirementData> requirements = new ArrayList<>();
    private List<TextActionData> actions = new ArrayList<>();



//    public boolean checkRequirements(){
//        return requirements.stream().allMatch(requirement ->
//                VariableManager.getVariables().getOrDefault(requirement.getVariable(),"")
//                        .equals(requirement.getValue()));
//    }
}
