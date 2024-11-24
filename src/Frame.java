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
        JLabel textAboutAlgorithm = new JLabel("Choose algorithm for the Penguin!");
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
        JLabel text;
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if(state==GameState.LOST) {
             text= new JLabel("You Lost!");
             text.setFont(new Font("Arial",Font.BOLD,42));
        }else {
            text = new JLabel(" You Won!");
            text.setFont(new Font("Arial",Font.BOLD,42));
        }
        text.setHorizontalAlignment(JLabel.CENTER); // Center the text horizontally
        text.setVerticalAlignment(JLabel.CENTER);   // Center the text vertically
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 20, 0); // Add some spacing
        mainPanel.add(text, gbc);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); // Center buttons with spacing
        JButton newGameButton = new JButton("New Game");
        JButton saveProgressButton = new JButton("Save Progress");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(newGameButton);
        buttonPanel.add(saveProgressButton);
        buttonPanel.add(exitButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 10, 0); // Add spacing above the buttons
        mainPanel.add(buttonPanel, gbc);

        // Add the main panel to the frame
        frame.add(mainPanel, BorderLayout.CENTER);
   
       // Refresh the frame to apply changes
       frame.revalidate();
       frame.repaint();
       
    

       newGameButton.addActionListener(e -> {
        this.removeAll();
        frame.getContentPane().removeAll();
        System.out.println("New Game button clicked!");
       // drawStartScreen(); // Start a new game
        
    });


    exitButton.addActionListener(e -> {
        //System.out.println("Exit button clicked!");
        System.exit(0); 
    });
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
