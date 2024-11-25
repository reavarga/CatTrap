package main;

import java.util.Collections;
import java.util.List;

public class RandomAlgorithm implements Algorithm {
    @Override
    /**
     * The stepper of the penguin in the game
     * if the neighbouring tiles have minimum one null value the game is lost
     * if there are no free neighbours the game is won
     * the penguin moves based on what it sees before him, based on it's direction
     * will try for the first three tiles it sees, if they are not free will try for
     * the
     * ones behind him
     * 
     * @param game
     * @param penguin
     */
    public void step(Game game, Penguin penguin) {
        List<Tile> neighbours = game.getNeighbours(penguin.getPos().getXindex(), penguin.getPos().getYindex());
        if (Collections.frequency(neighbours, null) > 0) {
            game.setGameState(GameState.LOST);
            return;
        }
        if (freeNeighbours(neighbours) == 0) {
            game.setGameState(GameState.WON);
            return;
        }
        if (penguin.getDir() == Direction.WEST) {
            if (stepForwardCount(penguin.getDir(), neighbours) == 0) {
                Tile tmp = getRandomNeighbour(neighbours, 0, 1, 2);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            } else {
                Tile tmp = getRandomNeighbour(neighbours, 3, 4, 5);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }
        } else if (penguin.getDir() == Direction.EAST) {
            if (stepForwardCount(penguin.getDir(), neighbours) == 0) {
                Tile tmp = getRandomNeighbour(neighbours, 3, 4, 5);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            } else {
                Tile tmp = getRandomNeighbour(neighbours, 0, 1, 2);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }
        } else if (penguin.getDir() == Direction.NORTH_EAST) {
            if (stepForwardCount(penguin.getDir(), neighbours) == 0) {
                Tile tmp = getRandomNeighbour(neighbours, 2, 3, 4);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            } else {
                Tile tmp = getRandomNeighbour(neighbours, 5, 0, 1);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }
        } else if (penguin.getDir() == Direction.NORTH_WEST) {
            if (stepForwardCount(penguin.getDir(), neighbours) == 0) {
                Tile tmp = getRandomNeighbour(neighbours, 1, 2, 3);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            } else {
                Tile tmp = getRandomNeighbour(neighbours, 4, 5, 0);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }

        } else if (penguin.getDir() == Direction.SOUTH_EAST) {
            if (stepForwardCount(penguin.getDir(), neighbours) == 0) {
                Tile tmp = getRandomNeighbour(neighbours, 4, 5, 0);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            } else {
                Tile tmp = getRandomNeighbour(neighbours, 1, 2, 3);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }

        } else if (penguin.getDir() == Direction.SOUTH_WEST) {
            if (stepForwardCount(penguin.getDir(), neighbours) == 0) {
                Tile tmp = getRandomNeighbour(neighbours, 5, 0, 1);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            } else {
                Tile tmp = getRandomNeighbour(neighbours, 2, 3, 4);
                penguin.setPos(tmp);
                penguin.setDir(getNewDirection(tmp, neighbours));
            }
        }
    }

    /**
     * Returns the number of tiles that are int the first state based on the
     * direction of
     * the penguin and the neighbours of the tile it stands on
     * 
     * @param dir        direction of the penguin
     * @param neighbours neighbours of the tile the penguin is on
     * @return the number of possibly free tiles
     */
    private int stepForwardCount(Direction dir, List<Tile> neighbours) {
        if (dir == Direction.WEST) {
            return (neighbours.get(3).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(4).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(5).getState() == State.FIRST ? 1 : 0);
        } else if (dir == Direction.EAST) {
            return (neighbours.get(0).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(1).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(2).getState() == State.FIRST ? 1 : 0);
        } else if (dir == Direction.NORTH_EAST) {
            return (neighbours.get(5).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(0).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(1).getState() == State.FIRST ? 1 : 0);
        } else if (dir == Direction.NORTH_WEST) {
            return (neighbours.get(4).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(5).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(0).getState() == State.FIRST ? 1 : 0);
        } else if (dir == Direction.SOUTH_EAST) {
            return (neighbours.get(1).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(2).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(3).getState() == State.FIRST ? 1 : 0);
        } else if (dir == Direction.SOUTH_WEST) {
            return (neighbours.get(2).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(3).getState() == State.FIRST ? 1 : 0)
                    + (neighbours.get(4).getState() == State.FIRST ? 1 : 0);
        }
        return 0;
    }

    /**
     * returns how much of the neighbours are in the first state
     * 
     * @param neighbours the list of the tiles
     * @return a number
     */
    private int freeNeighbours(List<Tile> neighbours) {
        int count = 0;
        for (Tile t : neighbours) {
            if (t.getState() == State.FIRST) {
                count++;
            }
        }
        return count;
    }

    /**
     * returns a random tile from the neighbourlist based on the values
     * the values represent the number of tiles in the list which are not null
     * 
     * @param neighbours
     * @param values     come from the indexes of the not null tiles
     * @return a tile
     */
    private Tile getRandomNeighbour(List<Tile> neighbours, int... values) {
        Tile tmp = neighbours.get(values[Game.generateRandom(0, values.length - 1)]);
        while (tmp.getState() != State.FIRST) {
            tmp = neighbours.get(values[Game.generateRandom(0, values.length - 1)]);
        }
        return tmp;
    }

    /**
     * 
     * @param nextPos    the next position of the penguin
     * @param neighbours tiles around the penguin
     * @return the next direction of the penguin
     */
    private Direction getNewDirection(Tile nextPos, List<Tile> neighbours) {
        if (neighbours.indexOf(nextPos) == 0) {
            return Direction.NORTH_EAST;
        } else if (neighbours.indexOf(nextPos) == 1) {
            return Direction.NORTH_EAST;
        } else if (neighbours.indexOf(nextPos) == 2) {
            return Direction.SOUTH_EAST;
        } else if (neighbours.indexOf(nextPos) == 3) {
            return Direction.SOUTH_WEST;
        } else if (neighbours.indexOf(nextPos) == 4) {
            return Direction.WEST;
        } else {
            return Direction.NORTH_WEST;
        }
    }

    /**
     * for the file writer
     */
    @Override
    public String toString() {
        return "RANDOM";
    }
}
