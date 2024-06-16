package at.tobias.schule.textadventurebackend.dto.adventure.function;

import at.tobias.schule.textadventurebackend.service.VariableManager;
import lombok.Getter;

import java.util.Map;

@Getter
public abstract class VariableFunction {
    private final String functionName;

    public VariableFunction(String functionName){
        this.functionName = functionName;
    }


    abstract Map<String,Class<?>> requiredParams();
    abstract void onFunctionUse(FunctionContext functionContext);



}
