package it.unicam.cs.mpgc.rpg125928.controller;

import it.unicam.cs.mpgc.rpg125928.model.*;
import it.unicam.cs.mpgc.rpg125928.view.GameView;

public class GameController {
    private final MovementHandler movementHandler;
    private final InteractionHandler interactionHandler;
    private final GameBoard gameboard;
    private GameView gameView;
    private GamePersistenceManager gamePersistenceManager;

    public  GameController(MovementHandler movementHandler,
                           InteractionHandler interactionHandler,
                           GameBoard gameboard,
                           GamePersistenceManager gamePersistenceManager) {
        this.movementHandler = movementHandler;
        this.interactionHandler = interactionHandler;
        this.gameboard = gameboard;
        this.gamePersistenceManager = gamePersistenceManager;
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

    public void saveCurrentGame() {
        if (this.gamePersistenceManager != null && this.gameboard != null) {
            gamePersistenceManager.saveGame(this.gameboard);
            if (this.gameView != null) {
                gameView.viewMessage("Partita salvata con successo!");
            }
        }
    }

    public GameBoard getGameboard() {
        return gameboard;
    }
}
