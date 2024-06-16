package at.tobias.schule.textadventurebackend.service;


import at.tobias.schule.textadventurebackend.dto.adventure.IInputAction;
import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;
import at.tobias.schule.textadventurebackend.dto.adventure.TextAdventureData;
import at.tobias.schule.textadventurebackend.dto.adventure.TextStageData;
import at.tobias.schule.textadventurebackend.dto.adventure.actions.ActionContext;
import at.tobias.schule.textadventurebackend.dto.adventure.actions.RandomNumberAction;
import at.tobias.schule.textadventurebackend.dto.adventure.function.FunctionContext;
import at.tobias.schule.textadventurebackend.dto.adventure.function.VariableFunction;
import at.tobias.schule.textadventurebackend.dto.request.ChatRoomMessageDTO;
import at.tobias.schule.textadventurebackend.exception.GameFileReadException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Getter
@Slf4j

public class GameProcessService {


    private final VariableManager variableManager;
    private final ActionManager actionManager;

    @Autowired
    public GameProcessService(VariableManager variableManager, ActionManager actionManager) {
        this.variableManager = variableManager;
        this.actionManager = actionManager;
    }

    public List<TextActionData> continueAdventure(TextAdventureData textAdventureData, String gameId) {
        TextStageData currentStage;
        List<TextActionData> toVisit = new ArrayList<>();
        while (true) {
            if (textAdventureData.getCurrentStageIndex() == 0)
                currentStage = findFirstStage(textAdventureData);
            else {
                currentStage = findNextStage(textAdventureData, gameId);
            }
            if (currentStage == null)
                break;
            textAdventureData.setCurrentStageIndex((int) currentStage.getStage() + 1);

            for (TextActionData textActionData : currentStage.getActions()) {
                if (textActionData != null && !(textActionData instanceof IInputAction) && !textActionData.isVisited()) {
                    if (!(textActionData instanceof RandomNumberAction))
                        toVisit.add(textActionData);
                    textActionData.onAction(new ActionContext() {
                        @Override
                        public VariableManager getVariableManager() {
                            return variableManager;
                        }

                        @Override
                        public String getGameId() {
                            return gameId;
                        }
                    });
                    executeSetVariables(textActionData, gameId);
                    textActionData.setVisited(true);
                } else if (textActionData != null && !textActionData.isVisited() && textActionData instanceof IInputAction) {
                    toVisit.add(textActionData);
                    return toVisit;
                }
            }
        }
        variableManager.clearVariables(gameId);
        return toVisit;

    }

    public TextActionData handleInput(TextAdventureData textAdventureData, String gameId, ChatRoomMessageDTO chatRoomMessageDTO) {
        TextStageData currentStage;
        currentStage = findStage(textAdventureData, gameId, textAdventureData.getCurrentStageIndex() - 1);

        for (TextActionData textActionData : currentStage.getActions()) {
            if (textActionData != null && !(textActionData instanceof IInputAction) && !textActionData.isVisited()) {
                return null;
            } else if (textActionData != null && !textActionData.isVisited() && textActionData instanceof IInputAction) {
                if (((IInputAction) textActionData).input(chatRoomMessageDTO)) {
                    textActionData.onAction(new ActionContext() {
                        @Override
                        public VariableManager getVariableManager() {
                            return variableManager;
                        }

                        @Override
                        public String getGameId() {
                            return gameId;
                        }
                    });
                    executeSetVariables(textActionData, gameId);
                    textActionData.setVisited(true);
                }
                else
                    return null;
                return textActionData;
            }
        }


        return null;
    }

    public TextStageData findFirstStage(TextAdventureData textAdventureData) {
        return textAdventureData.getAdventure().stream()
                .filter(adventureData -> adventureData.getId() == 0)
                .findFirst()
                .orElseThrow(() -> new GameFileReadException(String.format("Can't find first stage of %s", textAdventureData.getGameId())));

    }

    public TextStageData findNextStage(TextAdventureData textAdventureData, String gameId) {
        return textAdventureData.getAdventure().stream()
                .filter(element -> element.getStage() == (textAdventureData.getCurrentStageIndex()))
                .filter(element -> checkRequirements(element, gameId))
                .findFirst().orElse(null);
    }

    public TextStageData findStage(TextAdventureData textAdventureData, String gameId, long index) {
        return textAdventureData.getAdventure().stream()
                .filter(element -> element.getStage() == index)
                .filter(element -> checkRequirements(element, gameId))
                .findFirst().orElse(null);
    }

    public boolean checkRequirements(TextStageData textStageData, String gameId) {
        return textStageData.getRequirements().stream().allMatch(requirement -> requirement
                .compareTo(variableManager.getVariables(gameId).getOrDefault(requirement.getVariable(), "")));

    }

    public void executeSetVariables(TextActionData textActionData, String gameId) {
        if (textActionData.getSetVariables() == null)
            return;
        textActionData.getSetVariables().forEach(variable -> {
                    if (VariableManager.FUNCTION.matcher(variable).matches())
                        executeFunctions(gameId, variable);
                    else {
                        String variables = variable.split("=")[0];
                        String assignmentRaw = variable.split("=")[1];
                        String assignment = assignmentRaw.replace("{", "").replace("}", "");
                        Object assignmentValue = null;
                        if (VariableManager.GLOBAL_VARIABLE.matcher(assignmentRaw).matches())
                            assignmentValue = variableManager.getVariables(gameId).get(assignment);
                        else if (VariableManager.LOCAL_VARIABLE.matcher(assignmentRaw).matches())
                            assignmentValue = textActionData.getLocalVariables().get(assignment);
                        variableManager.setVariables(gameId, variables, assignmentValue);
                    }
                }
        );
    }

    public void executeFunctions(String gameId, String variable) {
        variable = variable.replace("$", "");
        String functionName = variable.split("\\(")[0];
        String params = variable.replace(functionName, "").replace("(", "").replace(")", "");
        VariableFunction variableFunction = getActionManager().getFunctionClassByKey(functionName);
        if (variableFunction == null)
            return;
        String[] args = params.split(",");
        Map<String, Object> paramsMap = new HashMap<>();
        for (String arg : args) {
            String argsKey = arg.split("=")[0].trim();
            String argsValue = arg.split("=")[1].trim();
            Object value;
            Class<?> clazz = variableFunction.requiredParams().get(argsKey);
            if (clazz == null)
                return;
            if (VariableManager.GLOBAL_VARIABLE.matcher(argsValue).matches()) {
                argsValue = argsValue.replace("{{", "").replace("}}", "");
                value = variableManager.getVariables(gameId).get(argsValue);
            } else
                value = argsValue;

            if (!value.getClass().isAssignableFrom(clazz)) {
                if (clazz.isAssignableFrom(Integer.class)) {
                    try {
                        value = Integer.parseInt(argsValue);
                    } catch (NumberFormatException e) {
                        return;
                    }
                }
            }
            paramsMap.put(argsKey, value);
        }
        variableFunction.onFunctionUse(new FunctionContext() {
            @Override
            public VariableManager variableManager() {
                return variableManager;
            }

            @Override
            public Map<String, Object> params() {
                return paramsMap;
            }

            @Override
            public String gameId() {
                return gameId;
            }
        });
    }
}
