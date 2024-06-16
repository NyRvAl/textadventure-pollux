package at.tobias.schule.textadventurebackend.dto.adventure;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.type.internal.ConvertedBasicTypeImpl;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)

public class RequirementData {
    private String variable;
    private Object value = "";
    private ComparisonOption option;

    public enum ComparisonOption {
        EQUAL, NOT_EQUAL, GREATER, LOWER, GREATER_EQUALS, LOWER_EQUALS
    }

    public boolean compareTo(Object object) {
        if (option == null) option = ComparisonOption.EQUAL;
        if (object instanceof Integer optionInteger && value instanceof Integer currentValue) {
            if (option == ComparisonOption.LOWER) return optionInteger < currentValue;
            else if (option == ComparisonOption.GREATER) return optionInteger > currentValue;
            else if (option == ComparisonOption.GREATER_EQUALS) return optionInteger >= currentValue;
            if (option == ComparisonOption.LOWER_EQUALS) return optionInteger < currentValue;
        }
        if(option == ComparisonOption.EQUAL) return object.equals(value);
        else if(option == ComparisonOption.NOT_EQUAL) return  !object.equals(value);
        return false;
    }
}
