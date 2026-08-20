package model;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {

    private Map<Coordinates, Cell> gameMap;

    public GameBoard() {
        gameMap = new HashMap<>();
    }
}
