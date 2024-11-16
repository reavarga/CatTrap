import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Frame implements MouseListener {
    private Game g;
    private JFrame frame;

    Frame(){
        this.g = new Game();
        this.frame = new JFrame("Frame");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(g);
        frame.getContentPane().addMouseListener(this);
        frame.setVisible(true);
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
