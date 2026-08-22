package model;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {

    private Map<Coordinates, Cell> gameMap;

    public GameBoard() {
        gameMap = new HashMap<>();
    }

    public boolean cellIsEmpty(Coordinates coordinates) {
        if(gameMap.containsKey(coordinates)) {
            if(gameMap.get(coordinates).getOccupant().isSolid()){
                return false;
            }
        }
        return true;
    }
}