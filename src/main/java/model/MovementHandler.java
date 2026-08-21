package model;

public class MovementHandler {
    private Coordinates playerCoordinates;
    private GameBoard gameBoard;

    public MovementHandler(Coordinates playerCoordinates, GameBoard gameBoard) {
        this.playerCoordinates = playerCoordinates;
        this.gameBoard = gameBoard;
    }


    public Coordinates getPlayerCoordinates() {
        return playerCoordinates;
    }

    public void movePlayer(Direction direction) {
        if(direction == Direction.UP) {
            playerCoordinates.setY(playerCoordinates.getY() - 1);
        }
    }


    public void updateGameBoard(){

    }
}
