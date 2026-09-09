package it.unicam.cs.mpgc.rpg125928.model.mapGenerator;

import it.unicam.cs.mpgc.rpg125928.model.GameBoard;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

public interface MapGenerator {

    GameBoard generateMap();

    GameBoard generateExistantMap();
}
