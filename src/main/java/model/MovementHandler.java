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

        Coordinates targetCoordinates = calculateTargetCoordinates(playerCoordinates, direction);


//        int newX = playerCoordinates.getX();
//        int newY = playerCoordinates.getY();
//
//        switch (direction){
//            case UP ->  newY--;
//            case DOWN -> newY++;
//            case LEFT -> newX--;
//            case RIGHT -> newX++;
//        }
//
//        Coordinates targetCoordinates = new Coordinates(newX, newY);

        if(gameBoard.cellIsEmpty(targetCoordinates)){
            playerCoordinates.setX(targetCoordinates.getX());
            playerCoordinates.setY(targetCoordinates.getY());
        }
        else{
            System.out.println("La cella è occupata o non valida...");
        }
    }

    public NPC getAdjacentNPC(){

        Direction[] directions = {Direction.UP, Direction.DOWN, Direction.LEFT, Direction.RIGHT};

        int playerCoordinatesX = playerCoordinates.getX();
        int playerCoordinatesY = playerCoordinates.getY();

        for(Direction direction : directions){

            int targetX = playerCoordinatesX;
            int targetY = playerCoordinatesY;

            switch (direction){
                case UP -> targetY--;
                case DOWN -> targetY++;
                case LEFT -> targetX--;
                case RIGHT -> targetX++;
            }

            Coordinates targetCoordinates = new Coordinates(targetX, targetY);

            Cell targetCell = gameBoard.getCell(targetCoordinates);

            if(targetCell != null && targetCell.getOccupant() instanceof NPC){
                return (NPC) targetCell.getOccupant();
            }
        }
        return null;
    }


    private Coordinates calculateTargetCoordinates(Coordinates currentCoordinates, Direction direction){

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
}