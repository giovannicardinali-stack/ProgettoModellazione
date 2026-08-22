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


        int newX = playerCoordinates.getX();
        int newY = playerCoordinates.getY();

        switch (direction){
            case UP ->  newY--;
            case DOWN -> newY++;
            case LEFT -> newX--;
            case RIGHT -> newX++;
        }

        Coordinates targetCoordinates = new Coordinates(newX, newY);

        if(gameBoard.cellIsEmpty(targetCoordinates)){
            playerCoordinates.setX(newX);
            playerCoordinates.setY(newY);
        }
        else{
            System.out.println("La cella è occupata o non valida...");
        }
    }
}
