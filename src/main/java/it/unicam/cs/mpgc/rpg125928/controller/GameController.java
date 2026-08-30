package it.unicam.cs.mpgc.rpg125928.controller;

import it.unicam.cs.mpgc.rpg125928.model.*;
import it.unicam.cs.mpgc.rpg125928.view.GameView;

public class GameController {
    private final MovementHandler movementHandler;
    private final InteractionHandler interactionHandler;
    private final GameBoard gameboard;
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
        String message = interactionHandler.handleInteraction();

        if(message != null && gameView != null){
            gameView.viewMessage(message);
        }
    }

    public void onDirectionChange(Direction direction){

        boolean moved = movementHandler.movePlayer(direction);

        if(moved){
            if(gameView != null){
                gameView.updateMapView(gameboard);
            }
        }


    }

    public GameBoard getGameboard() {
        return gameboard;
    }
}
