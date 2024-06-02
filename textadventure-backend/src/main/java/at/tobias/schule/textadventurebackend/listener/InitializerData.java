package at.tobias.schule.textadventurebackend.listener;

import at.tobias.schule.textadventurebackend.dto.adventure.actions.RandomNumberAction;
import at.tobias.schule.textadventurebackend.dto.adventure.actions.TextInputAction;
import at.tobias.schule.textadventurebackend.dto.adventure.actions.TextOutputAction;
import at.tobias.schule.textadventurebackend.dto.adventure.actions.TimeOutAction;
import at.tobias.schule.textadventurebackend.service.ActionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class InitializerData {

    private final ActionManager actionManager;

    @Autowired
    public InitializerData(ActionManager actionManager) {
        this.actionManager = actionManager;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initActions() {
        actionManager.registerAction(new TextOutputAction());
        actionManager.registerAction(new TextInputAction());
        actionManager.registerAction(new RandomNumberAction());
        actionManager.registerAction(new TimeOutAction());


    }
}
