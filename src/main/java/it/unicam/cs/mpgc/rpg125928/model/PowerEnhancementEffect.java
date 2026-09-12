package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;
import jakarta.persistence.*;

@Entity
@Table(name = "power_effects")
public class PowerEnhancementEffect implements Effect {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private int powerAmount;

    public PowerEnhancementEffect(int powerAmount){
        this.powerAmount = powerAmount;
    }

    public PowerEnhancementEffect() {}

    @Override
    public void applyEffect(Player player) {
        player.setPower(player.getPower() + powerAmount);
    }

    public Long getId() { return id; }
    public int getPowerAmount() { return powerAmount; }
}
