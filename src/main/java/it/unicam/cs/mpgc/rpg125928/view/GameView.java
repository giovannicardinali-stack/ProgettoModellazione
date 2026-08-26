package it.unicam.cs.mpgc.rpg125928.view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class GameView {

    private final Stage primaryStage;


    public GameView(Stage primaryStage){
        this.primaryStage = primaryStage;
    }

    public void showMainMenu(){
        Label titleLabel = new Label();
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button newGameButton = new Button("Nuova Partita");
        //to do button to load an already started game
        Button exitButton = new Button("Exit");

        newGameButton.setOnAction(e -> showGameView());
        exitButton.setOnAction(e -> primaryStage.close());

    }


    public void showGameView(){

    }
}
