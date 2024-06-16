package at.tobias.schule.textadventurebackend.dto.adventure.function;

import at.tobias.schule.textadventurebackend.service.VariableManager;

import java.util.HashMap;
import java.util.Map;

public class AddFunction extends VariableFunction{
    public AddFunction() {
        super("ADD");
    }

    @Override
    public Map<String, Class<?>> requiredParams() {
        Map<String,Class<?>> params = new HashMap<>();
        params.put("var",String.class);
        params.put("add", Integer.class);
        return params;
    }

    @Override
    public void onFunctionUse(FunctionContext functionContext) {
        String variableToAdd = (String) functionContext.params().get("var");
        int toAdd = (Integer) functionContext.params().get("add");
        String gameId = functionContext.gameId();
        if(functionContext.variableManager().getVariables(gameId).get(variableToAdd) != null && functionContext.variableManager().getVariables(gameId).get(variableToAdd) instanceof Integer integer){
            toAdd+=integer;
        }
        functionContext.variableManager().setVariables(gameId,variableToAdd,toAdd);

    }
}
