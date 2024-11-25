package main;

/**
 * An interface for the algorithm
 */
public interface Algorithm {
    /**
     * 
     * @param game    the game the player plays
     * @param penguin the penguin of the game
     */
    public void step(Game game, Penguin penguin);

    /**
     * a toSting to write out the algo
     * 
     * @return
     */
    public String toString();
}
