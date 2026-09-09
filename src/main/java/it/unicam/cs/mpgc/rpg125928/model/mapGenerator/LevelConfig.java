package it.unicam.cs.mpgc.rpg125928.model.mapGenerator;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Collectible;
import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;

import java.util.List;
import java.util.Map;

public class LevelConfig {

        private final int levelNumber;
    private final Coordinates playerSpawn;
    private final Map<Coordinates, NPC> enemies;
    private final Map<Coordinates, Collectible> items;
    private final List<Coordinates> walls;

    public LevelConfig(int levelNumber,
                       Coordinates playerSpawn,
                       Map<Coordinates, NPC> enemies,
                       Map<Coordinates, Collectible> items,
                       List<Coordinates> walls) {
        this.levelNumber = levelNumber;
        this.playerSpawn = playerSpawn;
        this.enemies = enemies;
        this.items = items;
        this.walls = walls;
    }

    public Coordinates getPlayerSpawn() { return playerSpawn; }
    public Map<Coordinates, NPC> getEnemies() { return enemies; }
    public Map<Coordinates, Collectible> getItems() { return items; }
    public List<Coordinates> getInternalWalls() { return walls; }
}
