package at.tobias.schule.textadventurebackend.dto.adventure;


import at.tobias.schule.textadventurebackend.service.VariableManager;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)

public abstract class TextActionData implements IAction {

    private List<String> setVariables;
    protected Map<String, Object> localVariables = new ConcurrentHashMap<>();


@JsonProperty("action")
public abstract String deserializeKey();


}
