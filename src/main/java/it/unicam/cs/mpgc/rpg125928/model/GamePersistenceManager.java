package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.Occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.Occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.Occupant.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GamePersistenceManager {

    private final SessionFactory sessionFactory;

    public GamePersistenceManager(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveGame(GameBoard gameBoard) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Occupant> savedOccupants = session.createQuery("FROM Occupant", Occupant.class).getResultList();
            Set<Coordinates> activeCoords = gameBoard.getGameMap().keySet();

            for (Occupant saved : savedOccupants) {
                if (!activeCoords.contains(saved.getCoordinates())) {
                    session.remove(saved);
                }
            }
            session.flush();
            for (Map.Entry<Coordinates, Occupant> entry : gameBoard.getGameMap().entrySet()) {
                Coordinates coords = entry.getKey();
                Occupant occupant = entry.getValue();

                if (occupant != null) {
                    occupant.setCoordinates(coords);
                    session.merge(occupant);
                }
            }
            session.getTransaction().commit();
            System.out.println("Partita salvata e database sincronizzato correttamente!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public GameBoard loadGame() {
        MapGenerator mapGenerator = new DefaultMapGenerator();

        GameBoard gameBoard = mapGenerator.generateExistantMap();

        gameBoard.getGameMap().values().removeIf(occupant -> occupant instanceof Player || occupant instanceof NPC);

        try (Session session = sessionFactory.openSession()) {
            List<Occupant> occupants = session.createQuery("FROM Occupant", Occupant.class).getResultList();

            for (Occupant occupant : occupants) {
                Coordinates coords = occupant.getCoordinates();
                if (coords != null) {

                    gameBoard.getGameMap().put(coords, occupant);
                }
            }

            System.out.println("Partita caricata e board ricostruita con successo!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return gameBoard;
    }
}