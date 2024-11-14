import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Game extends JComponent {
    static public final int boardSize=5;
    static public final int r=50;
    static public final int offset=10;
    List<List<Tile>> tiles;

    public Game() {
        this.tiles=new ArrayList<>();
        for(int i=0;i<boardSize;i++){
            List<Tile> tileRow=new ArrayList<>();
            for(int j=0;j<boardSize;j++){
                int nextValX=2*j*(r+offset)+i%2*(r+offset/2);
                int nextValY=i*(int)(r+offset+Math.tan(Math.PI/6)*r);
                tileRow.add(new Tile(100+nextValX,100+nextValY,r));
            }
            this.tiles.add(tileRow);
        }
    }

    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                g2.setColor(Color.black);
                g2.drawPolygon(this.tiles.get(i).get(j).getPolygon());
                g2.setColor(this.tiles.get(i).get(j).getState().getColor());
                g2.fillPolygon(this.tiles.get(i).get(j).getPolygon());
            }
        }
    }
}
