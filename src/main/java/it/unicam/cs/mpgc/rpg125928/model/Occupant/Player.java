package it.unicam.cs.mpgc.rpg125928.model.Occupant;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Player extends Occupant {


    private int power;
    private int health;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "player_id")
    private List<Collectible> inventory;
    private int inventorySize;


    public Player() {

    }

    public Player(String name, boolean isSolid,  int power, int health, int inventorySize) {
        super(name, isSolid);
        this.power = power;
        this.health = health;
        this.inventory = new ArrayList<Collectible>();
        this.inventorySize = inventorySize;


    }

    public int getPower() { return power; }

    public void setPower(int power) { this.power = power; }

    public int getHealth() { return health; }

    public void setHealth(int health) { this.health = health; }

    public List<Collectible> getInventory() { return inventory; }

    public boolean addItem(Collectible item){
        if(inventory.size() < inventorySize){
            inventory.add(item);
            return true;
        }
        else {
            return false;
        }
    }

    public boolean removeItem(Collectible item){
        if(inventory.contains(item)){
            inventory.remove(item);
            return true;
        }
        return false;
    }

}