package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;

public class MovementHandler {
    private Coordinates playerCoordinates;
    private final GameBoard gameBoard;

    public MovementHandler(Coordinates playerCoordinates, GameBoard gameBoard) {
        this.playerCoordinates = playerCoordinates;
        this.gameBoard = gameBoard;
    }

    public boolean movePlayer(Direction direction){
        System.out.println("--- MOVE TRY ---");
        System.out.println("Player Coords in Handler: " + playerCoordinates);

        Coordinates targetCoordinates = getAdjacentCoordinates(playerCoordinates, direction);
        System.out.println("Target Coords: " + targetCoordinates);

        if(!isInMapBorder(targetCoordinates)){
            return false;
        }

        if (!gameBoard.cellIsEmpty(targetCoordinates)) {
            System.out.println("Cell is NOT empty!");
            return false;
        }

        Occupant player = gameBoard.getOccupant(playerCoordinates);
        System.out.println("Player entity on board at current coords: " + player);
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



            if(isInMapBorder(targetCoordinates)){
                Occupant targetCell = gameBoard.getOccupant(targetCoordinates);
                if(targetCell != null){
                    return targetCoordinates;
                }

            }


        }
        return null;
    }

    private boolean isInMapBorder(Coordinates coordinates){
        int size = gameBoard.getMapSize();
        return coordinates.getX() >= 0 && coordinates.getX() < size &&
                coordinates.getY() >= 0 && coordinates.getY() < size;
    }

    public void setPlayerCoordinates(Coordinates playerCoordinates) {
        this.playerCoordinates = playerCoordinates;
    }
}