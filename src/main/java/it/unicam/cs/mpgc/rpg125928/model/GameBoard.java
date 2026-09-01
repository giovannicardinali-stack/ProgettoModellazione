package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {

    private Map<Coordinates, Occupant> gameMap;
    private final int mapSize;

    public GameBoard(int mapSize) {
        gameMap = new HashMap<>();
        this.mapSize = mapSize;
    }

    public boolean cellIsEmpty(Coordinates coordinates) {
        if(!gameMap.containsKey(coordinates)) {
            return true;
        }
        Occupant occupant = gameMap.get(coordinates);

        return !occupant.isSolid();
    }

    public void addOccupant(Coordinates coordinates, Occupant occupant) {
        if(!gameMap.containsKey(coordinates)) {
            gameMap.put(coordinates, occupant);
        }
    }

    public Coordinates getOccupantCoordinates(Occupant occupant) {
        for (var entry : gameMap.entrySet()) {
            if (entry.getValue().equals(occupant)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public Occupant getOccupant(Coordinates coordinates) {
        return gameMap.get(coordinates);
    }

    public Map<Coordinates, Occupant> getGameMap() {
        return gameMap;
    }

    public boolean removeOccupant(Coordinates coordinates) {
        return gameMap.remove(coordinates) != null;
    }
}