package it.unicam.cs.mpgc.rpg125928.model;

public class NPC extends Occupant{

    private int power;
    private int health;
    private boolean isHostile;
    private String dialogue;


    public NPC(String name, boolean isSolid,  int power, int health, boolean isHostile, String dialogue) {
        super(name, isSolid);
        this.power = power;
        this.health = health;
        this.isHostile = isHostile;
        this.dialogue = dialogue;
    }

    public String getName(){
        return this.name;
    }

    public int getPower() { return power; }

    public void setPower(int power) { this.power = power; }

    public int getHealth() { return health; }

    public void setHealth(int health) { this.health = health; }

    public boolean isHostile() { return isHostile; }

    public void setIsHostile(boolean isHostile) { this.isHostile = isHostile; }

    public String getDialogue() { return dialogue; }

    public void setDialogue(String dialogue) { this.dialogue = dialogue; }
}