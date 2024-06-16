package at.tobias.schule.textadventurebackend.service;

import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Service
public class ActionManager {
    private final Map<String,Class<? extends TextActionData>> registeredActions = new ConcurrentHashMap<>();

    public void registerAction(TextActionData textAction){
        registeredActions.put(textAction.deserializeKey(),textAction.getClass());
    }

    public Class<? extends TextActionData> getClassByKey(String key){
        return registeredActions.get(key);
    }


}
