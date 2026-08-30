package it.unicam.cs.mpgc.rpg125928.model;

public class InteractionHandler {

    private final MovementHandler movementHandler;
    Player player;
    GameBoard gameBoard;

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
            if(nearNPC.isHostile()){
                return combatInteraction(nearNPC);
            }
            else {
                String dialogue = nearNPC.getDialogue();
                return nearNPC.getName() + ": " + dialogue;
            }
        }
        else if(target instanceof Collectible nearItem){
            if(player.addItem(nearItem)){
                gameBoard.removeOccupant(targetCoordinates);
                return "Oggetto raccolto: " + nearItem.getName();
            }
        }
        return null;
    }

    public String combatInteraction(NPC enemy){

        if(player.getPower() >= enemy.getPower()){
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
        else {
            return "La tua forza è inferiore o uguale a quella di " +
                    enemy.getName() + "! Impossibile attaccare.";
        }
    }
}