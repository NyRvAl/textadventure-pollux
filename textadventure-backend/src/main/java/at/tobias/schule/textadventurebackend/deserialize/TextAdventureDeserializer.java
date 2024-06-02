package at.tobias.schule.textadventurebackend.deserialize;

import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;
import at.tobias.schule.textadventurebackend.service.ActionManager;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class TextAdventureDeserializer extends JsonDeserializer<TextActionData> {
    public ActionManager actionManager;
    @Autowired
    public TextAdventureDeserializer(ActionManager actionManager){
        this.actionManager = actionManager;
    }
    @Override
    public TextActionData deserialize(JsonParser parser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        final JsonNode node = parser.getCodec().readTree(parser);
        final ObjectMapper mapper = (ObjectMapper)parser.getCodec();


        String className = node.get("action").asText();

        Class<? extends TextActionData> clz = actionManager.getClassByKey(className);

        // Deserialize the JSON to the appropriate class
        return  mapper.treeToValue(node, clz);
    }
}
