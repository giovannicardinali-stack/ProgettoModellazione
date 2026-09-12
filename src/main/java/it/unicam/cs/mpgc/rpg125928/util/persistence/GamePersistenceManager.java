package it.unicam.cs.mpgc.rpg125928.util.persistence;

import it.unicam.cs.mpgc.rpg125928.model.Coordinates;
import it.unicam.cs.mpgc.rpg125928.model.IGameBoard;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelConfig;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelConfigFactory;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.LevelMapGenerator;
import it.unicam.cs.mpgc.rpg125928.model.mapGenerator.MapGenerator;
import it.unicam.cs.mpgc.rpg125928.model.occupant.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GamePersistenceManager implements PersistanceManager {

    private final SessionFactory sessionFactory;
    private MapGenerator mapGenerator;

    public GamePersistenceManager(SessionFactory sessionFactory, MapGenerator mapGenerator) {
        this.sessionFactory = sessionFactory;
        this.mapGenerator = mapGenerator;
    }

    @Override
    public void saveGame(IGameBoard gameBoard) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Player currentPlayer = gameBoard.getPlayer();

            if (currentPlayer != null) {
                Player existingDbPlayer = session.createQuery("FROM Player", Player.class)
                        .uniqueResultOptional()
                        .orElse(null);

                if (existingDbPlayer != null) {
                    currentPlayer.setId(existingDbPlayer.getId());
                }

                Coordinates playerCoords = gameBoard.getOccupantCoordinates(currentPlayer);
                if (playerCoords != null) {
                    currentPlayer.setCoordinates(playerCoords);
                }

                if (currentPlayer.getInventory() != null) {
                    List<Collectible> mergedInventory = new ArrayList<>();
                    for (Collectible item : currentPlayer.getInventory()) {
                        item.setCoordinates(null);

                        if (item.getId() != null) {
                            Collectible dbItem = session.get(Collectible.class, item.getId());
                            if (dbItem != null) {
                                mergedInventory.add(session.merge(item));
                            }
                        } else {
                            mergedInventory.add(session.merge(item));
                        }
                    }
                    currentPlayer.getInventory().clear();
                    currentPlayer.getInventory().addAll(mergedInventory);
                }

                currentPlayer = session.merge(currentPlayer);
            }

            Set<Long> inventoryIds = (currentPlayer != null && currentPlayer.getInventory() != null)
                    ? currentPlayer.getInventory().stream()
                    .filter(inv -> inv.getId() != null)
                    .map(Collectible::getId)
                    .collect(Collectors.toSet())
                    : Set.of();

            List<Occupant> allDbOccupants = session.createQuery("FROM Occupant", Occupant.class).getResultList();
            for (Occupant occ : allDbOccupants) {

                if (occ instanceof Player || occ instanceof Obstacle) {
                    continue;
                }

                boolean inInventory = (occ instanceof Collectible) && inventoryIds.contains(occ.getId());
                boolean onBoard = gameBoard.getGameMap().values().stream()
                        .anyMatch(boardOcc -> boardOcc != null && occ.getId() != null && occ.getId().equals(boardOcc.getId()));

                if (!inInventory && !onBoard) {
                    session.remove(occ);
                }
            }

            for (Map.Entry<Coordinates, Occupant> entry : gameBoard.getGameMap().entrySet()) {
                Occupant occupant = entry.getValue();

                if (occupant != null && !(occupant instanceof Player) && !(occupant instanceof Obstacle)) {
                    if (occupant instanceof Collectible collectible) {
                        if (collectible.getId() != null && inventoryIds.contains(collectible.getId())) {
                            continue;
                        }
                    }
                    occupant.setCoordinates(entry.getKey());
                    session.merge(occupant);
                }
            }

            session.getTransaction().commit();
            System.out.println("Partita salvata e database sincronizzato correttamente!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IGameBoard loadGame() {
        try (Session session = sessionFactory.openSession()) {
            List<Occupant> occupants = session.createQuery("FROM Occupant", Occupant.class).getResultList();

            Player loadedPlayer = occupants.stream()
                    .filter(Player.class::isInstance)
                    .map(Player.class::cast)
                    .findFirst()
                    .orElse(null);

            if (loadedPlayer != null && loadedPlayer.getCurrentLevel() > 1) {
                LevelConfig config = LevelConfigFactory.getLevelConfig(loadedPlayer.getCurrentLevel());
                this.mapGenerator = new LevelMapGenerator(config, loadedPlayer);
            }

            IGameBoard gameBoard = mapGenerator.generateExistantMap();
            boolean playerAdded = false;

            Set<Long> inventoryIds = (loadedPlayer != null && loadedPlayer.getInventory() != null)
                    ? loadedPlayer.getInventory().stream()
                    .filter(i -> i.getId() != null)
                    .map(Collectible::getId)
                    .collect(Collectors.toSet())
                    : Set.of();

            for (Occupant occupant : occupants) {
                if (occupant instanceof Collectible collectible) {
                    if (collectible.getId() != null && inventoryIds.contains(collectible.getId())) {
                        continue;
                    }
                }

                if (occupant instanceof Player) {
                    if (playerAdded) continue;
                    playerAdded = true;
                }

                if (occupant instanceof Obstacle) {
                    continue;
                }

                Coordinates coords = occupant.getCoordinates();
                if (coords != null) {
                    gameBoard.addOccupant(coords, occupant);
                }
            }

            System.out.println("Partita caricata con successo!");
            return gameBoard;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setMapGenerator(MapGenerator mapGenerator) {
        this.mapGenerator = mapGenerator;
    }
}