package it.unicam.cs.mpgc.rpg125928.model.occupant;

import it.unicam.cs.mpgc.rpg125928.model.Effect;
import jakarta.persistence.*;

@Entity
@Table(name = "collectibles")
public class Collectible extends Occupant{

    private Effect effect;
    private String description;

    public Collectible(String name,boolean isSolid, String description, Effect effect) {
        super(name, isSolid);
        this.description = description;
        this.effect = effect;
    }

    public Collectible(){}

    public String getDescription(){
        return description;
    }

    public void setEffect(Effect effect){ this.effect = effect; }

    public boolean use(Player player){
        if(effect != null){
            effect.applyEffect(player);
            return true;
        }
        return false;
    }
}
