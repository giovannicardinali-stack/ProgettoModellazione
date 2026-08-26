package it.unicam.cs.mpgc.rpg125928.model;

public abstract class Occupant {

    protected String name;
    protected boolean isSolid;


    public Occupant(String name, boolean solid) {
        this.name = name;
        this.isSolid = solid;
    }

    public boolean isSolid() {
        return isSolid;
    }

}