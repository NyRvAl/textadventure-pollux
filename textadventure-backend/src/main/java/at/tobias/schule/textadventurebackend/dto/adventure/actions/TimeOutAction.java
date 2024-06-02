package at.tobias.schule.textadventurebackend.dto.adventure.actions;

import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;
import at.tobias.schule.textadventurebackend.dto.response.OutputDTO;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TimeOutAction extends TextActionData {

    private long time = 0;

    @Override
    public void onAction(ActionContext actionContext) {
    }

    @Override
    public String deserializeKey() {
        return "SLEEP";
    }
}
