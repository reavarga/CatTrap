import java.awt.*;

public enum State {
    FIRST(135, 206, 250),
    SECOND(56,172,236),
    THIRD(21,105,199),
    FINAL(0,65,194);

    private final Color color;

    State(int r, int g, int b) {
        color = new Color(r, g, b);
    }
    public Color getColor() {
        return color;
    }
}
