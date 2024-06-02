package at.tobias.schule.textadventurebackend.dto.adventure;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RequirementData {
    private String variable;
    private Object value = "";
}
