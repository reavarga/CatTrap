import java.awt.*;
/**
 * The state of the tiles
 * first being the lightest last being the darkest
 */
public enum State {
    FIRST(135, 206, 250),
    SECOND(56,172,236),
    THIRD(21,105,199),
    FINAL(0,65,194);

    private final Color color;
    private static Difficulty difficulty;

/**
 * constructor for the state
 * @param r red
 * @param g green
 * @param b blue
 */
    State(int r, int g, int b) {
        color = new Color(r, g, b);
    }

/**
 * 
 * @return the color of the tile
 */
    public Color getColor() {
        return color;
    }
/**
 * sets the difficulty to the one given in the param
 * @param difficulty
 */
    public static void setDifficulty(Difficulty difficulty) {
        State.difficulty=difficulty;
    }

/**
 * getter
 * @return a difficulty
 */
    public static Difficulty getDifficulty() {
        return difficulty;
    }

/**
 * returns available states based on the difficulty
 * @return a state
 */
    public static State[] availableValues() {
        if(difficulty==Difficulty.EASY) {
            return new State[]{FIRST, FINAL};
        }else if(difficulty==Difficulty.MEDIUM) {
            return new State[]{FIRST, SECOND, FINAL};
        }else if(difficulty==Difficulty.HARD) {
            return State.values();
        }else{
            return new State[]{FIRST, SECOND, THIRD};
        }

    }
}
