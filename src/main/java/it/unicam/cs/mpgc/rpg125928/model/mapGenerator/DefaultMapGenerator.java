package it.unicam.cs.mpgc.rpg125928.model.mapGenerator;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.GameBoard;
import it.unicam.cs.mpgc.rpg125928.model.PowerEnhancementEffect;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Collectible;
import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Obstacle;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

public class DefaultMapGenerator implements MapGenerator {

    private static final int MAP_SIZE = 15;

    @Override
    public GameBoard generateMap(){
        GameBoard gameBoard = new GameBoard(MAP_SIZE);

        generatePerimeterWalls(gameBoard);
        generateInternalWalls(gameBoard);
        generateEntities(gameBoard);

        return gameBoard;
    }

    public void generateInternalWalls(GameBoard gameBoard){
        for (int y = 1; y <= 5; y++) {
            gameBoard.addOccupant(new Coordinates(8, y), new Obstacle("Wall", true));
        }
    }

    private void generatePerimeterWalls(GameBoard gameBoard) {
        int maxIndex = MAP_SIZE - 1;
        for (int i = 0; i < MAP_SIZE; i++) {
            gameBoard.addOccupant(new Coordinates(i, 0), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(i, maxIndex), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(0, i), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(maxIndex, i), new Obstacle("Wall", true));
        }
    }

    private void generateEntities(GameBoard gameBoard) {
        Player player = new Player("player1", true, 10, 10, 4);
        gameBoard.addOccupant(new Coordinates(3, 11), player);

        NPC npc = new NPC("enemy1", true, 8, 5, true, "");
        gameBoard.addOccupant(new Coordinates(11, 3), npc);

        PowerEnhancementEffect powerEffect = new PowerEnhancementEffect(10);
        Collectible potion = new Collectible("Pozione della Forza", true, "", powerEffect);

        gameBoard.addOccupant(new Coordinates(5, 5), potion);
    }

    @Override
    public GameBoard generateExistantMap(){
        GameBoard gameBoard = new GameBoard(MAP_SIZE);

        generatePerimeterWalls(gameBoard);
        generateInternalWalls(gameBoard);

        return gameBoard;
    }

    @Override
    public void populateLevel(GameBoard gameBoard, int level, Player player){
        gameBoard.getGameMap().clear();

        generatePerimeterWalls(gameBoard);
        generateInternalWalls(gameBoard);

        Coordinates startCoords = new Coordinates(3, 11);
        gameBoard.addOccupant(startCoords, player);

        int numberOfEnemies = Math.min(level, 5);
        for (int i = 0; i < numberOfEnemies; i++) {
            int enemyPower = 3 + (level * 2);
            int enemyHealth = 5 + (level * 3);
            NPC enemy = new NPC("Mostro Lvl " + level + " (" + (i + 1) + ")", true, enemyHealth, enemyPower, true, "Grrr!");

            gameBoard.addOccupant(new Coordinates(11 - (i * 2), 3 + (i * 2)), enemy);
        }

        PowerEnhancementEffect powerEffect = new PowerEnhancementEffect(5 + level);
        Collectible potion = new Collectible("Pozione Lvl " + level, true, "+ Power", powerEffect);
        gameBoard.addOccupant(new Coordinates(5, 5), potion);
    }
}