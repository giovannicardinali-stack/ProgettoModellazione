package it.unicam.cs.mpgc.rpg125928.model.mapGenerator;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.GameBoard;
import it.unicam.cs.mpgc.rpg125928.model.IGameBoard;
import it.unicam.cs.mpgc.rpg125928.model.PowerEnhancementEffect;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Collectible;
import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Obstacle;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;

public class DefaultMapGenerator implements MapGenerator {

    private static final int MAP_SIZE = 15;

    @Override
    public IGameBoard generateMap(){
        IGameBoard gameBoard = new GameBoard(MAP_SIZE);

        generatePerimeterWalls(gameBoard);
        generateInternalWalls(gameBoard);
        generateEntities(gameBoard);

        return gameBoard;
    }

    public void generateInternalWalls(IGameBoard gameBoard){
        for (int y = 1; y <= 5; y++) {
            gameBoard.addOccupant(new Coordinates(8, y), new Obstacle("Wall", true));
        }
    }

    private void generatePerimeterWalls(IGameBoard gameBoard) {
        int maxIndex = MAP_SIZE - 1;
        for (int i = 0; i < MAP_SIZE; i++) {
            gameBoard.addOccupant(new Coordinates(i, 0), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(i, maxIndex), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(0, i), new Obstacle("Wall", true));
            gameBoard.addOccupant(new Coordinates(maxIndex, i), new Obstacle("Wall", true));
        }
    }

    private void generateEntities(IGameBoard gameBoard) {
        Player player = new Player("player1", true, 10, 10, 4);
        gameBoard.addOccupant(new Coordinates(3, 11), player);

        NPC npc = new NPC("enemy1", true, 8, 5, true, "");
        gameBoard.addOccupant(new Coordinates(11, 3), npc);

        PowerEnhancementEffect powerEffect = new PowerEnhancementEffect(10);
        Collectible potion = new Collectible("Pozione della Forza", true, "", powerEffect);

        gameBoard.addOccupant(new Coordinates(5, 5), potion);
    }

    @Override
    public IGameBoard generateExistantMap(){
        IGameBoard gameBoard = new GameBoard(MAP_SIZE);

        generatePerimeterWalls(gameBoard);
        generateInternalWalls(gameBoard);

        return gameBoard;
    }
}