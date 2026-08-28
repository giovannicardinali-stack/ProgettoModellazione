package it.unicam.cs.mpgc.rpg125928.model;

public class InteractionHandler {

    private MovementHandler movementHandler;
    Player player;
    GameBoard gameBoard;

    public InteractionHandler(MovementHandler movementHandler,  Player player, GameBoard gameBoard) {
        this.movementHandler = movementHandler;
        this.player = player;
        this.gameBoard = gameBoard;
    }

    public void handleInteraction(){
        Coordinates targetCoordinates = movementHandler.getAdjacentOccupantCoordinates();

        if(targetCoordinates == null){
            System.out.println("non c'è nulla con cui interagire nelle vicinanze...");
            return;
        }
        Occupant target = gameBoard.getOccupant(targetCoordinates);

        if(target instanceof NPC nearNPC){
            if(nearNPC.isHostile()){
                combatInteraction(nearNPC);
            }
            else {
                String dialogue = nearNPC.getDialogue();
                //todo update view
                System.out.println(nearNPC.getName() + ": " + dialogue);
            }
        }
        else if(target instanceof Collectible nearItem){
            //add the item in the player's inventory and remove it from the map
            if(player.addItem(nearItem)){
                gameBoard.removeOccupant(targetCoordinates);
                System.out.println("oggetto raccolto: "+ nearItem.getName());
            }
        }
    }

    public void combatInteraction(NPC enemy){
        //todo combat logic
    }
}