package it.unicam.cs.mpgc.rpg125928.util.persistence;

import it.unicam.cs.mpgc.rpg125928.model.GameBoard;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.MapGenerator;

public interface PersistanceManager {

    void saveGame(GameBoard gameboard);

    GameBoard loadGame();

    void setMapGenerator(MapGenerator mapGenerator);
}
