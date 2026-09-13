package it.unicam.cs.mpgc.rpg125928.controller;

import it.unicam.cs.mpgc.rpg125928.controller.handler.InteractionHandler;
import it.unicam.cs.mpgc.rpg125928.controller.handler.MovementHandler;
import it.unicam.cs.mpgc.rpg125928.model.*;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelConfig;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelConfigFactory;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelMapGenerator;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Collectible;
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

    public GameView getGameView() {
        return gameView;
    }

    public void handleInteraction() {
        String message = interactionHandler.handleInteraction();

        if ("LEVEL_CLEARED".equals(message)) {
            advanceToNextLevel();
        } else if (message != null) {
            notifyViewChanges(message);
        }
    }

    private void advanceToNextLevel() {
        int currentLevel = gameboard.getLevel();

        if (LevelConfigFactory.hasNextLevel(currentLevel)) {
            int nextLevel = incrementAndGetLevel();
            setupNextLevelEnvironment(nextLevel);
            saveCurrentGame();
            notifyViewChanges("Hai eliminato tutti i nemici! Benvenuto al Piano " + nextLevel);
        } else {
            notifyMessageOnly("COMPLIMENTI! Hai sconfitto tutti i nemici e completato il gioco!");
        }
    }

    private int incrementAndGetLevel() {
        gameboard.incrementLevel();
        int nextLevel = gameboard.getLevel();
        player.setCurrentLevel(nextLevel);
        return nextLevel;
    }

    private void setupNextLevelEnvironment(int level) {
        LevelConfig config = LevelConfigFactory.getLevelConfig(level);
        LevelMapGenerator mapGenerator = new LevelMapGenerator(config, player);
        mapGenerator.populateLevel(gameboard);
        movementHandler.setPlayerCoordinates(config.getPlayerSpawn());

        if (persistenceManager != null) {
            persistenceManager.setMapGenerator(mapGenerator);
        }
    }

    public void onDirectionChange(Direction direction) {
        boolean moved = movementHandler.movePlayer(direction);
        if (moved && gameView != null) {
            gameView.updateMapView(gameboard);
        }
    }

    public void saveCurrentGame() {
        if (this.persistenceManager != null && this.gameboard != null) {
            persistenceManager.saveGame(this.gameboard);
        }
    }

    public void loadGame() {
        if (this.persistenceManager == null) return;

        IGameBoard loadedBoard = persistenceManager.loadGame();
        if (loadedBoard == null || loadedBoard.isEmpty()) return;

        applyLoadedBoardState(loadedBoard);
        notifyGameLoadedSuccessfully();
    }

    private void applyLoadedBoardState(IGameBoard loadedBoard) {
        this.gameboard.clear();
        this.gameboard.copyOccupantsFrom(loadedBoard);

        Player loadedPlayer = this.gameboard.getPlayer();
        if (loadedPlayer == null) return;

        this.player = loadedPlayer;
        syncBoardLevelWithPlayer(loadedPlayer);

        this.interactionHandler.setPlayer(loadedPlayer);
        this.movementHandler.setLoadedPlayer(loadedPlayer);

        Coordinates playerCoords = this.gameboard.getOccupantCoordinates(loadedPlayer);
        if (playerCoords != null) {
            this.movementHandler.setPlayerCoordinates(playerCoords);
        }
    }

    private void syncBoardLevelWithPlayer(Player loadedPlayer) {
        while (this.gameboard.getLevel() < loadedPlayer.getCurrentLevel()) {
            this.gameboard.incrementLevel();
        }
    }

    public void handleItemUse(Collectible item) {
        if (item == null || player == null) return;

        boolean success = player.useItem(item);
        if (success) {
            notifyViewChanges("Hai usato: " + item.getName());
        }
    }

    private void notifyViewChanges(String message) {
        if (gameView == null) return;
        gameView.viewMessage(message);
        gameView.updateMapView(gameboard);
        gameView.updateInventoryView();
        gameView.updatePlayerStatsUI();
    }

    private void notifyMessageOnly(String message) {
        if (gameView != null) {
            gameView.viewMessage(message);
        }
    }

    private void notifyGameLoadedSuccessfully() {
        if (gameView == null) return;
        gameView.updateMapView(this.gameboard);
        gameView.updatePlayerStatsUI();
        gameView.updateInventoryView();
        gameView.viewMessage("Partita caricata con successo!");
        gameView.requestFocusOnGame();
    }

    public IGameBoard getGameboard() {
        return gameboard;
    }

    public Player getPlayer() {
        return player;
    }
}