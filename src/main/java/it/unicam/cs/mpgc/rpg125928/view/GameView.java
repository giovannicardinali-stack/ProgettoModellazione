package it.unicam.cs.mpgc.rpg125928.view;

import it.unicam.cs.mpgc.rpg125928.controller.GameController;
import it.unicam.cs.mpgc.rpg125928.controller.InputController;
import it.unicam.cs.mpgc.rpg125928.model.GameBoard;
import it.unicam.cs.mpgc.rpg125928.model.Obstacle;
import it.unicam.cs.mpgc.rpg125928.model.Occupant;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class GameView {

    private final Stage primaryStage;
    private final GameController gamecontroller;

    private GridPane mapArea;



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
        mapArea = new GridPane();
        mapArea.setAlignment(Pos.CENTER);

        mapArea.setHgap(0);
        mapArea.setVgap(0);

        mapArea.setStyle("-fx-background-color: #1e1e1e;" +
                "-fx-background-image: url('" + getClass().getResource("/images/floor.jpg").toExternalForm() + "');" +
                "-fx-background-repeat: repeat;");

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

        if(gamecontroller != null && gamecontroller.getGameboard() != null) {
            updateMapView(gamecontroller.getGameboard());
        }

    }

    public void updateMapView(GameBoard gameBoard){
        mapArea.getChildren().clear();

        int tilesize = 32;

        String wallImageUrl = getClass().getResource("/images/wall.png").toExternalForm();


        for (var entry : gameBoard.getGameMap().entrySet()) {
            var coords = entry.getKey();
            Occupant occupant = entry.getValue();

            if(occupant instanceof Obstacle){

                javafx.scene.layout.Pane wallPane = new javafx.scene.layout.Pane();
                wallPane.setPrefSize(tilesize, tilesize);
                wallPane.setMinSize(tilesize, tilesize);
                wallPane.setMaxSize(tilesize, tilesize);

                wallPane.setStyle(
                        "-fx-background-image: url('" + wallImageUrl + "');" +
                                "-fx-background-size: 100% 100%;" +
                                "-fx-background-repeat: no-repeat;"
                );

                mapArea.add(wallPane,coords.getX(),coords.getY());
            }
        }

    }
}