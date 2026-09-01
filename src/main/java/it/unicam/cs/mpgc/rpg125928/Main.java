package it.unicam.cs.mpgc.rpg125928;

import it.unicam.cs.mpgc.rpg125928.controller.GameController;
import it.unicam.cs.mpgc.rpg125928.model.*;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.DefaultMapGenerator;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.MapGenerator;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;
import it.unicam.cs.mpgc.rpg125928.util.HibernateUtil;
import it.unicam.cs.mpgc.rpg125928.view.GameView;
import javafx.application.Application;
import javafx.stage.Stage;
import org.hibernate.SessionFactory;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){

        MapGenerator mapGenerator = new DefaultMapGenerator();

        GameBoard gameBoard = mapGenerator.generateMap();

        Coordinates playerCoordinates = new Coordinates(3,11);

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();

        GamePersistenceManager gamePersistenceManager = new GamePersistenceManager(sessionFactory, mapGenerator);

        Player player = (Player) gameBoard.getOccupant(playerCoordinates);

        MovementHandler movementHandler = new MovementHandler(playerCoordinates, gameBoard);
        InteractionHandler interactionHandler = new InteractionHandler(movementHandler, player, gameBoard);

        GameController gameController = new GameController(movementHandler, interactionHandler, gameBoard, gamePersistenceManager);

        GameView view = new GameView(primaryStage, gameController);
        gameController.setGameView(view);
        view.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
