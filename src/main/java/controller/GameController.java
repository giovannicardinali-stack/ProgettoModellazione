package controller;

import model.MovementHandler;
import model.NPC;

public class GameController {
    MovementHandler movementHandler;


    public  GameController(MovementHandler movementHandler) {
        this.movementHandler = movementHandler;
    }

    public void handleInteraction(){
        NPC nearNPC = movementHandler.getAdjacentNPC();

        if(nearNPC != null){
            if(nearNPC.isHostile()){
                //to do combat logic
            }
            else {
                String dialogue = nearNPC.getDialogue();
            }
        }
        else {
            System.out.println("non c'è nessuno con cui interagire nelle vicinanze");
        }
    }
}
