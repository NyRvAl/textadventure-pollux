package at.tobias.schule.textadventurebackend.dto.adventure;

import at.tobias.schule.textadventurebackend.dto.adventure.actions.ActionContext;
import at.tobias.schule.textadventurebackend.dto.response.OutputDTO;

public interface IAction {


     void onAction(ActionContext actionContext);
}
