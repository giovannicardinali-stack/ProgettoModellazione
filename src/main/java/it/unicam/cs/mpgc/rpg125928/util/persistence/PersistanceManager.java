package it.unicam.cs.mpgc.rpg125928.util.persistence;

import it.unicam.cs.mpgc.rpg125928.model.IGameBoard;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.MapGenerator;

public interface PersistanceManager {

    void saveGame(IGameBoard gameboard);

    IGameBoard loadGame();

    void setMapGenerator(MapGenerator mapGenerator);
}
