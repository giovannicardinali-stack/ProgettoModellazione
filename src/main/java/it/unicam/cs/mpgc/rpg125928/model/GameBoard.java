package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameBoard {

    private Map<Coordinates, Occupant> gameMap;
    private final int mapSize;
    private int currentLevel = 1;

    public GameBoard(int mapSize) {
        gameMap = new HashMap<>();
        this.mapSize = mapSize;
    }

    public boolean isEmpty(){
        return gameMap.isEmpty();
    }

    public void clear(){
        gameMap.clear();
    }

    public boolean cellIsEmpty(Coordinates coordinates) {
        if(!gameMap.containsKey(coordinates)) {
            return true;
        }
        Occupant occupant = gameMap.get(coordinates);

        return !occupant.isSolid();
    }

    public void addOccupant(Coordinates coordinates, Occupant occupant) {
        if(coordinates != null && occupant != null) {
            gameMap.put(coordinates, occupant);
        }
    }

    public boolean removeOccupant(Coordinates coordinates) {
        return gameMap.remove(coordinates) != null;
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
        return Collections.unmodifiableMap(gameMap);
    }

    public Player getPlayer(){
        return gameMap.values().stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .findFirst()
                .orElse(null);
    }

    public void incrementLevel() {
        this.currentLevel++;
    }

    public int getLevel() {
        return currentLevel;
    }

    public int getMapSize() { return mapSize; }

    public Long countHostileNPC(){
        return gameMap.values().stream()
                .filter(NPC.class::isInstance)
                .map(NPC.class::cast)
                .filter(NPC::isHostile)
                .count();
    }

    public void putAll(Map<Coordinates, Occupant> newMap){
        if (newMap != null) {
            gameMap.putAll(newMap);
        }
    }

    public int getOccupantCount() {
        return gameMap.size();
    }

    public void copyOccupantsFrom(GameBoard otherBoard) {
        if (otherBoard != null && otherBoard.getGameMap() != null) {
            this.gameMap.putAll(otherBoard.getGameMap());
        }
    }
}