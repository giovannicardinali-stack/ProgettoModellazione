package it.unicam.cs.mpgc.rpg125928.model;

public class MovementHandler {
    private Coordinates playerCoordinates;
    private final GameBoard gameBoard;

    public MovementHandler(Coordinates playerCoordinates, GameBoard gameBoard) {
        this.playerCoordinates = playerCoordinates;
        this.gameBoard = gameBoard;
    }

    public Coordinates getPlayerCoordinates() {
        return playerCoordinates;
    }

    public boolean movePlayer(Direction direction){

        Coordinates targetCoordinates = getAdjacentCoordinates(playerCoordinates, direction);

        if (!gameBoard.cellIsEmpty(targetCoordinates)) {
            return false;
        }

        Occupant player = gameBoard.getOccupant(playerCoordinates);
        gameBoard.getGameMap().remove(playerCoordinates);

        playerCoordinates = targetCoordinates;

        gameBoard.addOccupant(playerCoordinates, player);
        return true;
    }

    public Coordinates getAdjacentCoordinates(Coordinates currentCoordinates, Direction direction){

        int newX = currentCoordinates.getX();
        int newY = currentCoordinates.getY();

        switch (direction){
            case UP ->  newY--;
            case DOWN -> newY++;
            case LEFT -> newX--;
            case RIGHT -> newX++;
        }
        return new Coordinates(newX, newY);
    }

    public Coordinates getAdjacentOccupantCoordinates(){
        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

        for(Direction direction : directions){
            Coordinates targetCoordinates = getAdjacentCoordinates(playerCoordinates, direction);
            Occupant targetCell = gameBoard.getOccupant(targetCoordinates);
            if(targetCell != null){
                return targetCoordinates;
            }
        }
        return null;
    }
}