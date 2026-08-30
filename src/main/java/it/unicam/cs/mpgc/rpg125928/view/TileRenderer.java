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

        if(imagePath == null){
            return null;
        }

        Pane pane = new Pane();
        pane.setPrefSize(tileSize,tileSize);
        pane.setMinSize(tileSize,tileSize);
        pane.setMaxSize(tileSize,tileSize);

        pane.setStyle("-fx-background-image: url('" + imagePath + "');" +
                "-fx-background-size: 100% 100%;" +
                "-fx-background-repeat: no-repeat;");

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
        else if (occupant instanceof Collectible) {
            return getResourcePath("/images/collectible.png");
        }
        return null;
    }

    public String getResourcePath(String path){
        URL resource = getClass().getResource(path);
        return resource != null ? resource.toExternalForm() : "";
    }
}
