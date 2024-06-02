package at.tobias.schule.textadventurebackend.dto.adventure.actions;

import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;
import at.tobias.schule.textadventurebackend.dto.response.OutputDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TextOutputAction extends TextActionData {

    private String text;

    @Override
    public void onAction(ActionContext actionContext) {
        text = actionContext.getVariableManager().useVariablesInText(text,actionContext.getGameId());
        localVariables.put("text", text);
    }

    @Override
    public String deserializeKey() {
        return "WRITE";
    }
}
