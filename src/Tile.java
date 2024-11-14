//import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;

public class Tile {
    private int r; //atlo
    private int middlePointX, middlePointY;
    State state;
    Polygon p;

/*    public Tile() {
        this.r=0;
        this.middlePoint = new Coordinate(0,0);
        this.free = true;
    }*/

    public Tile(int middlePointX, int middlePointY, int r) {
        this.middlePointX = middlePointX;
        this.middlePointY = middlePointY;
        this.r = r;
        this.state = State.FIRST;
        int [] coordsX = new int[6];
        int [] coordsY = new int[6];

        coordsX[0] = middlePointX;
        coordsX[1] = middlePointX+r;
        coordsX[2] = middlePointX+r;
        coordsX[3] = middlePointX;
        coordsX[4] = middlePointX-r;
        coordsX[5] = middlePointX-r;

        coordsY[0] = middlePointY+r;
        coordsY[1] = middlePointY+r/2;
        coordsY[2] = middlePointY-r/2;
        coordsY[3] = middlePointY-r;
        coordsY[4] = middlePointY-r/2;
        coordsY[5] = middlePointY+r/2;
        this.p = new Polygon(coordsX, coordsY, 6);
    }



    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Polygon getPolygon() {
        return this.p;
    }

    public void nextRound(boolean clicked){
        if(this.state != State.FINAL) {
            if (clicked) {
                int nextState = (this.state.ordinal() + 1) % State.values().length;
                this.state = State.values()[nextState];
            }else if(this.state != State.FIRST) {
                int nextState = (this.state.ordinal() - 1) % State.values().length;
                this.state = State.values()[nextState];
            }
        }
    }
}

