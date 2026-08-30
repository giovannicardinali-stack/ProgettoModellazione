package it.unicam.cs.mpgc.rpg125928.model;

public class InteractionHandler {

    private final MovementHandler movementHandler;
    Player player;
    GameBoard gameBoard;

    public InteractionHandler(MovementHandler movementHandler,  Player player, GameBoard gameBoard) {
        this.movementHandler = movementHandler;
        this.player = player;
        this.gameBoard = gameBoard;
    }

    public String handleInteraction(){
        Coordinates targetCoordinates = movementHandler.getAdjacentOccupantCoordinates();

        if(targetCoordinates == null){
            return "non c'è nulla con cui interagire nelle vicinanze...";
        }
        Occupant target = gameBoard.getOccupant(targetCoordinates);

        if(target instanceof NPC nearNPC){
            if(nearNPC.isHostile()){
                combatInteraction(nearNPC);
                return "Inizia il combattimento con " + nearNPC.getName() + "!";
            }
            else {
                String dialogue = nearNPC.getDialogue();
                return nearNPC.getName() + ": " + dialogue;
            }
        }
        else if(target instanceof Collectible nearItem){
            if(player.addItem(nearItem)){
                gameBoard.removeOccupant(targetCoordinates);
                return "Oggetto raccolto: " + nearItem.getName();
            }
        }
        return null;
    }

    public void combatInteraction(NPC enemy){
        //todo combat logic
    }
}