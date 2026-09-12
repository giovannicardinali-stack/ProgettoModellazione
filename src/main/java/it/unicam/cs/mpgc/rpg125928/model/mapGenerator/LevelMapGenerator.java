package it.unicam.cs.mpgc.rpg125928.model.mapGenerator;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.GameBoard;
import it.unicam.cs.mpgc.rpg125928.model.IGameBoard;
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
    public IGameBoard generateMap(){
        IGameBoard gameBoard = new GameBoard(MAP_SIZE);
        populateLevel(gameBoard);
        return gameBoard;
    }

    @Override
    public IGameBoard generateExistantMap(){
        IGameBoard gameBoard = new GameBoard(MAP_SIZE);
        populateWalls(gameBoard);
        return gameBoard;
    }
    
    private void populateWalls(IGameBoard gameBoard){
        if (levelConfig != null && levelConfig.getInternalWalls() != null) {
            for (Coordinates wallCoord : levelConfig.getInternalWalls()) {
                gameBoard.addOccupant(wallCoord, new Obstacle("Wall", true));
            }
        }
    }

    public void populateLevel(IGameBoard gameBoard){
        gameBoard.clear();

        populateWalls(gameBoard);

        if (player != null && levelConfig.getPlayerSpawn() != null) {
            Coordinates spawn = levelConfig.getPlayerSpawn();
            player.setCoordinates(spawn);
            gameBoard.addOccupant(spawn, player);

        }

        levelConfig.getEnemies().forEach(gameBoard::addOccupant);
        levelConfig.getItems().forEach(gameBoard::addOccupant);
    }
}
