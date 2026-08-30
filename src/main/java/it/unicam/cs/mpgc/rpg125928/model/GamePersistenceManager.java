package it.unicam.cs.mpgc.rpg125928.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Map;

public class GamePersistenceManager {

    private final SessionFactory sessionFactory;

    public GamePersistenceManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveGame(GameBoard gameBoard) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            for (Map.Entry<Coordinates, Occupant> entry : gameBoard.getGameMap().entrySet()) {
                Coordinates coords = entry.getKey();
                Occupant occupant = entry.getValue();

                if (!occupant.getName().equalsIgnoreCase("Wall")) {
                    occupant.setCoordinates(coords);
                    session.merge(occupant);
                }
            }

            session.getTransaction().commit();
            System.out.println("Partita salvata correttamente sul database!");
        }
    }

    public GameBoard loadGame(int mapSize) {
        MapGenerator mapGenerator = new MapGenerator();
        GameBoard gameBoard = mapGenerator.generateMap();

        try (Session session = sessionFactory.openSession()) {
            List<Occupant> occupants = session.createQuery("FROM Occupant", Occupant.class).getResultList();

            for (Occupant occupant : occupants) {
                Coordinates coords = occupant.getCoordinates();
                if (coords != null) {
                    gameBoard.addOccupant(coords, occupant);
                }
            }

            System.out.println("Partita caricata e board ricostruita con successo!");
        }

        return gameBoard;
    }
}