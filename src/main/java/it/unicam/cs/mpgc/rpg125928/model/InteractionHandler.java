package it.unicam.cs.mpgc.rpg125928.model;

public class InteractionHandler {

    private MovementHandler movementHandler;


    public InteractionHandler(MovementHandler movementHandler) {
        this.movementHandler = movementHandler;
    }

    public void handleInteraction(){
        NPC nearNPC = movementHandler.getAdjacentNPC();

        if(nearNPC != null){
            if(nearNPC.isHostile()){
                combatInteraction(nearNPC);
            }
            else {
                String dialogue = nearNPC.getDialogue();
                //to do update view
                System.out.println(nearNPC.getName() + ": " + dialogue);
            }
        }
        else {
            System.out.println("non c'è nessuno con cui interagire nelle vicinanze");
        }
    }
}
