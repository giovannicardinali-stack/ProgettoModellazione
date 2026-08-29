package it.unicam.cs.mpgc.rpg125928.controller;

import it.unicam.cs.mpgc.rpg125928.model.*;

public class GameController {
    MovementHandler movementHandler;
    InteractionHandler interactionHandler;
    GameBoard gameboard;
    //to do view

    public  GameController(MovementHandler movementHandler,
                           InteractionHandler interactionHandler,
                           GameBoard gameboard) {
        this.movementHandler = movementHandler;
        this.interactionHandler = interactionHandler;
        this.gameboard = gameboard;
    }

    public void handleInteraction(){
        interactionHandler.handleInteraction();
    }

    public void onDirectionChange(Direction direction){

        movementHandler.movePlayer(direction);
        Coordinates newPosition = movementHandler.getPlayerCoordinates();

        //to do update view
    }

    public GameBoard getGameboard() {
        return gameboard;
    }
}
