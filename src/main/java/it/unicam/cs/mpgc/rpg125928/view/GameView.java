package it.unicam.cs.mpgc.rpg125928.view;

import it.unicam.cs.mpgc.rpg125928.controller.GameController;
import it.unicam.cs.mpgc.rpg125928.controller.InputController;
import it.unicam.cs.mpgc.rpg125928.model.*;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Collectible;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.net.URL;


public class GameView {

    private static final int tileSize = 32;

    private final Stage primaryStage;
    private final GameController gamecontroller;

    private GridPane mapArea;
    private TextArea textArea;

    private VBox leftPanel;
    private ListView<Collectible> inventoryListView;
    private Label healthLabel;
    private Label powerLabel;

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
        Button loadGameButton = new Button("Carica Partita");
        Button exitButton = new Button("Exit");

        newGameButton.setOnAction(e -> showGameView());


        loadGameButton.setOnAction(e -> {
            showGameView();
            gamecontroller.loadGame();
        });

        exitButton.setOnAction(e -> primaryStage.close());

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.getChildren().addAll(titleLabel, newGameButton, loadGameButton, exitButton);

        Scene scene = new Scene(root,400,300);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void showGameView(){

        BorderPane gameRoot = new BorderPane();

        initMapArea();

        gameRoot.setCenter(mapArea);
        gameRoot.setBottom(downBar());

        Player p = gamecontroller.getPlayer();

        gameRoot.setLeft(createLeftPanel(p));

        Scene gameScene = new Scene(gameRoot,900,700);

        InputController inputController = new InputController(gamecontroller);
        inputController.setUpListeners(gameScene);

        primaryStage.setTitle("");
        primaryStage.setScene(gameScene);

        primaryStage.show();
        gameScene.getRoot().requestFocus();

        if(gamecontroller.getGameboard() != null) {
            updateMapView(gamecontroller.getGameboard());
        }
    }

    public void requestFocusOnGame() {
        if (primaryStage.getScene() != null && primaryStage.getScene().getRoot() != null) {
            primaryStage.getScene().getRoot().requestFocus();
        }
    }

    public void initMapArea(){
        mapArea = new GridPane();
        mapArea.setAlignment(Pos.CENTER);
        mapArea.setHgap(0);
        mapArea.setVgap(0);


        mapArea.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        URL resource = getClass().getResource("/images/floor.jpg");

        if (resource != null) {
            String floorURL = resource.toExternalForm();
            mapArea.setStyle("-fx-background-color: #1e1e1e;" +
                    "-fx-background-image: url('" + floorURL + "');" +
                    "-fx-background-repeat: repeat;");
        } else {
            mapArea.setStyle("-fx-background-color: #1e1e1e;");
        }
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

            if(occupant == null) {
                continue;
            }

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

    private VBox createLeftPanel(Player player) {
        leftPanel = new VBox(10);
        leftPanel.setPadding(new Insets(10));
        leftPanel.setPrefWidth(220);
        leftPanel.setStyle("-fx-background-color: #2a2a2a; -fx-border-color: #444444; -fx-border-width: 0 1 0 0;");

        Label statsTitle = new Label("STATISTICHE");
        statsTitle.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px;");

        healthLabel = new Label("Salute: " + player.getHealth());
        healthLabel.setStyle("-fx-text-fill: #ff5555;");

        powerLabel = new Label("Forza: " + player.getPower());
        powerLabel.setStyle("-fx-text-fill: #ffb86c;");

        Label invTitle = new Label("INVENTARIO");
        invTitle.setStyle("-fx-text-fill: #ffffff; -fx-font-weight: bold; -fx-font-size: 14px;");

        inventoryListView = new ListView<>();
        inventoryListView.setPrefHeight(300);
        inventoryListView.setStyle("-fx-control-inner-background: #1e1e1e; -fx-background-color: #1e1e1e;");

        updateInventoryView();

        leftPanel.getChildren().addAll(statsTitle, healthLabel, powerLabel, invTitle, inventoryListView);
        return leftPanel;
    }

    public void updateInventoryView(){
        if(inventoryListView == null || gamecontroller == null){
            return;
        }

        Player player = gamecontroller.getPlayer();

        if(player == null){
            return;
        }

        inventoryListView.getItems().clear();
        inventoryListView.getItems().addAll(player.getInventory());

        inventoryListView.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Collectible item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    HBox box = new HBox(8);
                    box.setAlignment(Pos.CENTER_LEFT);

                    Label nameLabel = new Label(item.getName());
                    nameLabel.setStyle("-fx-text-fill: #ffffff;");

                    Button useBtn = new Button("Usa");
                    useBtn.setStyle("-fx-font-size: 10px;");

                    useBtn.setOnAction(e -> {
                        if (player.useItem(item)) {
                            viewMessage("Hai usato: " + item.getName());
                            updatePlayerStatsUI();
                            updateInventoryView();
                            requestFocusOnGame();
                        }
                    });

                    box.getChildren().addAll(nameLabel, useBtn);
                    setGraphic(box);
                }
            }
        });
    }

    public void updatePlayerStatsUI() {
        if (gamecontroller == null) return;
        Player player = gamecontroller.getPlayer();
        if (player != null && healthLabel != null && powerLabel != null) {
            healthLabel.setText("Salute: " + player.getHealth());
            powerLabel.setText("Forza: " + player.getPower());
        }
    }
}