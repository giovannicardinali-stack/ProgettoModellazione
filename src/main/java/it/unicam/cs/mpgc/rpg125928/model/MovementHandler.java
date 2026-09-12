package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

public class MovementHandler {
    private Coordinates playerCoordinates;
    private final IGameBoard gameBoard;
    private Player loadedPlayer;

    public MovementHandler(Coordinates playerCoordinates, IGameBoard gameBoard) {
        this.playerCoordinates = playerCoordinates;
        this.gameBoard = gameBoard;
    }

    public boolean movePlayer(Direction direction){
        Coordinates targetCoordinates = gameBoard.getAdjacentCoordinates(playerCoordinates, direction);

        if(!gameBoard.isWithinBounds(targetCoordinates) || !gameBoard.cellIsEmpty(targetCoordinates)){
            return false; }

        Occupant player = (this.loadedPlayer != null) ? this.loadedPlayer : gameBoard.getOccupant(playerCoordinates);

        if (player == null) {
            return false;
        }

        gameBoard.removeOccupant(playerCoordinates);
        this.playerCoordinates = targetCoordinates;
        gameBoard.addOccupant(playerCoordinates, player);
        return true;
    }

    public Coordinates getAdjacentOccupantCoordinates(){
        for(Direction direction : Direction.values()){
            Coordinates targetCoordinates = gameBoard.getAdjacentCoordinates(playerCoordinates, direction);

            if(gameBoard.isWithinBounds(targetCoordinates) && !gameBoard.cellIsEmpty(targetCoordinates)){
                return targetCoordinates;
            }
        }
        return null;
    }

    public void setPlayerCoordinates(Coordinates playerCoordinates) {
        this.playerCoordinates = playerCoordinates;
    }

    public void setLoadedPlayer(Player loadedPlayer) {
        this.loadedPlayer = loadedPlayer;
    }
}