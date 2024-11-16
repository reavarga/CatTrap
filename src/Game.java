import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Game extends JComponent  {
    static public final int boardSize=5;
    static public final int r=50;
    static public final int offset=10;
    List<List<Tile>> tiles;


    public Game() {
        this.tiles=new ArrayList<>();
        for(int i=0;i<boardSize;i++){
            List<Tile> tileRow=new ArrayList<>();
            for(int j=0;j<boardSize;j++){
                int a=(int)Math.sqrt(Math.pow(r,2)-Math.pow(((double)r)/2,2));
                int nextValX=j*(2*a+offset)+a+i%2*(a+offset/2);
                int nextValY=i*((int)(1.5*r+Math.sqrt(Math.pow(offset,2)-Math.pow(((double)offset/2),2))));
                tileRow.add(new Tile(100+nextValX,100+nextValY,r));
            }
            this.tiles.add(tileRow);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                //Rectangle bounds=this.tiles.get(i).get(j).getBounds();
                g2.setColor(Color.black);
                g2.drawPolygon(this.tiles.get(i).get(j));
                g2.setColor(this.tiles.get(i).get(j).getState().getColor());
                g2.fillPolygon(this.tiles.get(i).get(j));
                //g2.setColor(Color.red);
                //g2.drawRect(bounds.x,bounds.y,bounds.width,bounds.height);
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        for(int i=0;i<boardSize;i++){
            for(int j=0;j<boardSize;j++){
                Tile tmp = tiles.get(i).get(j);
                tmp.nextRound(tmp.containsPoint(e.getX(),e.getY()));
            }
        }
    }
}
