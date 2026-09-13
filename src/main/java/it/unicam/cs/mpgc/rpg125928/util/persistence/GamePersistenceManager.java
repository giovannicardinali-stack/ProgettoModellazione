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
    public void saveGame(IGameBoard gameBoard){
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Player currentPlayer = saveAndSyncPlayer(session, gameBoard);
            Set<Long> inventoryIds = getInventoryIds(currentPlayer);

            cleanupRemovedOccupants(session, gameBoard);
            cleanupOrphanedCollectibles(session, gameBoard, inventoryIds);
            saveBoardOccupants(session, gameBoard, inventoryIds);

            session.getTransaction().commit();
            System.out.println("Partita salvata e database sincronizzato correttamente!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public IGameBoard loadGame(){
        try (Session session = sessionFactory.openSession()) {
            List<Occupant> occupants = session.createQuery("FROM Occupant", Occupant.class).getResultList();

            Player loadedPlayer = extractPlayer(occupants);
            updateMapGenerator(loadedPlayer);

            IGameBoard gameBoard = mapGenerator.generateExistantMap();
            populateBoardWithOccupants(gameBoard, occupants, loadedPlayer);

            System.out.println("Partita caricata con successo!");
            return gameBoard;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Player saveAndSyncPlayer(Session session, IGameBoard gameBoard) {
        Player currentPlayer = gameBoard.getPlayer();
        if(currentPlayer == null) {
            return null;
        }

        Player existingDBPLayer = session.createQuery("FROM Player", Player.class)
                .uniqueResultOptional()
                .orElse(null);
        if (existingDBPLayer != null) {
            currentPlayer.setId(existingDBPLayer.getId());
        }
        Coordinates playerCoords = gameBoard.getOccupantCoordinates(currentPlayer);
        if(playerCoords != null) {
            currentPlayer.setCoordinates(playerCoords);
        }
        if (currentPlayer.getInventory() != null) {
            List<Collectible> currentItems = new ArrayList<>(currentPlayer.getInventory());
            currentPlayer.getInventory().clear();

            for (Collectible item : currentItems) {
                item.setCoordinates(null);

                if (item.getId() != null) {
                    Collectible dbCheck = session.get(Collectible.class, item.getId());
                    if (dbCheck == null) {
                        item.setId(null);
                    }
                }

                Collectible mergedItem = session.merge(item);
                currentPlayer.getInventory().add(mergedItem);
            }
        }

        return session.merge(currentPlayer);
    }

    private Set<Long> getInventoryIds(Player player) {
        if (player == null || player.getInventory() == null) {
            return Set.of();
        }
        return player.getInventory().stream()
                .filter(inv -> inv.getId() != null)
                .map(Collectible::getId)
                .collect(Collectors.toSet());
    }

    private void cleanupRemovedOccupants(Session session, IGameBoard gameBoard){
        List<Occupant> allDbOccupants = session.createQuery("FROM Occupant", Occupant.class).getResultList();
        for (Occupant occ : allDbOccupants) {
            if (occ instanceof Player || occ instanceof Obstacle || occ instanceof Collectible) {
                continue;
            }

            boolean onBoard = gameBoard.getGameMap().values().stream()
                    .anyMatch(boardOcc -> boardOcc != null && occ.getId() != null && occ.getId().equals(boardOcc.getId()));

            if (!onBoard) {
                session.remove(occ);
            }
        }
    }

    private void cleanupOrphanedCollectibles(Session session, IGameBoard gameBoard, Set<Long> inventoryIds) {
        List<Collectible> allDbCollectibles = session.createQuery("FROM Collectible", Collectible.class).getResultList();
        for (Collectible dbItem : allDbCollectibles) {
            boolean inInventory = inventoryIds.contains(dbItem.getId());
            boolean onBoard = gameBoard.getGameMap().values().stream()
                    .anyMatch(boardOcc -> boardOcc != null && boardOcc.getId() != null && boardOcc.getId().equals(dbItem.getId()));

            if (!inInventory && !onBoard) {
                session.remove(dbItem);
            }
        }
    }

    private void saveBoardOccupants(Session session, IGameBoard gameBoard, Set<Long> inventoryIds){
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
    }

    private Player extractPlayer(List<Occupant> occupants) {
        return occupants.stream()
                .filter(Player.class::isInstance)
                .map(Player.class::cast)
                .findFirst()
                .orElse(null);
    }

    private void updateMapGenerator(Player loadedPlayer) {
        if (loadedPlayer != null && loadedPlayer.getCurrentLevel() > 1) {
            LevelConfig config = LevelConfigFactory.getLevelConfig(loadedPlayer.getCurrentLevel());
            this.mapGenerator = new LevelMapGenerator(config, loadedPlayer);
        }
    }

    private void populateBoardWithOccupants(IGameBoard gameBoard, List<Occupant> occupants, Player loadedPlayer) {
        boolean playerAdded = false;
        Set<Long> inventoryIds = getInventoryIds(loadedPlayer);

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
    }



    public void setMapGenerator(MapGenerator mapGenerator) {
        this.mapGenerator = mapGenerator;
    }
}