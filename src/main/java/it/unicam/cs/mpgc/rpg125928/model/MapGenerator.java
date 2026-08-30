package it.unicam.cs.mpgc.rpg125928.model;

public class MapGenerator {

    private static final int MAP_SIZE = 15;

    public GameBoard generateMap(){
        GameBoard gameBoard = new GameBoard(MAP_SIZE);

        generatePerimeterWalls(gameBoard);
        generateInternalWalls(gameBoard);
        generateEntities(gameBoard);

        return gameBoard;
    }

    private void generateInternalWalls(GameBoard gameBoard){
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
}