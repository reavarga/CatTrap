//import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;

public class Tile extends Polygon{
    private final int r; //atlo
    private final int middlePointX, middlePointY;
    State state;

/*    public Tile() {
        this.r=0;
        this.middlePoint = new Coordinate(0,0);
        this.free = true;
    }*/

    public Tile(int middlePointX, int middlePointY, int r){
        this.middlePointX = middlePointX;
        this.middlePointY = middlePointY;
        this.r = r;
        this.state = State.FIRST;
        int [] coordsX = new int[6];
        int [] coordsY = new int[6];

        coordsX[0] = middlePointX;
        coordsX[1] = middlePointX-(int)Math.sqrt(Math.pow(r,2)-Math.pow(((double)r)/2,2));
        coordsX[2] = middlePointX-(int)Math.sqrt(Math.pow(r,2)-Math.pow(((double)r)/2,2));
        coordsX[3] = middlePointX;
        coordsX[4] = middlePointX+(int)Math.sqrt(Math.pow(r,2)-Math.pow(((double)r)/2,2));
        coordsX[5] = middlePointX+(int)Math.sqrt(Math.pow(r,2)-Math.pow(((double)r)/2,2));

        coordsY[0] = middlePointY+r;
        coordsY[1] = middlePointY+r/2;
        coordsY[2] = middlePointY-r/2;
        coordsY[3] = middlePointY-r;
        coordsY[4] = middlePointY-r/2;
        coordsY[5] = middlePointY+r/2;

        for(int i=0;i<6;i++){
          this.addPoint(coordsX[i], coordsY[i]);
        }
    }

    private double triangleArea(int x1, int y1, int x2, int y2, int x3, int y3){
        return Math.abs(((double)(x1*(y2-y3) + x2*(y3-y1) + x3*(y1-y2)))/2.0);
    }



    public boolean containsPoint(int x, int y) {
        for(int i=0;i<6;i++){
            double A=triangleArea(xpoints[i],ypoints[i], xpoints[(i+1)%6], ypoints[(i+1)%6], middlePointX, middlePointY);

            double denominator = ((ypoints[(i+1)%6] - middlePointY)*(xpoints[i] - middlePointX) + (middlePointX - xpoints[(i+1)%6])*(ypoints[i] - middlePointY));
            double a = ((ypoints[(i+1)%6] - middlePointY)*(x - middlePointX) + (middlePointX - xpoints[(i+1)%6])*(y - middlePointY)) / denominator;
            double b = ((middlePointY - ypoints[i])*(x - middlePointX) + (xpoints[i] - middlePointX)*(y - middlePointY)) / denominator;
            double c = 1 - a - b;


            if(0 <= a && a <= 1 && 0 <= b && b <= 1 && 0 <= c && c <= 1){
                return true;
            }
        }
        return false;
    }

    public State getState() {
        return this.state;
    }

    public void setState(State state) {
        this.state = state;
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

