package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

public class PowerEnhancementEffect implements Effect {

    private final int powerAmount;

    public  PowerEnhancementEffect(int powerAmount){
        this.powerAmount = powerAmount;
    }

    @Override
    public void applyEffect(Player player) {
        player.setPower(player.getPower() + powerAmount);
    }
}
