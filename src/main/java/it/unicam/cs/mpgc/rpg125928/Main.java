package it.unicam.cs.mpgc.rpg125928;

import it.unicam.cs.mpgc.rpg125928.controller.GameController;
import it.unicam.cs.mpgc.rpg125928.model.*;
import it.unicam.cs.mpgc.rpg125928.view.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        Player player = new Player("player1", true, 10, 10, 4);

        GameBoard gameBoard = new GameBoard(15);
        Coordinates playerCoordinates = new Coordinates(3,11);
        MovementHandler movementHandler = new MovementHandler(playerCoordinates, gameBoard);
        InteractionHandler interactionHandler = new InteractionHandler(movementHandler, player, gameBoard);

        GameController gameController = new GameController(movementHandler, interactionHandler);

        GameView view = new GameView(primaryStage, gameController);
        view.showMainMenu();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
