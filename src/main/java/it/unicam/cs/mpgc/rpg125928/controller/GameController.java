package it.unicam.cs.mpgc.rpg125928.controller;

import it.unicam.cs.mpgc.rpg125928.model.*;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelConfig;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelConfigFactory;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelMapGenerator;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;
import it.unicam.cs.mpgc.rpg125928.view.GameView;

import java.util.Map;

public class GameController {
    private final MovementHandler movementHandler;
    private final InteractionHandler interactionHandler;
    private final GameBoard gameboard;
    private GameView gameView;
    private GamePersistenceManager gamePersistenceManager;
    private Player player;
    private LevelConfigFactory levelConfigFactory;

    public GameController(MovementHandler movementHandler,
                           InteractionHandler interactionHandler,
                           GameBoard gameboard,
                           GamePersistenceManager gamePersistenceManager,
                          Player player,
                          LevelConfigFactory levelConfigFactory) {
        this.movementHandler = movementHandler;
        this.interactionHandler = interactionHandler;
        this.gameboard = gameboard;
        this.gamePersistenceManager = gamePersistenceManager;
        this.player = player;
        this.levelConfigFactory = levelConfigFactory;
    }

    public void setGameView(GameView gameView) {
        this.gameView = gameView;
    }

    public void handleInteraction(){
        String message = interactionHandler.handleInteraction();

        if("LEVEL_CLEARED".equals(message)){
            advanceToNextLevel();
        }
        else if(message != null && gameView != null){
            gameView.viewMessage(message);
            gameView.updateMapView(gameboard);
            gameView.updateInventoryView();
            gameView.updatePlayerStatsUI();

            saveCurrentGame();
        }
    }

    private void advanceToNextLevel() {
        gameboard.incrementLevel();
        int nextLevel = gameboard.getLevel();

        LevelConfig nextLevelConfig = levelConfigFactory.getLevelConfig(nextLevel);

        LevelMapGenerator mapGenerator = new LevelMapGenerator(nextLevelConfig, player);

        mapGenerator.populateLevel(gameboard);

        movementHandler.setPlayerCoordinates(nextLevelConfig.getPlayerSpawn());

        gamePersistenceManager.setMapGenerator(mapGenerator);

        if(gameView != null){
            gameView.viewMessage("Hai eliminato tutti i nemici! Benvenuto al Piano " + nextLevel);
            gameView.updateMapView(gameboard);
            gameView.updateInventoryView();
            gameView.updatePlayerStatsUI();
        }

        saveCurrentGame();
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

    public void loadGame() {
        if (this.gamePersistenceManager != null) {
            GameBoard loadedBoard = gamePersistenceManager.loadGame();

            if (loadedBoard != null && !loadedBoard.getGameMap().isEmpty()) {

                this.gameboard.getGameMap().clear();
                this.gameboard.getGameMap().putAll(loadedBoard.getGameMap());

                for (Map.Entry<Coordinates, Occupant> entry : this.gameboard.getGameMap().entrySet()) {
                    if (entry.getValue() instanceof Player) {
                        this.movementHandler.setPlayerCoordinates(entry.getKey());
                        break;
                    }
                }
                if (gameView != null) {
                    gameView.updateMapView(this.gameboard);
                    gameView.viewMessage("Partita caricata con successo!");
                    gameView.requestFocusOnGame();
                }
            } else {
                if (gameView != null) {
                    gameView.viewMessage("Nessun salvataggio trovato o mappa vuota.");
                }
            }
        }
    }

    public GameBoard getGameboard() {
        return gameboard;
    }

    public Player getPlayer() {
        return player;
    }


}