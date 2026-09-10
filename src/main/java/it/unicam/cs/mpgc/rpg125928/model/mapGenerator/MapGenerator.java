package it.unicam.cs.mpgc.rpg125928.model.mapGenerator;

import it.unicam.cs.mpgc.rpg125928.model.GameBoard;
import it.unicam.cs.mpgc.rpg125928.model.IGameBoard;

public interface MapGenerator {

    IGameBoard generateMap();

    IGameBoard generateExistantMap();
}
