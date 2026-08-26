package it.unicam.cs.mpgc.rpg125928.controller;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.Direction;
import it.unicam.cs.mpgc.rpg125928.model.MovementHandler;
import it.unicam.cs.mpgc.rpg125928.model.NPC;

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
                //to do update view
                System.out.println(nearNPC.getName() + ": " + dialogue);
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

        //to do update view
    }
}
