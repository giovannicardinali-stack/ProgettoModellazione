package it.unicam.cs.mpgc.rpg125928.controller.handler;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

public interface CombatHandler {

    void setPlayer(Player player);
    String combatInteraction(NPC enemy, Coordinates enemyCoordinates);
}
