package it.unicam.cs.mpgc.rpg125928.model;

public class Collectible extends Occupant{
    private String description;

    public Collectible(String name,boolean isSolid, String description){
        super(name, isSolid);
        this.description = description;
    }

    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }
}
