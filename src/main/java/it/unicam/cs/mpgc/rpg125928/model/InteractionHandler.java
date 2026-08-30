package it.unicam.cs.mpgc.rpg125928.model;

public class InteractionHandler {

    private final MovementHandler movementHandler;
    private final Player player;
    private final GameBoard gameBoard;

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
            return handleNPCInteraction(nearNPC);
        }

        if (target instanceof Collectible nearItem) {
            return handleCollectibleInteraction(nearItem, targetCoordinates);
        }

        return "Interazione non valida.";
    }

    public String combatInteraction(NPC enemy){

        if(player.getPower() < enemy.getPower()){
            return "La tua forza è inferiore o uguale a quella di "  + enemy.getName() + "! Impossibile attaccare.";

        }

        int damage = player.getPower();
        int healthAfterAttack = enemy.getHealth() - damage;

        enemy.setHealth(healthAfterAttack);

        if(enemy.getHealth() <= 0){

            Coordinates enemyCoordinates = gameBoard.getOccupantCoordinates(enemy);

            if(enemyCoordinates != null){
                gameBoard.removeOccupant(enemyCoordinates);
            }
            return "Hai sconfitto " + enemy.getName();

        }
        else {
            return "Hai attaccato " + enemy.getName() +
                    " infliggendo " + damage +
                    " danni. (Salute nemico: " + enemy.getHealth() + ")";
        }
    }

    private String handleNPCInteraction(NPC nearNPC){
        if(nearNPC.isHostile()){
            return  combatInteraction(nearNPC);
        }
        return nearNPC.getName() + ": " + nearNPC.getDialogue();
    }

    private String handleCollectibleInteraction(Collectible nearItem, Coordinates coordinates){
        if (player.addItem(nearItem)) {
            gameBoard.removeOccupant(coordinates);
            return "Oggetto raccolto: " + nearItem.getName();
        }
        return "Inventario pieno! Impossibile raccogliere " + nearItem.getName();
    }
}