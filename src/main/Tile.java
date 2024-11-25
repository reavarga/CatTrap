package main;

//import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;

public class Tile extends Polygon {
    private final int r; // radius
    private final int middlePointX, middlePointY; // the middle points
    private State state; // a.k.a the color
    private int x;
    private int y; // indexes in the 2D arraylist

    /*
     * public Tile() {
     * this.r=0;
     * this.middlePoint = new Coordinate(0,0);
     * this.free = true;
     * }
     */

/**
 * calculates all six points of the tile based on the middle point and the radius
 * @param middlePointX middle point
 * @param middlePointY middle point
 * @param r radius
 * @param x index
 * @param y index
 */
    public Tile(int middlePointX, int middlePointY, int r, int x, int y) {
        this.middlePointX = middlePointX;
        this.middlePointY = middlePointY;
        this.r = r;
        this.x = x;
        this.y = y;
        this.state = State.FIRST;
        int[] coordsX = new int[6];
        int[] coordsY = new int[6];

        coordsX[0] = middlePointX;
        coordsX[1] = middlePointX - (int) Math.sqrt(Math.pow(r, 2) - Math.pow(((double) r) / 2, 2));
        coordsX[2] = middlePointX - (int) Math.sqrt(Math.pow(r, 2) - Math.pow(((double) r) / 2, 2));
        coordsX[3] = middlePointX;
        coordsX[4] = middlePointX + (int) Math.sqrt(Math.pow(r, 2) - Math.pow(((double) r) / 2, 2));
        coordsX[5] = middlePointX + (int) Math.sqrt(Math.pow(r, 2) - Math.pow(((double) r) / 2, 2));

        coordsY[0] = middlePointY + r;
        coordsY[1] = middlePointY + r / 2;
        coordsY[2] = middlePointY - r / 2;
        coordsY[3] = middlePointY - r;
        coordsY[4] = middlePointY - r / 2;
        coordsY[5] = middlePointY + r / 2;
        System.out.println("Tile Coordinates:");
        for (int i = 0; i < coordsX.length; i++) {
            System.out.println("x: " + coordsX[i] + ", y: " + coordsY[i]);
        }
        for (int i = 0; i < 6; i++) {
            this.addPoint(coordsX[i], coordsY[i]);
        }
    }


/**
 * determines if a point is inside of a polygon by dividing the hexagon into six triangles and calculating the barycentryc coordinates
 * if every single one of the coordinates is between 0 and 1 returns true
 * @param x 
 * @param y
 * @return
 */
    public boolean containsPoint(int x, int y) {
        for (int i = 0; i < 6; i++) {
            double denominator = ((ypoints[(i + 1) % 6] - middlePointY) * (xpoints[i] - middlePointX)
                    + (middlePointX - xpoints[(i + 1) % 6]) * (ypoints[i] - middlePointY));
            double a = ((ypoints[(i + 1) % 6] - middlePointY) * (x - middlePointX)
                    + (middlePointX - xpoints[(i + 1) % 6]) * (y - middlePointY)) / denominator;
            double b = ((middlePointY - ypoints[i]) * (x - middlePointX)
                    + (xpoints[i] - middlePointX) * (y - middlePointY)) / denominator;
            double c = 1 - a - b;

            if (0 <= a && a <= 1 && 0 <= b && b <= 1 && 0 <= c && c <= 1) {
                return true;
            }
        }
        return false;
    }

    public void nextRound(boolean clicked) {
        if (this.state != State.FINAL) {
            if (clicked) {
                if (!(State.getDifficulty() == Difficulty.HARDER && this.state == State.THIRD)) {
                    int nextState = (this.state.ordinal() + 1 + State.availableValues().length)
                            % State.availableValues().length;
                    this.state = State.availableValues()[nextState];
                }
            } else if (this.state != State.FIRST) {
                int nextState = (this.state.ordinal() - 1 + State.availableValues().length)
                        % State.availableValues().length;
                this.state = State.availableValues()[nextState];
            }
        }
    }


/**
 * some getters and setters
 */

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public int getXindex() {
        return x;
    }

    public int getYindex() {
        return y;
    }

    public void setXindex(int x) {
        this.x = x;
    }

    public void setYindex(int y) {
        this.y = y;
    }

    public int getMiddlePointX(){
        return this.middlePointX;
    }
    public int getMiddlePointY(){
        return this.middlePointY;
    }

}
