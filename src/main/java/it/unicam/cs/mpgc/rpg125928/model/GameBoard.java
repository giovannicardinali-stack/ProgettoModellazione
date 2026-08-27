package it.unicam.cs.mpgc.rpg125928.model;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {

    private Map<Coordinates, Cell> gameMap;
    private final int mapSize;

    public GameBoard(int MapSize) {
        gameMap = new HashMap<>();
        this.mapSize = gameMap.size();
    }

    public boolean cellIsEmpty(Coordinates coordinates) {
        if(!gameMap.containsKey(coordinates)) {
            return false;
        }
        Cell cell = gameMap.get(coordinates);

        if(cell.getOccupant() == null) {
            return true;
        }
        return !cell.getOccupant().isSolid();
    }

    public Cell getCell(Coordinates coordinates) {
        return gameMap.get(coordinates);
    }

    public Map<Coordinates, Cell> getGameMap() {
        return gameMap;
    }
}