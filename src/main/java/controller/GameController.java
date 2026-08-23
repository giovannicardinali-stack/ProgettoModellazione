package controller;

import model.Coordinates;
import model.Direction;
import model.MovementHandler;
import model.NPC;

public class GameController {
    MovementHandler movementHandler;
    //to do view

    public  GameController(MovementHandler movementHandler) {
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
            }
        }
        else {
            System.out.println("non c'è nessuno con cui interagire nelle vicinanze");
        }
    }

    public void combatInteraction(NPC enemy){
        //to do combat logic
    }

    public void onDirectionChange(Direction direction){

        movementHandler.movePlayer(direction);

        Coordinates newPosition = movementHandler.getPlayerCoordinates();

        //update view
    }

    public void onInteraction(){
        NPC npc = movementHandler.getAdjacentNPC();
        if(npc != null){
            //update view
        }
    }
}
