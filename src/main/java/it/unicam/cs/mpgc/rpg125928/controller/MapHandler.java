package it.unicam.cs.mpgc.rpg125928.controller;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.GameBoard;
import it.unicam.cs.mpgc.rpg125928.model.Obstacle;

public class MapHandler {

    public GameBoard generateMap(){
        GameBoard gameBoard = new GameBoard(15);

        for (int i = 0; i < 15; i++) {
            gameBoard.addOccupant(new Coordinates(i, 0), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(i, 14), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(0, i), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(14, i), new Obstacle("Wall", true));

        }

        return gameBoard;
    }
}