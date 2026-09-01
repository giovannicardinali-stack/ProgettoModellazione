package it.unicam.cs.mpgc.rpg125928.view;

import it.unicam.cs.mpgc.rpg125928.model.occupant.NPC;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Obstacle;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Occupant;
import it.unicam.cs.mpgc.rpg125928.model.occupant.Player;
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

        URL resource = getClass().getResource(path);
        if (resource == null) {

            String cleanPath = path.startsWith("/") ? path.substring(1) : path;
            resource = Thread.currentThread().getContextClassLoader().getResource(cleanPath);
        }
        return resource != null ? resource.toExternalForm() : null;
    }
}
