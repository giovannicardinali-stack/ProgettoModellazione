package it.unicam.cs.mpgc.rpg125928;

import it.unicam.cs.mpgc.rpg125928.controller.GameController;
import it.unicam.cs.mpgc.rpg125928.model.*;
import it.unicam.cs.mpgc.rpg125928.view.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){

        MapGenerator mapGenerator = new MapGenerator();

        GameBoard gameBoard = mapGenerator.generateMap();

        Coordinates playerCoordinates = new Coordinates(3,11);

        Player player = (Player) gameBoard.getOccupant(playerCoordinates);

        MovementHandler movementHandler = new MovementHandler(playerCoordinates, gameBoard);
        InteractionHandler interactionHandler = new InteractionHandler(movementHandler, player, gameBoard);

        GameController gameController = new GameController(movementHandler, interactionHandler, gameBoard);

        GameView view = new GameView(primaryStage, gameController);
        gameController.setGameView(view);
        view.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
