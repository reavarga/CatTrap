import java.awt.*;

public enum State {
    FIRST(135, 206, 250),
    SECOND(56,172,236),
    THIRD(21,105,199),
    FINAL(0,65,194);

    private final Color color;
    private static Difficulty difficulty;

    State(int r, int g, int b) {
        color = new Color(r, g, b);
    }
    public Color getColor() {
        return color;
    }
    public static void setDifficulty(Difficulty difficulty) {
        State.difficulty=difficulty;
    }
    public static Difficulty getDifficulty() {
        return difficulty;
    }
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
