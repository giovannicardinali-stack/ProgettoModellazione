package it.unicam.cs.mpgc.rpg125928.controller;

import it.unicam.cs.mpgc.rpg125928.model.*;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelConfig;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelConfigFactory;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelMapGenerator;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;
import it.unicam.cs.mpgc.rpg125928.util.persistence.PersistanceManager;
import it.unicam.cs.mpgc.rpg125928.view.GameView;

public class GameController {
    private final MovementHandler movementHandler;
    private final InteractionHandler interactionHandler;
    private final IGameBoard gameboard;
    private GameView gameView;
    private final PersistanceManager persistenceManager;
    private Player player;

    public GameController(MovementHandler movementHandler,
                           InteractionHandler interactionHandler,
                           IGameBoard gameboard,
                           PersistanceManager gamePersistenceManager,
                          Player player) {
        this.movementHandler = movementHandler;
        this.interactionHandler = interactionHandler;
        this.gameboard = gameboard;
        this.persistenceManager = gamePersistenceManager;
        this.player = player;
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

        int currentLevel = gameboard.getLevel();
        if(LevelConfigFactory.hasNextLevel(currentLevel)){
            gameboard.incrementLevel();
            int nextLevel = gameboard.getLevel();
            LevelConfig nextLevelConfig = LevelConfigFactory.getLevelConfig(nextLevel);
            LevelMapGenerator mapGenerator = new LevelMapGenerator(nextLevelConfig, player);
            mapGenerator.populateLevel(gameboard);
            movementHandler.setPlayerCoordinates(nextLevelConfig.getPlayerSpawn());
            if(persistenceManager != null){
                persistenceManager.setMapGenerator(mapGenerator);
            }
            if(gameView != null){
                gameView.viewMessage("Hai eliminato tutti i nemici! Benvenuto al Piano " + nextLevel);
                gameView.updateMapView(gameboard);
                gameView.updateInventoryView();
                gameView.updatePlayerStatsUI();
            }
            saveCurrentGame();
        }
        else {
            if(gameView != null){
                gameView.viewMessage("COMPLIMENTI! Hai sconfitto tutti i nemici e completato il gioco!");
            }
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
        if (this.persistenceManager != null && this.gameboard != null) {
            persistenceManager.saveGame(this.gameboard);
        }
    }

    public void loadGame() {
        if (this.persistenceManager != null) {
            IGameBoard loadedBoard = persistenceManager.loadGame();

            if (loadedBoard != null && !loadedBoard.isEmpty()) {

                this.gameboard.clear();
                this.gameboard.copyOccupantsFrom(loadedBoard);

                Player loadedPlayer = this.gameboard.getPlayer();

                if(loadedPlayer != null){
                    this.player = loadedPlayer;

                    this.interactionHandler.setPlayer(loadedPlayer);

                    Coordinates playerCoords = this.gameboard.getOccupantCoordinates(loadedPlayer);
                    if (playerCoords != null) {
                        this.movementHandler.setPlayerCoordinates(playerCoords);
                    }
                }
                if (gameView != null) {
                    gameView.updateMapView(this.gameboard);
                    gameView.viewMessage("Partita caricata con successo!");
                    gameView.requestFocusOnGame();
                }
            }
            else {
                if (gameView != null) {
                    gameView.viewMessage("Nessun salvataggio trovato o mappa vuota.");
                    gameView.updateMapView(gameboard);
                }
            }
        }
    }

    public IGameBoard getGameboard() {
        return gameboard;
    }

    public Player getPlayer() {
        return player;
    }

}