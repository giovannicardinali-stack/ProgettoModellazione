package it.unicam.cs.mpgc.rpg125928;

import it.unicam.cs.mpgc.rpg125928.view.GameView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage){
        GameView view = new GameView(primaryStage);
        view.showMainMenu();
    }

    static void main(String[] args) {
        launch(args);
    }
}
