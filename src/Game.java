import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends JComponent {
    static public final int boardSize = 7;
    static public final int r = 25;
    static public final int offset = 7;
    private List<List<Tile>> tiles;
    private GameState gameState;
    private Penguin penguin;
    private Algorithm algorithm;

    public Game() throws IOException {
        gameState = GameState.IN_PROGRESS;
        this.tiles = new ArrayList<>();
        this.algorithm =  new RandomAlgorithm();
        for (int i = 0; i < boardSize; i++) {
            List<Tile> tileRow = new ArrayList<>();
            for (int j = 0; j < boardSize; j++) {
                int a = (int) Math.sqrt(Math.pow(r, 2) - Math.pow(((double) r) / 2, 2));
                int nextValX = j * (2 * a + offset) + a + i % 2 * (a + offset / 2);
                int nextValY = i * ((int) (1.5 * r + Math.sqrt(Math.pow(offset, 2) - Math.pow(((double) offset / 2), 2))));
                tileRow.add(new Tile(100 + nextValX, 100 + nextValY, r, i, j));

            }
            this.tiles.add(tileRow);
        }
        recolorSome();
        penguin=new Penguin(tiles.get(Game.boardSize/2).get(Game.boardSize/2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                // Rectangle bounds=this.tiles.get(i).get(j).getBounds();
                g2.setColor(Color.white);
                g2.drawPolygon(this.tiles.get(i).get(j));
                g2.setColor(this.tiles.get(i).get(j).getState().getColor());
                g2.fillPolygon(this.tiles.get(i).get(j));
                // g2.setColor(Color.red);
                // g2.drawRect(bounds.x,bounds.y,bounds.width,bounds.height);
            }
        }
        g2.drawImage(penguin.getIcon(),penguin.getPos().getMiddlePointX()-9,penguin.getPos().getMiddlePointY()-9,null);

    }

    public boolean isCenter(int x, int y){
        return x==Game.boardSize/2 && y==Game.boardSize/2;
    }

    public void setGameState(GameState state) {
        this.gameState = state;
    }

    public void recolorSome() {
        if (State.getDifficulty() == Difficulty.EASY || State.getDifficulty()==Difficulty.MEDIUM) {
            int numberOfPainted = generateRandom(12, 16);
            while (numberOfPainted != 0) {
                Tile colored = tiles.get(generateRandom(0, boardSize - 1)).get(generateRandom(0, boardSize - 1));
                if (!isCenter(colored.getXindex(), colored.getYindex())) {
                    colored.setState(State.FINAL);
                    numberOfPainted--;
                }
            }
        }
        else if (State.getDifficulty() == Difficulty.HARD) {
            int numberOfPainted = generateRandom(16, 20);
            while (numberOfPainted != 0) {
                Tile colored = tiles.get(generateRandom(0, boardSize - 1)).get(generateRandom(0, boardSize - 1));
                int colorTo = generateRandom(0, 2);
                if (!isCenter(colored.getXindex(), colored.getYindex())) {
                    if (colorTo == 1)
                        colored.setState(State.SECOND);
                    if (colorTo == 2)
                        colored.setState(State.THIRD);
                    else 
                        colored.setState(State.FINAL);
                    numberOfPainted--;
                }
            }
        }
        else if (State.getDifficulty() == Difficulty.HARDER) {
            int numberOfPainted = generateRandom(20, 27);
            while (numberOfPainted != 0) {
                Tile colored = tiles.get(generateRandom(0, boardSize - 1)).get(generateRandom(0, boardSize - 1));
                int colorTo = generateRandom(0, 1);
                if (!isCenter(colored.getXindex(), colored.getYindex())) {
                    if (colorTo == 1)
                        colored.setState(State.SECOND);
                    else
                        colored.setState(State.THIRD);
                    numberOfPainted--;
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e) throws IOException {
        if(penguin.getPos().containsPoint(e.getX(), e.getY())) {
            return;
        }
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Tile tmp = tiles.get(i).get(j);
                tmp.nextRound(tmp.containsPoint(e.getX(), e.getY()));
            }
        }
        algorithm.step(this, penguin);
        penguin.update();
    }

    public static int generateRandom(int min, int max) {
        Random r = new Random();
        int result = r.nextInt((max - min) + 1) + min;
        return result;
    }

    public Tile getTile(int x, int y) {
        return tiles.get(x).get(y);
    }

    public List<Tile> getNeighbours(int x, int y){
        List<Tile> neighbours = new ArrayList<>();
        if(x==0){ //elso sor
            if(y==0){ //bal sarok
                neighbours.add(null);
                neighbours.add(tiles.get(x).get(y+1));
                neighbours.add(tiles.get(x+1).get(y));
                neighbours.add(null);
                neighbours.add(null);
                neighbours.add(null);

            }else if(y==boardSize-1){ //jobb sarok
                neighbours.add(null);
                neighbours.add(null);
                neighbours.add(tiles.get(x+1).get(y));
                neighbours.add(tiles.get(x+1).get(y-1));
                neighbours.add(tiles.get(x).get(y-1));
                neighbours.add(null);

            }else{ // elso sor
                neighbours.add(null);
                neighbours.add(tiles.get(x).get(y+1));
                neighbours.add(tiles.get(x+1).get(y));
                neighbours.add(tiles.get(x+1).get(y-1));
                neighbours.add(tiles.get(x).get(y-1));
                neighbours.add(null);

            }
        }else if(x==boardSize-1){ //utolso sor
            if(y==0){ //bal sarok
                neighbours.add(tiles.get(x-1).get(y));
                neighbours.add(tiles.get(x).get(y+1));
                neighbours.add(null);
                neighbours.add(null);
                neighbours.add(null);
                neighbours.add(null);

            }else if(y==boardSize-1){ //jobb sarok
                neighbours.add(tiles.get(x-1).get(y));
                neighbours.add(null);
                neighbours.add(null);
                neighbours.add(null);
                neighbours.add(tiles.get(x).get(y-1));
                neighbours.add(tiles.get(x-1).get(y-1));
            }else{ //utolso sor
                neighbours.add(tiles.get(x-1).get(y));
                neighbours.add(tiles.get(x).get(y+1));
                neighbours.add(null);
                neighbours.add(null);
                neighbours.add(tiles.get(x).get(y-1));
                neighbours.add(tiles.get(x-1).get(y-1));
            }
        }else if(y==0){ //bal oszlop
            if(x%2==0){
                neighbours.add(tiles.get(x-1).get(y));
                neighbours.add(tiles.get(x).get(y+1));
                neighbours.add(tiles.get(x+1).get(y));
                neighbours.add(null);
                neighbours.add(null);
                neighbours.add(null);
            }
            else{
                neighbours.add(tiles.get(x-1).get(y+1));
                neighbours.add(tiles.get(x).get(y+1));
                neighbours.add(tiles.get(x+1).get(y+1));
                neighbours.add(tiles.get(x+1).get(y));
                neighbours.add(null);
                neighbours.add(tiles.get(x-1).get(y));
            }
        }else if(y==boardSize-1){ //jobb oszlop
            if(x%2==0){
                neighbours.add(tiles.get(x-1).get(y));
                neighbours.add(null);
                neighbours.add(tiles.get(x+1).get(y));
                neighbours.add(tiles.get(x+1).get(y-1));
                neighbours.add(tiles.get(x).get(y-1));
                neighbours.add(tiles.get(x-1).get(y-1));
            }
            else{
                neighbours.add(null);
                neighbours.add(null);
                neighbours.add(null);
                neighbours.add(tiles.get(x+1).get(y));
                neighbours.add(tiles.get(x).get(y-1));
                neighbours.add(tiles.get(x-1).get(y));
            }
        }else{ //tobbi
            if(x%2==0){
                neighbours.add(tiles.get(x-1).get(y));
                neighbours.add(tiles.get(x).get(y+1));
                neighbours.add(tiles.get(x+1).get(y));
                neighbours.add(tiles.get(x+1).get(y-1));
                neighbours.add(tiles.get(x).get(y-1));
                neighbours.add(tiles.get(x-1).get(y-1));
            }else{
                neighbours.add(tiles.get(x-1).get(y+1));
                neighbours.add(tiles.get(x).get(y+1));
                neighbours.add(tiles.get(x+1).get(y+1));
                neighbours.add(tiles.get(x+1).get(y));
                neighbours.add(tiles.get(x).get(y-1));
                neighbours.add(tiles.get(x-1).get(y));
            }
        }

        return neighbours;
    }
}
