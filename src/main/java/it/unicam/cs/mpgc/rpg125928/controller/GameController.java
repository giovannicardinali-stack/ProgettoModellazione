package it.unicam.cs.mpgc.rpg125928.controller;

import it.unicam.cs.mpgc.rpg125928.model.*;
import it.unicam.cs.mpgc.rpg125928.view.GameView;

public class GameController {
    MovementHandler movementHandler;
    InteractionHandler interactionHandler;
    GameBoard gameboard;
    private GameView gameView;

    public  GameController(MovementHandler movementHandler,
                           InteractionHandler interactionHandler,
                           GameBoard gameboard) {
        this.movementHandler = movementHandler;
        this.interactionHandler = interactionHandler;
        this.gameboard = gameboard;
    }
    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void handleInteraction(){
        interactionHandler.handleInteraction();
    }

    public void onDirectionChange(Direction direction){

        movementHandler.movePlayer(direction);

        if(gameView != null){
            gameView.updateMapView(gameboard);
        }

        Coordinates newPosition = movementHandler.getPlayerCoordinates();

    }

    public GameBoard getGameboard() {
        return gameboard;
    }
}
