
//import javax.swing.*;
import java.awt.*;
import javax.swing.JComponent;

public class Tile extends Polygon {
    private final int r; // atlo
    private final int middlePointX, middlePointY; // kozeppontok
    State state; // allapot(szin)
    public int x;
    public int y; // indexek

    /*
     * public Tile() {
     * this.r=0;
     * this.middlePoint = new Coordinate(0,0);
     * this.free = true;
     * }
     */

    public Tile(int middlePointX, int middlePointY, int r) {
        this.middlePointX = middlePointX;
        this.middlePointY = middlePointY;
        this.r = r;
        this.state = State.FIRST; // a legvilagosabb
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

    public Tile getTile(int x, int y) {
        if (this.getXindex() == x && this.getYindex() == y)
            return this;
        else
            return null;
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

    public static boolean isCorner(Tile tile) {
        return (tile.getXindex() == 0 && tile.getYindex() == 0
                || tile.getXindex() == Game.boardSize - 1 && tile.getYindex() == 0 ||
                tile.getXindex() == 0 && tile.getYindex() == Game.boardSize - 1
                || tile.getXindex() == Game.boardSize - 1 && tile.getYindex() == Game.boardSize - 1);
    }

    public static boolean isBorder(Tile tile) {
        return (tile.getXindex() != 0 && tile.getXindex() != Game.boardSize - 1 && tile.getYindex() == 0 || // felso sor
                tile.getXindex() != 0 && tile.getXindex() != Game.boardSize - 1
                        && tile.getYindex() == Game.boardSize - 1
                || // also sor
                tile.getYindex() != 0 && tile.getYindex() != Game.boardSize - 1 && tile.getXindex() == 0 || // bal sor
                tile.getYindex() != 0 && tile.getYindex() != Game.boardSize - 1
                        && tile.getXindex() == Game.boardSize - 1); // jobb sor
    }

    public static Tile[] neighboursOfATile(Tile tile) {
        if (isCorner(tile)) {
            return neighboursOfCorner(tile);
        }
        if (isBorder(tile)) {
            return neighboursOfBorder(tile);
        } else {
            Tile[] tileArray = new Tile[6]; // felso jobbal kezd es oramutato jarasaval megy korbe
            tileArray[0] = tile.getTile(tile.getXindex(), tile.getYindex() - 1);
            tileArray[1] = tile.getTile(tile.getXindex() + 1, tile.getYindex());
            tileArray[2] = tile.getTile(tile.getXindex(), tile.getYindex() + 1);
            tileArray[3] = tile.getTile(tile.getXindex() - 1, tile.getYindex() + 1);
            tileArray[4] = tile.getTile(tile.getXindex() - 1, tile.getYindex());
            tileArray[5] = tile.getTile(tile.getXindex() - 1, tile.getYindex() - 1);
            return tileArray;
        }
    }

    private static Tile[] neighboursOfCorner(Tile tile) { // mindegyik az oramutatoval egy iranyba jon letre
        if (tile.getXindex() == 0 && tile.getYindex() == 0) { // bal felso
            Tile[] tileArray = new Tile[2];
            tileArray[0] = tile.getTile(tile.getXindex() + 1, tile.getYindex()); // tole jobbra
            tileArray[1] = tile.getTile(tile.getXindex() + 1, tile.getYindex() + 1); // tole jobbra atloban
            return tileArray;
        }
        if (tile.getXindex() == Game.boardSize - 1 && tile.getYindex() == 0) { // jobb felso
            Tile[] tileArray = new Tile[3];
            tileArray[0] = tile.getTile(tile.getXindex(), tile.getYindex() + 1); // tole jobbra lent
            tileArray[1] = tile.getTile(tile.getXindex() - 1, tile.getYindex() + 1); // tole balra atlosan
            tileArray[2] = tile.getTile(tile.getXindex() - 1, tile.getYindex()); // tole balra
            return tileArray;
        }
        if (tile.getXindex() == 0 && tile.getYindex() == Game.boardSize - 1) { // bal also
            Tile[] tileArray = new Tile[2];
            tileArray[0] = tile.getTile(tile.getXindex(), tile.getYindex() - 1); // tole jobbra fent
            tileArray[1] = tile.getTile(tile.getXindex() - 1, tile.getYindex()); // tole jobbra atlosan
            return tileArray;
        } else { // jobb also
            Tile[] tileArray = new Tile[3];
            tileArray[0] = tile.getTile(tile.getXindex(), tile.getYindex() - 1); // tole jobbra fent
            tileArray[1] = tile.getTile(tile.getXindex() - 1, tile.getYindex()); // tole balra
            tileArray[2] = tile.getTile(tile.getXindex() - 1, tile.getYindex() - 1); // tole balra fent
            return tileArray;
        }

    }

    public static Tile[] neighboursOfBorder(Tile tile) { // oramutatoval egyiranyba
        if (tile.getYindex() == 0) { // felso sor
            Tile[] tileArray = new Tile[4];
            tileArray[0] = tile.getTile(tile.getXindex() + 1, tile.getYindex());// a balra levo
            tileArray[1] = tile.getTile(tile.getXindex(), tile.getYindex() + 1);
            tileArray[2] = tile.getTile(tile.getXindex() - 1, tile.getYindex() + 1);
            tileArray[3] = tile.getTile(tile.getXindex() - 1, tile.getYindex() - 1);
            return tileArray;
        }
        if (tile.getYindex() == Game.boardSize - 1) {// also sor
            Tile[] tileArray = new Tile[4];
            tileArray[0] = tile.getTile(tile.getXindex(), tile.getYindex() - 1);// a jobb felso
            tileArray[1] = tile.getTile(tile.getXindex() + 1, tile.getYindex());
            tileArray[2] = tile.getTile(tile.getXindex() - 1, tile.getYindex());
            tileArray[3] = tile.getTile(tile.getXindex() - 1, tile.getYindex() - 1);
            return tileArray;
        }
        if (tile.getXindex() == 0) { // ha bal oldali
            if (tile.getYindex() % 2 == 1) { // ha prtl indexu sor
                Tile[] tileArray = new Tile[5];
                tileArray[0] = tile.getTile(tile.getXindex() + 1, tile.getYindex() - 1);// a jobb felso
                tileArray[1] = tile.getTile(tile.getXindex() + 1, tile.getYindex());
                tileArray[2] = tile.getTile(tile.getXindex() + 1, tile.getYindex() + 1);
                tileArray[3] = tile.getTile(tile.getXindex(), tile.getYindex() + 1);
                tileArray[4] = tile.getTile(tile.getXindex(), tile.getYindex() - 1);
                return tileArray;
            } else {
                Tile[] tileArray = new Tile[3];
                tileArray[0] = tile.getTile(tile.getXindex(), tile.getYindex() - 1); // tole jobbra fent
                tileArray[1] = tile.getTile(tile.getXindex() + 1, tile.getYindex());
                tileArray[2] = tile.getTile(tile.getXindex(), tile.getYindex() + 1);
                return tileArray;
            }
        } else {// ha jobb oldali
            if (tile.getYindex() % 2 == 0) {
                Tile[] tileArray = new Tile[5];
                tileArray[0] = tile.getTile(tile.getXindex() + 1, tile.getYindex() - 1);// a jobb felso
                tileArray[1] = tile.getTile(tile.getXindex(), tile.getYindex() + 1);
                tileArray[2] = tile.getTile(tile.getXindex() - 1, tile.getYindex() + 1);
                tileArray[3] = tile.getTile(tile.getXindex() - 1, tile.getYindex());
                tileArray[4] = tile.getTile(tile.getXindex() - 1, tile.getYindex() - 1);
                return tileArray;
            } else {
                Tile[] tileArray = new Tile[3];
                tileArray[0] = tile.getTile(tile.getXindex(), tile.getYindex() + 1); // tole balra lent
                tileArray[1] = tile.getTile(tile.getXindex() - 1, tile.getYindex());
                tileArray[2] = tile.getTile(tile.getXindex(), tile.getYindex() - 1);
                return tileArray;

            }

        }
    }
}
