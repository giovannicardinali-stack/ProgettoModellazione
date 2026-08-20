package model;

import java.util.HashMap;

public class Map {

    private HashMap<Coordinates, Cell> gameMap;

    public Map() {
        gameMap = new HashMap<>();
    }
}
