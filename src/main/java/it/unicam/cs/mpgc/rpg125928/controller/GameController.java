package it.unicam.cs.mpgc.rpg125928.controller;

import it.unicam.cs.mpgc.rpg125928.model.*;

public class GameController {
    MovementHandler movementHandler;
    InteractionHandler interactionHandler;
    //to do view

    public  GameController(MovementHandler movementHandler, InteractionHandler interactionHandler) {
        this.movementHandler = movementHandler;
        this.interactionHandler = interactionHandler;
    }

    public void handleInteraction(){
        interactionHandler.handleInteraction();
    }

    public void onDirectionChange(Direction direction){

        movementHandler.movePlayer(direction);
        Coordinates newPosition = movementHandler.getPlayerCoordinates();

        //to do update view
    }
}
