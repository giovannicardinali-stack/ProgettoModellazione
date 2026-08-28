package it.unicam.cs.mpgc.rpg125928.model;

public class InteractionHandler {

    private MovementHandler movementHandler;

    public InteractionHandler(MovementHandler movementHandler) {
        this.movementHandler = movementHandler;
    }

    public void handleInteraction(){
        Occupant target = movementHandler.getAdjacentOccupant();

        if(target == null){
            System.out.println("non c'è nulla con cui interagire nelle vicinanze...");
            return;
        }

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
            //todo handle pickupItem

        }
    }

    public void combatInteraction(NPC enemy){
        //todo combat logic
    }
}
