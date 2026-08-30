package it.unicam.cs.mpgc.rpg125928.view;

import it.unicam.cs.mpgc.rpg125928.controller.GameController;
import it.unicam.cs.mpgc.rpg125928.controller.InputController;
import it.unicam.cs.mpgc.rpg125928.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;


public class GameView {

    private static final int tileSize = 32;

    private final Stage primaryStage;
    private final GameController gamecontroller;

    private GridPane mapArea;
    private TextArea textArea;

    private final TileRenderer tileRenderer;



    public GameView(Stage primaryStage, GameController gamecontroller) {
        this.primaryStage = primaryStage;
        this.gamecontroller = gamecontroller;
        this.tileRenderer = new TileRenderer(tileSize);
    }

    public void showMainMenu(){
        Label titleLabel = new Label("Menù Principale");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Button newGameButton = new Button("Nuova Partita");
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

        initMapArea();

        //putting the elements in the main BorderPane
        gameRoot.setCenter(mapArea);
        gameRoot.setBottom(downBar());

        Scene gameScene = new Scene(gameRoot,900,700);

        InputController inputController = new InputController(gamecontroller);
        inputController.setUpListeners(gameScene);

        primaryStage.setTitle("");
        primaryStage.setScene(gameScene);

        primaryStage.show();
        gameScene.getRoot().requestFocus();

        if(gamecontroller != null && gamecontroller.getGameboard() != null) {
            updateMapView(gamecontroller.getGameboard());
        }

    }

    public void initMapArea(){
        mapArea = new GridPane();
        mapArea.setAlignment(Pos.CENTER);
        mapArea.setHgap(0);
        mapArea.setVgap(0);

        URL resource = getClass().getResource("/images/floor.jpg");
        String floorURL = resource != null ? resource.toExternalForm() : "";

        mapArea.setStyle("-fx-background-color: #1e1e1e;" +
                "-fx-background-image: url('" + floorURL + "');" +
                "-fx-background-repeat: repeat;");
    }

    private VBox downBar(){
        VBox downBar = new VBox(5);
        downBar.setPadding(new Insets(10));
        downBar.setStyle("-fx-background-color: #222222;");

        textArea = new TextArea();
        textArea.setPrefHeight(100);
        textArea.setEditable(false);
        textArea.setText("Benvenuto");

        downBar.getChildren().add(textArea);
        return downBar;
    }

    public void updateMapView(GameBoard gameBoard){
        mapArea.getChildren().clear();

        for(var entry : gameBoard.getGameMap().entrySet()){
            Coordinates coordinates = entry.getKey();
            Occupant occupant = entry.getValue();

            Pane tilePane = tileRenderer.createTilePane(occupant);
            if(tilePane != null){
                mapArea.add(tilePane,coordinates.getX(),coordinates.getY());
            }
        }
    }

    public void viewMessage(String message){
        if(textArea != null){
            textArea.appendText("\n" + message);
        }
    }
}