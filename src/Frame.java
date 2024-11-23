import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class Frame extends JPanel implements MouseListener {
    private Game g;
    private JFrame frame;

    Frame() {
        this.frame = new JFrame("Penguin Trap");
        frame.setSize(800, 650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        // start screen
        drawStartScreen();
    }

    private void drawStartScreen() {
        // frame.setLayout(new FlowLayout());
        JButton loadOldGameButton= new JButton("Load an older game!");
        JTextField textAboutAlgorithm = new JTextField("Choose algorithm for the Penguin!");
        JRadioButton randomButton = new JRadioButton("Random", true);
        JRadioButton shortestButton = new JRadioButton("Short path", false);
        ButtonGroup radioGroup = new ButtonGroup();
        radioGroup.add(randomButton);
        radioGroup.add(shortestButton);
        JButton easyButton = new JButton("Easy");
        JButton mediumButton = new JButton("Medium");
        JButton hardButton = new JButton("Hard");
        JButton extremeButton = new JButton("Extreme");
        

        // adding buttons to frame
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(textAboutAlgorithm);
        buttonPanel.add(randomButton);
        buttonPanel.add(shortestButton);
        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);
        buttonPanel.add(extremeButton);
        buttonPanel.setBorder(BorderFactory.createBevelBorder(0));
        buttonPanel.setPreferredSize(new Dimension(400, 200));
        JPanel wrapButtons = new JPanel();
        wrapButtons.setLayout(new FlowLayout());
        wrapButtons.add(buttonPanel);
        this.setLayout(new BorderLayout());
        this.add(wrapButtons, BorderLayout.SOUTH);

        // adding the picture
        ImageIcon originalIcon = new ImageIcon("assets/logo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel imageLabel = new JLabel(resizedIcon);
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imageLabel.setVerticalAlignment(SwingConstants.CENTER);
        this.add(imageLabel, BorderLayout.CENTER);

        // callbacks
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

        extremeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State.setDifficulty(Difficulty.HARDER);
                startGame();
            }
        });

        frame.setVisible(true);
    }

    private void startGame() {
        // game start
        // Clear all components in the frame
        frame.getContentPane().removeAll();

        System.out.println("WTF " + frame.getContentPane().getComponentCount());
        this.g = new Game();

        frame.getContentPane().add(g);
        frame.getContentPane().addMouseListener(this);
        System.out.println("WTF " + frame.getContentPane().getComponentCount());
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
