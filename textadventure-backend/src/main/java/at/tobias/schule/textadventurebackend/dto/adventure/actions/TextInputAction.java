package at.tobias.schule.textadventurebackend.dto.adventure.actions;

import at.tobias.schule.textadventurebackend.dto.adventure.IInputAction;
import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;
import at.tobias.schule.textadventurebackend.dto.request.ChatRoomMessageDTO;
import at.tobias.schule.textadventurebackend.dto.response.OutputDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TextInputAction extends TextActionData implements IInputAction {
    private List<String> limit;

    private String input;
    @Override
    public void onAction(ActionContext actionContext) {
        localVariables.put("input",input);
    }


    @Override
    public String deserializeKey() {
        return "WAITING_FOR_INPUT";
    }

    @Override
    public boolean input(ChatRoomMessageDTO text) {
        if(text.message() != null && limit.contains(text.message())){
            input = text.message();
            return true;
        }
        return false;
    }
}
