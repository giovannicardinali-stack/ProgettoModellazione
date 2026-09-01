package it.unicam.cs.mpgc.rpg125928.model;

import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.MapGenerator;
import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GamePersistenceManager {

    private final SessionFactory sessionFactory;
    private MapGenerator mapGenerator;

    public GamePersistenceManager(SessionFactory sessionFactory, MapGenerator mapGenerator) {
        this.sessionFactory = sessionFactory;
        this.mapGenerator = mapGenerator;
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

    public void setMapGenerator(MapGenerator mapGenerator) {
        this.mapGenerator = mapGenerator;
    }
}