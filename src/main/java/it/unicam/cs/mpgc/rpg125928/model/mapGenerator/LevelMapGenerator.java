package it.unicam.cs.mpgc.rpg125928.model.mapGenerator;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.GameBoard;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Obstacle;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

public class LevelMapGenerator implements  MapGenerator {

    private static final int MAP_SIZE = 15;
    private final LevelConfig levelConfig;
    private final Player player;

    public LevelMapGenerator(LevelConfig levelConfig, Player player) {
        this.levelConfig = levelConfig;
        this.player = player;
    }

    @Override
    public GameBoard generateMap(){
        GameBoard gameBoard = new GameBoard(MAP_SIZE);

    };

    @Override
    public GameBoard generateExistantMap(){
        GameBoard gameBoard = new GameBoard(MAP_SIZE);
        generatePerimeterWalls(gameBoard);
        return gameBoard;
    };

    @Override
    public void populateLevel(GameBoard gameBoard, int level, Player player){

    };

    public void populateBoard(GameBoard gameBoard){
        gameBoard.getGameMap().clear();

        generatePerimeterWalls(gameBoard);

        for (Coordinates wallCoord : levelConfig.getInternalWalls()) {
            gameBoard.addOccupant(wallCoord, new Obstacle("Wall", true));
        }

        if (player != null && levelConfig.getPlayerSpawn() != null) {
            gameBoard.addOccupant(levelConfig.getPlayerSpawn(), player);
        }

        levelConfig.getEnemies().forEach(gameBoard::addOccupant);
        levelConfig.getItems().forEach(gameBoard::addOccupant);
    }

    private void generatePerimeterWalls(GameBoard gameBoard) {
        int maxIndex = MAP_SIZE - 1;
        for (int i = 0; i < MAP_SIZE; i++) {
            gameBoard.addOccupant(new Coordinates(i, 0), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(i, maxIndex), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(0, i), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(maxIndex, i), new Obstacle("Wall", true));
        }
    }
}
