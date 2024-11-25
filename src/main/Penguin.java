package main;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Penguin {
    private Tile pos; // the tile of the penguin
    private Direction dir; // the direction of it
    private BufferedImage icon; // the image

    /**
     * Makes a penguin with the lite position, direction to the west and the given
     * icon
     * 
     * @param pos the tile on which the penguin stands
     * @throws IOException
     */
    Penguin(Tile pos) throws IOException {
        this.pos = pos;
        this.dir = Direction.WEST;
        this.icon = ImageIO.read(new File("assets/west.png"));
    }

    /**
     * getters and setters
     */
    public BufferedImage getIcon() {
        return icon;
    }

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public void setPos(Tile t) {
        pos = t;
    }

    public Tile getPos() {
        return pos;
    }

    /**
     * updates the image based on the direction
     * 
     * @throws IOException
     */
    public void update() throws IOException {
        if (dir == Direction.WEST) {
            this.icon = ImageIO.read(new File("assets/west.png"));
        } else if (dir == Direction.EAST) {
            this.icon = ImageIO.read(new File("assets/east.png"));
        } else if (dir == Direction.NORTH_EAST) {
            this.icon = ImageIO.read(new File("assets/north east.png"));
        } else if (dir == Direction.SOUTH_EAST) {
            this.icon = ImageIO.read(new File("assets/south east.png"));
        } else if (dir == Direction.NORTH_WEST) {
            this.icon = ImageIO.read(new File("assets/north west.png"));
        } else if (dir == Direction.SOUTH_WEST) {
            this.icon = ImageIO.read(new File("assets/south west.png"));
        }
    }
}
