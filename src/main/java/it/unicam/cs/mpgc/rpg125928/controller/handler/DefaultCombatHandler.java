package it.unicam.cs.mpgc.rpg125928.controller.handler;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.IGameBoard;
import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

public class DefaultCombatHandler implements CombatHandler {

    private final IGameBoard gameBoard;
    private Player player;


    public DefaultCombatHandler(IGameBoard gameBoard, Player player) {
        this.gameBoard = gameBoard;
        this.player = player;
    }

    @Override
    public String combatInteraction(NPC enemy, Coordinates enemyCoordinates) {
        if (player == null || enemy == null) {
            return "Interazione non valida.";
        }
        if (player.getPower() < enemy.getPower()) {
            return "La tua forza è inferiore o uguale a quella di " + enemy.getName() + "! Impossibile attaccare.";
        }
        applyDamage(enemy, player.getPower());
        if (enemy.getHealth() <= 0) {
            return handleEnemyDefeat(enemy, enemyCoordinates);
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

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }
}
