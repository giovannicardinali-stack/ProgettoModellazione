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

    public void movePlayer(Direction direction){
        if(direction == Direction.UP) {
            Coordinates coordinates = new Coordinates(playerCoordinates.getX(), playerCoordinates.getY() - 1);
            if(gameBoard.cellIsEmpty(coordinates)) {
                playerCoordinates.setY(playerCoordinates.getY() - 1);
            }

        }
        else if(direction == Direction.DOWN) {
            Coordinates coordinates = new Coordinates(playerCoordinates.getX(), playerCoordinates.getY() + 1);
            if(gameBoard.cellIsEmpty(coordinates)) {
                playerCoordinates.setY(playerCoordinates.getY() + 1);
            }
        }
        else if(direction == Direction.LEFT) {
            Coordinates coordinates = new Coordinates(playerCoordinates.getX() - 1, playerCoordinates.getY());
            if(gameBoard.cellIsEmpty(coordinates)) {
                playerCoordinates.setX(playerCoordinates.getX() - 1);
            }

        }
        else if(direction == Direction.RIGHT) {
            Coordinates coordinates = new Coordinates(playerCoordinates.getX() + 1, playerCoordinates.getY());
            if(gameBoard.cellIsEmpty(coordinates)) {
                playerCoordinates.setX(playerCoordinates.getX() + 1);
            }

        }
        else {
            System.out.println("la cella è occupata");
        }
    }
}
