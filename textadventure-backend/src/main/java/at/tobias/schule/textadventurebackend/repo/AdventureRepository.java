package at.tobias.schule.textadventurebackend.repo;

import at.tobias.schule.textadventurebackend.model.AdventureGameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdventureRepository extends JpaRepository<AdventureGameModel,Long> {
    AdventureGameModel findByName(String name);
    AdventureGameModel findById(long id);
}
