package it.unicam.cs.mpgc.rpg125928.view;

import it.unicam.cs.mpgc.rpg125928.controller.GameController;
import it.unicam.cs.mpgc.rpg125928.controller.InputController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GameView {

    private final Stage primaryStage;
    private final GameController gamecontroller;


    public GameView(Stage primaryStage, GameController gamecontroller) {
        this.primaryStage = primaryStage;
        this.gamecontroller = gamecontroller;
    }

    public void showMainMenu(){
        Label titleLabel = new Label("Menù Principale");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button newGameButton = new Button("Nuova Partita");
        //to do button to load an already started game
        Button exitButton = new Button("Exit");

        newGameButton.setOnAction(e -> showGameView());
        exitButton.setOnAction(e -> primaryStage.close());

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titleLabel,newGameButton,exitButton);

        Scene scene = new Scene(root,400,300);

        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void showGameView(){

        BorderPane gameRoot = new BorderPane();

        //center zone: map
        GridPane mapArea = new GridPane();
        mapArea.setAlignment(Pos.CENTER);
        mapArea.setStyle("-fx-background-color: #1e1e1e;");

        //downBar
        VBox downBar = new VBox(5);
        downBar.setPadding(new Insets(10));
        downBar.setStyle("-fx-background-color: #222222;");

        //text for in-game messages
        TextArea textArea = new TextArea();
        textArea.setPrefHeight(100);
        textArea.setEditable(false);
        textArea.setText("Benvenuto");

        //putting the elements in the main BorderPane
        gameRoot.setCenter(mapArea);
        gameRoot.setBottom(downBar);


        Scene gameScene = new Scene(gameRoot,900,600);

        InputController inputController = new InputController(gamecontroller);
        inputController.setUpListeners(gameScene);

        primaryStage.setTitle("");
        primaryStage.setScene(gameScene);







    }
}