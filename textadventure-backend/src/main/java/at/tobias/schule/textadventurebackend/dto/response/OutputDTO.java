package at.tobias.schule.textadventurebackend.dto.response;

import at.tobias.schule.textadventurebackend.dto.adventure.TextActionData;

import java.util.List;

public record OutputDTO (List<TextActionData> output){
}
