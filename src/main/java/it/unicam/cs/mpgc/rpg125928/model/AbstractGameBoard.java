package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

import java.util.Map;

public interface AbstractGameBoard {

    boolean isEmpty();

    void clear();

    boolean cellIsEmpty(Coordinates coordinates);

    void addOccupant(Coordinates coordinates, Occupant occupant);

    boolean removeOccupant(Coordinates coordinates);

    Occupant getOccupant(Coordinates coordinates);

    Coordinates getOccupantCoordinates(Occupant occupant);

    Map<Coordinates, Occupant> getGameMap();

    Player getPlayer();

    void incrementLevel();

    int getLevel();

    int getMapSize();

    Long countHostileNPC();

    void copyOccupantsFrom(GameBoard otherBoard);
}
