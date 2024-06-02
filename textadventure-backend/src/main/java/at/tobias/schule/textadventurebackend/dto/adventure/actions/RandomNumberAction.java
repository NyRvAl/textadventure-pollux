package at.tobias.schule.textadventurebackend.dto.adventure.actions;

import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;
import at.tobias.schule.textadventurebackend.dto.response.OutputDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

@Getter
@Setter
public class RandomNumberAction extends TextActionData {

    private int min = 0, max = 0;

    @Override
    public void onAction(ActionContext actionContext) {
        Random random = new Random();
        max++;
        int result = random.nextInt(max-min) + min;
        localVariables.put("random",result);

    }

    @Override
    public String deserializeKey() {
        return "RANDOM_NUMBER";
    }
}
