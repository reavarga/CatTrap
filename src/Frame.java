import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Frame extends JPanel implements MouseListener {
    private Game g;
    private JFrame frame;
    private int maxScore;
    private int currentScore;


/**
 * sets the scores to zero and makes the frame
 */
    Frame() {
        this.maxScore=0;
        this.currentScore=0;
        this.frame = new JFrame("Penguin Trap");
        frame.setSize(800, 650);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        // start screen
        drawStartScreen();
    }
/**
 * sets the maxscore
 * @param maxScore to this
 */
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
    }
/**
 * sets the current score
 * @param currentScore
 */
    public void setCurrentScore(int currentScore) {
        this.currentScore = currentScore;
    }
/**
 * starts a game by first clearing a screen and then ads the scores to the bottom and adds the game
 * @throws IOException
 */

    public void startGame() throws IOException {
        // game start
        // Clear all components in the frame
        frame.getContentPane().removeAll();
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JLabel cScore=new JLabel("Current Score: "+currentScore);
        JLabel mScore=new JLabel("Max Score: "+maxScore);
        JPanel scorePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        this.g = new Game();
        //this.g.add(cScore);
        //this.g.add(mScore);
        scorePanel.add(cScore, BorderLayout.CENTER);
        scorePanel.add(mScore, BorderLayout.CENTER);
        scorePanel.setPreferredSize(new Dimension(200,30));
        scorePanel.setBorder(BorderFactory.createBevelBorder(0));
        panel.add(scorePanel, BorderLayout.SOUTH);
        panel.add(this.g);
        frame.getContentPane().add(panel);

        frame.getContentPane().addMouseListener(this);
        frame.setVisible(true);
        frame.revalidate();
    }


/**
 * draws the starter screen for the game with the logo and buttons
 * has actionListeners for the buttons
 */
    private void drawStartScreen() {
        // frame.setLayout(new FlowLayout());
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
        JButton loadButton = new JButton("Continue old game!");


        // adding buttons to frame
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(textAboutAlgorithm);
        buttonPanel.add(randomButton);
        buttonPanel.add(shortestButton);
        buttonPanel.add(easyButton);
        buttonPanel.add(mediumButton);
        buttonPanel.add(hardButton);
        buttonPanel.add(extremeButton);
        buttonPanel.add(loadButton);
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

        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    LeaderBoard board= new LeaderBoard();
                    String name = JOptionPane.showInputDialog(null, "Enter your name:", "Input Dialog", JOptionPane.QUESTION_MESSAGE);
                    board.readEntry(name, Frame.this);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        frame.setVisible(true);
    }
/**
 * draws the enscreen when the gamestate is set to won or lost
 * first removes everything then tells if the player won or lost 
 * then offers new game, save score or exit
 * ha actionlisteners for the buttons
 * @param state
 */
    private void drawEndScreen(GameState state) {

        this.removeAll();
        frame.getContentPane().removeAll();
        frame.getContentPane().removeMouseListener(this);
        JLabel text;
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        if(state==GameState.LOST) {
            this.currentScore=0;
            text= new JLabel("You Lost!");
            text.setFont(new Font("Arial",Font.BOLD,42));
        }else {
            this.currentScore++;
            text = new JLabel(" You Won!");
            text.setFont(new Font("Arial",Font.BOLD,42));
        }
        if(this.currentScore>this.maxScore){
            this.maxScore=this.currentScore;
        }
        text.setHorizontalAlignment(JLabel.CENTER); 
        text.setVerticalAlignment(JLabel.CENTER);  
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 0, 20, 0); 
        mainPanel.add(text, gbc);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0)); 
        JButton newGameButton = new JButton("New Game");
        JButton saveProgressButton = new JButton("Save Progress");
        JButton exitButton = new JButton("Exit");

        buttonPanel.add(newGameButton);
        buttonPanel.add(saveProgressButton);
        buttonPanel.add(exitButton);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 10, 0); 
        mainPanel.add(buttonPanel, gbc);

        frame.add(mainPanel, BorderLayout.CENTER);
       frame.revalidate();
       frame.repaint();
       
    

       newGameButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               try {
                   startGame();
               } catch (IOException ex) {
                   throw new RuntimeException(ex);
               }
           }
       });


    exitButton.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
           System.exit(0);
       }
    });

    saveProgressButton.addActionListener(new ActionListener() {
       @Override
       public void actionPerformed(ActionEvent e) {
           if(currentScore>maxScore){
               maxScore=currentScore;
           }
           String name = JOptionPane.showInputDialog(null, "Enter your name:", "Input Dialog", JOptionPane.QUESTION_MESSAGE);
           try {
               LeaderBoard board=new LeaderBoard();
               board.newEntry(name, State.getDifficulty(),maxScore,currentScore);
           } catch (IOException ex) {
               throw new RuntimeException(ex);
           }

       }
    });
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
            drawEndScreen(this.g.getGameState());
        }
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
