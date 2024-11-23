import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Frame extends JPanel implements MouseListener {
    private Game g;
    private JFrame frame;
    private boolean restart;

    Frame() {
        restart=false;
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
                try {
                    startGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        mediumButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State.setDifficulty(Difficulty.MEDIUM);
                try {
                    startGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State.setDifficulty(Difficulty.HARD);
                try {
                    startGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        extremeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                State.setDifficulty(Difficulty.HARDER);
                try {
                    startGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        frame.setVisible(true);
    }

    private void startGame() throws IOException {
        // game start
        // Clear all components in the frame
        frame.getContentPane().removeAll();

        this.g = new Game();

        frame.getContentPane().add(g);
        frame.getContentPane().addMouseListener(this);
        frame.setVisible(true);
        frame.revalidate();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(this.g.getGameState()==GameState.IN_PROGRESS) {
            try {
                g.mouseClicked(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            this.frame.repaint();
        }

        if(this.g.getGameState()!=GameState.IN_PROGRESS) {
            if (this.restart) {
                try {
                    this.restart = false;
                    startGame();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                drawEndScreen(this.g.getGameState());
                this.restart = true;
            }
        }
    }

    void drawEndScreen(GameState state) {
        this.removeAll();
        frame.getContentPane().removeAll();
        JTextField text;
        if(state==GameState.LOST) {
             text= new JTextField("Lost");
        }else {
            text = new JTextField("Won");
        }
        text.setEditable(false);
        this.add(text);
        frame.add(this);
        //frame.setVisible(true);
        frame.revalidate();
        frame.repaint();
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
