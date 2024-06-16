package at.tobias.schule.textadventurebackend.service;


import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service

public class VariableManager {
    public static final Pattern GLOBAL_VARIABLE = Pattern.compile("\\{\\{[A-Za-z]+}}", Pattern.CASE_INSENSITIVE);
    public static final Pattern LOCAL_VARIABLE = Pattern.compile("\\{[A-Za-z]+}", Pattern.CASE_INSENSITIVE);
    public static final Pattern FUNCTION = Pattern.compile("\\$[A-Za-z0-9]+\\([^)]*\\)");
    private final Map<String,Map<String, Object>> variables = new ConcurrentHashMap<>();

    public Map<String, Object> getVariables(String gameId) {
        return variables.getOrDefault(gameId, new ConcurrentHashMap<>());
    }
    public void setVariables(String gameId,String key, Object value){
        Map<String,Object> variable = getVariables(gameId);
        variable.put(key,value);
        variables.put(gameId,variable);
    }

    public String useVariablesInText(String text, String gameId){
        Matcher matcher = VariableManager.GLOBAL_VARIABLE.matcher(text);
        for (int i = 0; matcher.find(); i++) {
            String segment = matcher.group(i);
            String currentMatch = segment.replace("{{", "").replace("}}", "");
            Object value = getVariables(gameId).getOrDefault(currentMatch, "not found");
            String newValue = value.toString();
            if (value instanceof Double)
                newValue = new DecimalFormat("0").format(value);

            text = text.replace(segment, newValue);
        }
        return text;
    }
    public void clearVariables(String gameId){
        variables.remove(gameId);
    }
}
