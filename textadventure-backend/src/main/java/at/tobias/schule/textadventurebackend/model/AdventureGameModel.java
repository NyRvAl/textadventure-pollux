package at.tobias.schule.textadventurebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class AdventureGameModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDateTime lastTimeChanged;
    private String name;
    private long ratingSummedUp;
    private long amountRating;
    private boolean available = true;
}
