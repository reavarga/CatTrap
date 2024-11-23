import javax.swing.*;

public class Penguin {
    private int x;
    private int y;
    Direction dir;
    ImageIcon icon;
    Penguin() {
        this.x=(Game.boardSize-1)/2;
        this.y=(Game.boardSize-1)/2;
        this.dir=Direction.WEST;
        this.icon=new ImageIcon("assets/west.png");
    }
    public ImageIcon getIcon() {
        return icon;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
    public void update(){
        if(dir==Direction.WEST){
            this.icon=new ImageIcon("assets/west.png");
        }else if(dir==Direction.EAST){
            this.icon=new ImageIcon("assets/east.png");
        }else if(dir==Direction.NORTH_EAST){
            this.icon=new ImageIcon("assets/north east.png");
        }else if(dir==Direction.SOUTH_EAST){
            this.icon=new ImageIcon("assets/south east.png");
        }else if(dir==Direction.NORTH_WEST){
            this.icon=new ImageIcon("assets/north west.png");
        }else if(dir==Direction.SOUTH_WEST){
            this.icon=new ImageIcon("assets/south west.png");
        }
    }
}
