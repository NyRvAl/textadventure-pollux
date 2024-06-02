package at.tobias.schule.textadventurebackend.dto.adventure.actions;

import at.tobias.schule.textadventurebackend.service.VariableManager;

public interface ActionContext {

    VariableManager getVariableManager();
    String getGameId();
}
