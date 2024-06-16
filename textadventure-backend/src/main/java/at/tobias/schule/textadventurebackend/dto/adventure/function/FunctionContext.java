package at.tobias.schule.textadventurebackend.dto.adventure.function;

import at.tobias.schule.textadventurebackend.service.VariableManager;

import java.util.Map;

public interface FunctionContext {

    VariableManager variableManager();
    Map<String,Object> params();

    String gameId();
}
