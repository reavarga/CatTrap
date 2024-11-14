import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        Game game = new Game();

        JFrame frame = new JFrame("Frame");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(game);
        frame.setVisible(true);
    }

}