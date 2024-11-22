import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game extends JComponent {
    static public final int boardSize = 7;
    static public final int r = 25;
    static public final int offset = 7;
    List<List<Tile>> tiles;

    public Game() {
        this.tiles = new ArrayList<>();
        for (int i = 0; i < boardSize; i++) {
            List<Tile> tileRow = new ArrayList<>();
            for (int j = 0; j < boardSize; j++) {
                int a = (int) Math.sqrt(Math.pow(r, 2) - Math.pow(((double) r) / 2, 2));
                int nextValX = j * (2 * a + offset) + a + i % 2 * (a + offset / 2);
                int nextValY = i
                        * ((int) (1.5 * r + Math.sqrt(Math.pow(offset, 2) - Math.pow(((double) offset / 2), 2))));
                tileRow.add(new Tile(100 + nextValX, 100 + nextValY, r));

            }
            this.tiles.add(tileRow);
        }
        recolorSome();
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
                tiles.get(i).get(j).setXindex(i);
                tiles.get(i).get(j).setYindex(j);

            }
        }

    }

    public void recolorSome() {
        if (State.getDifficulty() == Difficulty.EASY || State.getDifficulty()==Difficulty.MEDIUM) {
            int numberOfPainted = generateRandom(12, 16);
            while (numberOfPainted != 0) {
                Tile colored = tiles.get(generateRandom(0, boardSize - 1)).get(generateRandom(0, boardSize - 1));
                if (colored.getXindex() == (boardSize - 1) / 2 && colored.getYindex() == (boardSize - 1) / 2)
                    colored.setState(State.FIRST);
                else {
                    colored.setState(State.FINAL);
                }
                numberOfPainted--;
            }
        }
        if (State.getDifficulty() == Difficulty.HARD) {
            int numberOfPainted = generateRandom(16, 20);
            while (numberOfPainted != 0) {
                Tile colored = tiles.get(generateRandom(0, boardSize - 1)).get(generateRandom(0, boardSize - 1));
                int colorTo = generateRandom(0, 9);
                if (colored.getXindex() == (boardSize - 1) / 2 && colored.getYindex() == (boardSize - 1) / 2)
                    colored.setState(State.FIRST);
                else {
                    if (colorTo % 3 == 1)
                        colored.setState(State.SECOND);
                    if (colorTo % 3 == 2)
                        colored.setState(State.THIRD);
                    else 
                        colored.setState(State.FINAL);
                }
                numberOfPainted--;
            }
        }
        if (State.getDifficulty() == Difficulty.HARD) {
            int numberOfPainted = generateRandom(16, 20);
            while (numberOfPainted != 0) {
                Tile colored = tiles.get(generateRandom(0, boardSize - 1)).get(generateRandom(0, boardSize - 1));
                int colorTo = generateRandom(0, 9);
                if (colored.getXindex() == (boardSize - 1) / 2 && colored.getYindex() == (boardSize - 1) / 2)
                    colored.setState(State.FIRST);
                else {
                    if (colorTo % 2 == 1)
                        colored.setState(State.SECOND);
                    else
                        colored.setState(State.FINAL);
                }
                numberOfPainted--;
            }
        }
        if (State.getDifficulty() == Difficulty.HARDER) {
            int numberOfPainted = generateRandom(20, 27);
            while (numberOfPainted != 0) {
                Tile colored = tiles.get(generateRandom(0, boardSize - 1)).get(generateRandom(0, boardSize - 1));
                int colorTo = generateRandom(0, 9);
                if (colored.getXindex() == (boardSize - 1) / 2 && colored.getYindex() == (boardSize - 1) / 2)
                    colored.setState(State.FIRST);
                else {
                    if (colorTo % 2 == 1)
                        colored.setState(State.SECOND);
                    else
                        colored.setState(State.THIRD);
                }
                numberOfPainted--;
            }
        }
    }

    public void mouseClicked(MouseEvent e) {
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                Tile tmp = tiles.get(i).get(j);
                tmp.nextRound(tmp.containsPoint(e.getX(), e.getY()));
            }

        }
    }

    public int generateRandom(int min, int max) {
        Random r = new Random();
        int result = r.nextInt(max - min) + min;
        return result;
    }
}
