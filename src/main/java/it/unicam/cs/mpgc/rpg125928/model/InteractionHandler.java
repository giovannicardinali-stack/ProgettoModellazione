package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.occupant.Collectible;
import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

public class InteractionHandler {

    private final MovementHandler movementHandler;
    private Player player;
    private final GameBoard gameBoard;

    public InteractionHandler(MovementHandler movementHandler, Player player, GameBoard gameBoard) {
        this.movementHandler = movementHandler;
        this.player = player;
        this.gameBoard = gameBoard;
    }

    public String handleInteraction() {
        Coordinates targetCoordinates = movementHandler.getAdjacentOccupantCoordinates();

        if (targetCoordinates == null) {
            return "non c'è nulla con cui interagire nelle vicinanze...";
        }
        Occupant target = gameBoard.getOccupant(targetCoordinates);

        return switch (target){
            case NPC nearNPC -> handleNPCInteraction(nearNPC,  targetCoordinates);
            case Collectible nearItem -> handleCollectibleInteraction(nearItem, targetCoordinates);
            case null -> "non c'è nulla con cui interagire nelle vicinanze...";
            default -> "Interazione non valida.";
        };
    }

    public String combatInteraction(NPC enemy, Coordinates enemyCoordinates) {

        if (player.getPower() < enemy.getPower()) {
            return "La tua forza è inferiore o uguale a quella di " + enemy.getName() + "! Impossibile attaccare.";
        }

        applyDamage(enemy, player.getPower());

        if (enemy.getHealth() <= 0) {
            return handleEnemyDefeat(enemy,enemyCoordinates);

        }
        return "Hai attaccato " + enemy.getName() +
                " infliggendo " + player.getPower() +
                " danni. (Salute nemico: " + enemy.getHealth() + ")";
    }

    private void applyDamage(NPC enemy, int damage){
        enemy.setHealth(enemy.getHealth() - damage);
    }

    private String handleEnemyDefeat(NPC enemy, Coordinates enemyCoordinates){
        if (enemyCoordinates != null) {
            gameBoard.removeOccupant(enemyCoordinates);
        }
        if(gameBoard.countHostileNPC() == 0){
            return "LEVEL_CLEARED";
        }
        return "Hai sconfitto " + enemy.getName();
    }

    private String handleNPCInteraction(NPC nearNPC, Coordinates coordinates) {
        if (nearNPC.isHostile()) {
            return combatInteraction(nearNPC, coordinates);
        }
        return nearNPC.getName() + ": " + nearNPC.getDialogue();
    }

    private String handleCollectibleInteraction(Collectible nearItem, Coordinates coordinates) {
        if (player.addItem(nearItem)) {
            gameBoard.removeOccupant(coordinates);
            return "Oggetto raccolto: " + nearItem.getName();
        }
        return "Inventario pieno! Impossibile raccogliere " + nearItem.getName();
    }

    public void setPlayer(Player player) {
        this.player = player;
    }
}