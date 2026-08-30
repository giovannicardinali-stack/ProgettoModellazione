package it.unicam.cs.mpgc.rpg125928.view;

import it.unicam.cs.mpgc.rpg125928.model.*;
import javafx.scene.layout.Pane;

import java.net.URL;

public class TileRenderer {

    private final int tileSize;

    public TileRenderer(int tileSize) {
        this.tileSize = tileSize;
    }

    public Pane createTilePane(Occupant occupant){
        String imagePath = resolveImagePath(occupant);

        Pane pane = new Pane();
        pane.setPrefSize(tileSize, tileSize);
        pane.setMinSize(tileSize, tileSize);
        pane.setMaxSize(tileSize, tileSize);

        if(imagePath != null){
            pane.setStyle("-fx-background-image: url('" + imagePath + "');" +
                    "-fx-background-size: 100% 100%;" +
                    "-fx-background-repeat: no-repeat;");
        } else {
            // Fallback di sicurezza: se l'immagine non si carica, coloriamo la tile
            // così vediamo subito se il percorso dell'immagine è errato
            if(occupant instanceof Obstacle){
                pane.setStyle("-fx-background-color: gray; -fx-border-color: black;");
            } else if(occupant instanceof Player){
                pane.setStyle("-fx-background-color: blue; -fx-border-color: black;");
            } else if(occupant instanceof NPC){
                pane.setStyle("-fx-background-color: green; -fx-border-color: black;");
            }
        }

        return pane;
    }

    public String resolveImagePath(Occupant occupant){
        if(occupant instanceof Obstacle){
            return getResourcePath("/images/wall.png");
        }
        else if(occupant instanceof Player){
            return getResourcePath("/images/player.png");
        }
        else if (occupant instanceof NPC) {
            return getResourcePath("/images/NPC.png");
        }
        return null;
    }

    public String getResourcePath(String path){
        // Cerca partendo dalla radice del classpath usando lo slash iniziale
        URL resource = getClass().getResource(path);
        if (resource == null) {
            // Tentativo alternativo rimuovendo lo slash iniziale
            String cleanPath = path.startsWith("/") ? path.substring(1) : path;
            resource = Thread.currentThread().getContextClassLoader().getResource(cleanPath);
        }

        System.out.println("Cerco risorsa: " + path + " -> Trovata: " + (resource != null));
        return resource != null ? resource.toExternalForm() : null;
    }
}
