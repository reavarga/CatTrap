import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Frame extends JPanel implements MouseListener {
    private Game g;
    private JFrame frame;

    Frame(){
        this.frame = new JFrame("Cat Trap");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        //start screen
        drawStartScreen();
    }

    private void drawStartScreen(){
        //frame.setLayout(new FlowLayout());
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        JButton harderButton = new JButton("Harder");

        //adding buttons to frame
        this.add(easyButton);
        this.add(mediumButton);
        this.add(hardButton);
        this.add(harderButton);

        //callbacks
        easyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State.setDifficulty(Difficulty.EASY);
                startGame();
            }
        });


        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State.setDifficulty(Difficulty.MEDIUM);
                startGame();
            }
        });


        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State.setDifficulty(Difficulty.HARD);
                startGame();
            }
        });


        harderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State.setDifficulty(Difficulty.HARDER);
                startGame();
            }
        });

        frame.setVisible(true);
    }

    private void startGame(){
        //game start
        // Clear all components in the frame
        frame.getContentPane().removeAll();


        System.out.println("WTF "+frame.getContentPane().getComponentCount());
        this.g = new Game();

        frame.getContentPane().add(g);
        frame.getContentPane().addMouseListener(this);
        System.out.println("WTF "+frame.getContentPane().getComponentCount());
        frame.setVisible(true);
        frame.revalidate();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        g.mouseClicked(e);
        this.frame.repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
