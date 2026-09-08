package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

import java.util.HashMap;
import java.util.Map;

public class GameBoard {

    private Map<Coordinates, Occupant> gameMap;
    private final int mapSize;
    private int currentLevel = 1;

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

    public Player getPlayer(){
        return gameMap.values().stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .findFirst()
                .orElse(null);
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public void incrementLevel() {
        this.currentLevel++;
    }

    public Long countHostileNPC(){
        return gameMap.values().stream()
                .filter(NPC.class::isInstance)
                .map(NPC.class::cast)
                .filter(NPC::isHostile)
                .count();
    }
}