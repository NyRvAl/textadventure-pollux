package at.tobias.schule.textadventurebackend.dto.adventure;


import at.tobias.schule.textadventurebackend.service.VariableManager;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)

public abstract class TextActionData implements IAction {

    private List<String> setVariables;
    protected Map<String, Object> localVariables = new ConcurrentHashMap<>();

    @JsonIgnore
    private boolean visited = false;
//    public void executeSetVariables(String gameId) {
//        if(setVariables == null)
//            return;
//        setVariables.forEach(variable -> {
//                    String variables = variable.split("=")[0];
//                    String assignmentRaw = variable.split("=")[1];
//                    String assignment = assignmentRaw.replace("{", "").replace("}", "");
//                    Object assignmentValue = null;
//                    if (VariableManager.GLOBAL_VARIABLE.matcher(assignmentRaw).matches())
//                        assignmentValue = variableManager.getVariables(gameId).get(assignment);
//                    else if (VariableManager.LOCAL_VARIABLE.matcher(assignmentRaw).matches())
//                        assignmentValue = localVariables.get(assignment);
//                    variableManager.getVariables(gameId).put(variables, assignmentValue);
//                }
//        );
//    }
@JsonProperty("action")

public abstract String deserializeKey();


}
