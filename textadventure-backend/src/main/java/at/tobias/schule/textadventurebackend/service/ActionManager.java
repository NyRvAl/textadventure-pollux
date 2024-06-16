package at.tobias.schule.textadventurebackend.service;

import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;
import at.tobias.schule.textadventurebackend.dto.adventure.function.VariableFunction;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ActionManager {
    private final Map<String,Class<? extends TextActionData>> registeredActions = new ConcurrentHashMap<>();
    private final Map<String,VariableFunction> registeredFunctions = new ConcurrentHashMap<>();
    public void registerAction(TextActionData textAction){
        registeredActions.put(textAction.deserializeKey(),textAction.getClass());
    }

    public Class<? extends TextActionData> getClassByKey(String key){
        return registeredActions.get(key);
    }

    public void registerVariableFunction(VariableFunction variableFunction){
        registeredFunctions.put(variableFunction.getFunctionName(),variableFunction);
    }

    public VariableFunction getFunctionClassByKey(String key){
        return registeredFunctions.get(key);
    }
}
