package it.unicam.cs.mpgc.rpg125928.model.mapGenerator;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.GameBoard;
import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Obstacle;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

public class DefaultMapGenerator implements MapGenerator {

    private static final int MAP_SIZE = 15;

    @Override
    public GameBoard generateMap(){
        GameBoard gameBoard = new GameBoard(MAP_SIZE);

        generatePerimeterWalls(gameBoard);
        generateInternalWalls(gameBoard);
        generateEntities(gameBoard);

        return gameBoard;
    }

    public void generateInternalWalls(GameBoard gameBoard){
        for (int y = 1; y <= 5; y++) {
            gameBoard.addOccupant(new Coordinates(8, y), new Obstacle("Wall", true));
        }
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

    private void generateEntities(GameBoard gameBoard) {
        Player player = new Player("player1", true, 10, 10, 4);
        gameBoard.addOccupant(new Coordinates(3, 11), player);

        NPC npc = new NPC("enemy1", true, 8, 5, true, "");
        gameBoard.addOccupant(new Coordinates(11, 3), npc);
    }

    @Override
    public GameBoard generateExistantMap(){
        GameBoard gameBoard = new GameBoard(MAP_SIZE);

        generatePerimeterWalls(gameBoard);
        generateInternalWalls(gameBoard);

        return gameBoard;
    }
}