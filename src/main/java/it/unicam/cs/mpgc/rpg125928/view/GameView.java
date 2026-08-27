package it.unicam.cs.mpgc.rpg125928.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GameView {

    private final Stage primaryStage;


    public GameView(Stage primaryStage){
        this.primaryStage = primaryStage;
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
        Pane mapArea = new Pane();
        mapArea.setStyle("-fx-background-color: #1e1e1e;");

        //downBar
        VBox downBar = new VBox(5);
        downBar.setPadding(new Insets(10));
        downBar.setStyle("-fx-background-color: #222222;");

    }
}