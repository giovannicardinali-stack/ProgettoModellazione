package model;

public class NPC extends Occupant{

    private int power;
    private int health;


    public NPC(String name, boolean isSolid,  int power, int health){
        super(name, isSolid);
        this.power = power;
        this.health = health;
    }

    public int getPower() { return power; }

    public void setPower(int power) { this.power = power; }

    public int getHealth() { return health; }

    public void setHealth(int health) { this.health = health; }
}