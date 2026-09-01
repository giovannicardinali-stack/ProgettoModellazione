package it.unicam.cs.mpgc.rpg125928.model.occupant;

import jakarta.persistence.Entity;

@Entity
public class Obstacle extends Occupant {

    public Obstacle(String name, boolean isSolid) {
        super(name, isSolid);
    }

    public Obstacle() {}

}
