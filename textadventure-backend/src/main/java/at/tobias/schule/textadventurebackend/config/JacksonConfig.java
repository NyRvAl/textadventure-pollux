package at.tobias.schule.textadventurebackend.config;

import at.tobias.schule.textadventurebackend.deserialize.TextAdventureDeserializer;
import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;
import at.tobias.schule.textadventurebackend.service.ActionManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {
    private final ActionManager actionService;

    @Autowired
    public JacksonConfig(ActionManager actionService) {
        this.actionService = actionService;
    }

    @Bean
    public ObjectMapper createObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();

        SimpleModule module = new SimpleModule();
        module.addDeserializer(TextActionData.class, new TextAdventureDeserializer(actionService));
        mapper.registerModule(new JavaTimeModule());

        mapper.registerModule(module);

        return mapper;
    }
}
