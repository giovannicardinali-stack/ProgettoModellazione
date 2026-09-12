package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

import java.util.HashMap;
import java.util.Map;

public class GameBoard implements IGameBoard {

    private Map<Coordinates, Occupant> gameMap;
    private final int mapSize;
    private int currentLevel = 1;

    public GameBoard(int mapSize) {
        gameMap = new HashMap<>();
        this.mapSize = mapSize;
    }

    @Override
    public boolean isEmpty(){
        return gameMap.isEmpty();
    }

    @Override
    public void clear(){
        gameMap.clear();
    }

    @Override
    public boolean cellIsEmpty(Coordinates coordinates) {
        if(!gameMap.containsKey(coordinates)) {
            return true;
        }
        Occupant occupant = gameMap.get(coordinates);
        return !occupant.isSolid();
    }

    @Override
    public void addOccupant(Coordinates coordinates, Occupant occupant) {
        if(coordinates != null && occupant != null) {
            gameMap.put(coordinates, occupant);
        }
    }

    @Override
    public boolean removeOccupant(Coordinates coordinates) {
        return gameMap.remove(coordinates) != null;
    }

    @Override
    public Occupant getOccupant(Coordinates coordinates) {
        return gameMap.get(coordinates);
    }

    @Override
    public Coordinates getOccupantCoordinates(Occupant occupant) {
        if (occupant == null) return null;

        for (var entry : gameMap.entrySet()) {
            Occupant value = entry.getValue();
            if (value == occupant) {
                return entry.getKey();
            }
            if (value != null && value.getId() != null && value.getId().equals(occupant.getId())) {
                return entry.getKey();
            }
        }
        return null;
    }

    @Override
    public Map<Coordinates, Occupant> getGameMap() {
        return this.gameMap;
    }

    @Override
    public Player getPlayer(){
        return gameMap.values().stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .findFirst()
                .orElse(null);
    }

    @Override
    public void incrementLevel() {
        this.currentLevel++;
    }

    @Override
    public int getLevel() {
        return currentLevel;
    }

    public int getMapSize() { return mapSize; }

    @Override
    public Long countHostileNPC(){
        return gameMap.values().stream()
                .filter(NPC.class::isInstance)
                .map(NPC.class::cast)
                .filter(NPC::isHostile)
                .count();
    }

    @Override
    public void copyOccupantsFrom(IGameBoard otherBoard) {
        if (otherBoard != null && otherBoard.getGameMap() != null) {
            this.gameMap.putAll(otherBoard.getGameMap());
        }
    }

    @Override
    public Coordinates getAdjacentCoordinates(Coordinates currentCoordinates, Direction direction){
        if(currentCoordinates == null || direction == null) {
            return null;
        }

        int x = currentCoordinates.getX();
        int y = currentCoordinates.getY();

        return switch (direction){
            case UP ->  new Coordinates(x, y - 1);
            case DOWN -> new Coordinates(x, y + 1);
            case LEFT -> new Coordinates(x - 1, y);
            case RIGHT -> new Coordinates(x + 1, y);
        };
    }

    @Override
    public boolean isWithinBounds(Coordinates coordinates){
        if(coordinates == null) {
            return false;
        }

        return coordinates.getX() >= 0 && coordinates.getX() < mapSize &&
                coordinates.getY() >= 0 && coordinates.getY() < mapSize;
    }
}