package at.tobias.schule.textadventurebackend.repo;

import at.tobias.schule.textadventurebackend.model.GameroomModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GameRoomRepository extends JpaRepository<GameroomModel, UUID> {
}
