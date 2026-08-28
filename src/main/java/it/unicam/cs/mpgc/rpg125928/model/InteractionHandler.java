package it.unicam.cs.mpgc.rpg125928.model;

public class InteractionHandler {

    private MovementHandler movementHandler;
    Player player;

    public InteractionHandler(MovementHandler movementHandler,  Player player) {
        this.movementHandler = movementHandler;
        this.player = player;
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
            pickUpItem(nearItem);
        }
    }

    public void combatInteraction(NPC enemy){
        //todo combat logic
    }

    public void pickUpItem(Collectible item){
        player.addItem(item);
    }
}
