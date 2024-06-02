package at.tobias.schule.textadventurebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
@Entity
public class GameroomModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "adventureGameModel_id")
    private AdventureGameModel adventureGameModel;

    private String owner;



}

