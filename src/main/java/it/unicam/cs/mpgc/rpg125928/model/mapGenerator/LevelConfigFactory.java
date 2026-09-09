package it.unicam.cs.mpgc.rpg125928.model.mapGenerator;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.PowerEnhancementEffect;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Collectible;
import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LevelConfigFactory {

    public static LevelConfig getLevelConfig(int levelNumber) {
        return switch (levelNumber){
            case 1 -> createLevel1();
            case 2 -> createLevel2();
            case 3 -> createLevel3();
            default -> createLevel1();
        };
    }

    private static LevelConfig createLevel1() {

        Map<Coordinates, NPC> enemies = new HashMap<>();
        enemies.put(new Coordinates(11, 3), new NPC("npc1", true, 8, 3, true, "..."));

        Map<Coordinates, Collectible> items = new HashMap<>();
        items.put(new Coordinates(5, 5), new Collectible("Pozione Piccola", true, "+5 HP", new PowerEnhancementEffect(5)));

        return new LevelConfig(
                1,
                new Coordinates(3, 11),
                enemies,
                items,
                List.of(new Coordinates(8, 1), new Coordinates(8, 2), new Coordinates(8, 3), new Coordinates(8, 4), new Coordinates(8, 5)
                )
        );
    }

    private static LevelConfig createLevel2() {

        Map<Coordinates, NPC> enemies = new HashMap<>();
        enemies.put(new Coordinates(10, 5), new NPC("Orco", true, 15, 6, true, "ROAR!"));
        enemies.put(new Coordinates(4, 4), new NPC("Scheletro", true, 10, 4, true, "Clack!"));

        Map<Coordinates, Collectible> items = new HashMap<>();
        items.put(new Coordinates(2, 2), new Collectible("Pozione Media", true, "+10 Power", new PowerEnhancementEffect(10)));

        return new LevelConfig(
                2,
                new Coordinates(1, 13),
                enemies,
                items,
                List.of(new Coordinates(5, 5), new Coordinates(5, 6), new Coordinates(5, 7), new Coordinates(6, 7), new Coordinates(7, 7)
                )
        );
    }

    private static LevelConfig createLevel3() {
        Map<Coordinates, NPC> enemies = new HashMap<>();
        enemies.put(new Coordinates(7, 7), new NPC("Re Demone", true, 30, 10, true, "Inchinati!"));
        Map<Coordinates, Collectible> items = new HashMap<>();
        items.put(new Coordinates(1, 1), new Collectible("Elisir Supremo", true, "+20 Power", new PowerEnhancementEffect(20)));

        return new LevelConfig(
                3,
                new Coordinates(2, 2),
                enemies,
                items,
                List.of(new Coordinates(6, 6), new Coordinates(6, 8),  new Coordinates(8, 6),  new Coordinates(8, 8)
                )
        );
    }
}