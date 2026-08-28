package it.unicam.cs.mpgc.rpg125928.controller;


import it.unicam.cs.mpgc.rpg125928.model.Direction;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class InputController {

    private final GameController gameController;

    public  InputController(GameController gameController) {
        this.gameController = gameController;
    }

    public void setUpListeners(Scene scene) {
        scene.setOnKeyPressed(e -> {
            KeyCode keyCode = e.getCode();

            switch (keyCode) {
                case UP, W -> gameController.onDirectionChange(Direction.UP);
                case DOWN, S -> gameController.onDirectionChange(Direction.DOWN);
                case LEFT, A -> gameController.onDirectionChange(Direction.LEFT);
                case RIGHT, D -> gameController.onDirectionChange(Direction.RIGHT);
                case E, SPACE -> gameController.handleInteraction();

                default -> {

                }
            }
        });
    }
}