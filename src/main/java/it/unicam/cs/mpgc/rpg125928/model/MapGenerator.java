package it.unicam.cs.mpgc.rpg125928.model;

public class MapGenerator {

    public GameBoard generateMap(){
        GameBoard gameBoard = new GameBoard(15);

        //add perimeter walls
        for (int i = 0; i < 15; i++) {
            gameBoard.addOccupant(new Coordinates(i, 0), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(i, 14), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(0, i), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(14, i), new Obstacle("Wall", true));
        }

        //add internal walls
        gameBoard.addOccupant(new Coordinates(8, 1), new Obstacle("Wall", true));
        gameBoard.addOccupant(new Coordinates(8, 2), new Obstacle("Wall", true));
        gameBoard.addOccupant(new Coordinates(8, 3), new Obstacle("Wall", true));
        gameBoard.addOccupant(new Coordinates(8, 4), new Obstacle("Wall", true));
        gameBoard.addOccupant(new Coordinates(8, 5), new Obstacle("Wall", true));



        Player player = new Player("player1", true, 10, 10, 4);
        gameBoard.addOccupant(new Coordinates(3, 11), player);

        NPC npc = new NPC("enemy1", true, 8, 10, true, "");
        gameBoard.addOccupant(new Coordinates(11, 3), npc);

        return gameBoard;
    }
}