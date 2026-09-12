package it.unicam.cs.mpgc.rpg125928.model.occupant;

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
    private int currentGameLevel = 1;

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

    public int getCurrentLevel() { return currentGameLevel; }

    public void setCurrentLevel(int currentLevel) { this.currentGameLevel = currentLevel; }

    public boolean addItem(Collectible item){
        if(item == null) return false;
        if(inventory.size() < inventorySize){
            item.setCoordinates(null);
            inventory.add(item);
            return true;
        }
        return false;
    }

    public boolean useItem(Collectible item){
        if (item == null || inventory == null || inventory.isEmpty()) {
            return false;
        }

        Collectible targetItem = inventory.stream()
                .filter(i -> i.equals(item) || (i.getId() != null && i.getId().equals(item.getId())))
                .findFirst()
                .orElse(null);

        if (targetItem != null) {
            if (targetItem.use(this)) {
                inventory.remove(targetItem);
                System.out.println("Hai usato: " + targetItem.getName());
                return true;
            }
        }
        return false;
    }

}