package it.unicam.cs.mpgc.rpg125928.controller.handler;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.IGameBoard;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Collectible;
import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

public class InteractionHandler {

    private final MovementHandler movementHandler;
    private Player player;
    private final IGameBoard gameBoard;
    private CombatHandler combatHandler;

    public InteractionHandler(MovementHandler movementHandler, Player player, IGameBoard gameBoard, CombatHandler combatHandler) {
        this.movementHandler = movementHandler;
        this.player = player;
        this.gameBoard = gameBoard;
        this.combatHandler = combatHandler;
    }

    public String handleInteraction() {
        Coordinates targetCoordinates = movementHandler.getAdjacentOccupantCoordinates();

        if (targetCoordinates == null) {
            return "non c'è nulla con cui interagire nelle vicinanze...";
        }
        Occupant target = gameBoard.getOccupant(targetCoordinates);

        return switch (target){
            case NPC nearNPC -> handleNPCInteraction(nearNPC, targetCoordinates);
            case Collectible nearItem -> handleCollectibleInteraction(nearItem, targetCoordinates);
            case null -> "non c'è nulla con cui interagire nelle vicinanze...";
            default -> "Interazione non valida.";
        };
    }

    private String handleNPCInteraction(NPC nearNPC, Coordinates coordinates) {
        if (nearNPC.isHostile()) {
            return combatHandler.combatInteraction(nearNPC,coordinates);
        }
        return nearNPC.getName() + ": " + nearNPC.getDialogue();
    }

    private String handleCollectibleInteraction(Collectible nearItem, Coordinates coordinates) {
        if (player.addItem(nearItem)) {
            nearItem.setCoordinates(null);
            gameBoard.removeOccupant(coordinates);
            return "Oggetto raccolto: " + nearItem.getName();
        }
        return "Inventario pieno! Impossibile raccogliere " + nearItem.getName();
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.combatHandler.setPlayer(player);
    }
}