package it.unicam.cs.mpgc.rpg125928;

import it.unicam.cs.mpgc.rpg125928.controller.GameController;
import it.unicam.cs.mpgc.rpg125928.model.*;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.*;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;
import it.unicam.cs.mpgc.rpg125928.util.HibernateUtil;
import it.unicam.cs.mpgc.rpg125928.view.GameView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){

        Player player = new Player("Player 1", true, 10, 10, 4);

        LevelConfigFactory levelConfigFactory = new LevelConfigFactory();

        LevelConfig level1Config = LevelConfigFactory.getLevelConfig(1);

        MapGenerator mapGenerator = new LevelMapGenerator(level1Config, player);

        GameBoard gameBoard = mapGenerator.generateMap();

        Coordinates playerCoordinates = level1Config.getPlayerSpawn();

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        GamePersistenceManager gamePersistenceManager = new GamePersistenceManager(sessionFactory, mapGenerator);

        MovementHandler movementHandler = new MovementHandler(playerCoordinates, gameBoard);
        InteractionHandler interactionHandler = new InteractionHandler(movementHandler, player, gameBoard);

        GameController gameController = new GameController(movementHandler
                , interactionHandler
                , gameBoard
                , gamePersistenceManager
                , player
        ,levelConfigFactory);

        GameView view = new GameView(primaryStage, gameController);
        gameController.setGameView(view);
        view.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
