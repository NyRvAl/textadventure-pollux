package at.tobias.schule.textadventurebackend.service;

import at.tobias.schule.textadventurebackend.model.AdventureGameModel;

public interface IAdventureService {

    void save(AdventureGameModel gameModel);
    AdventureGameModel findByName(String name);
    AdventureGameModel findById(long id);

    void update(AdventureGameModel gameModel);

}
