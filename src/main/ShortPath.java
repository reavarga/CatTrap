package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShortPath implements Algorithm {
    @Override
    /**
     * This penguin algorithm is based on BFS
     * It has two Matrixes, one tells if the tile has been visited
     * The other contains the parent tiles
     * First it sets the parent of the penguin's tile to null and visited to true
     * the game is lost if the penguin gets to te sides
     * the game is won if the penguin is trapped, does not matter how big the trap is
     */
    public void step(Game game, Penguin penguin) {
        Queue<Tile> queue = new LinkedList<>();

        List<List<Tile>> tiles = game.getTiles();
        boolean[][] visited = new boolean[Game.boardSize][Game.boardSize];
        Tile[][] parentMap = new Tile[Game.boardSize][Game.boardSize];

        int x = penguin.getPos().getXindex();
        int y = penguin.getPos().getYindex();

        queue.offer(penguin.getPos());
        parentMap[x][y] = null;
        visited[x][y] = true;

        Tile currentTile = null;
        boolean lost = false;
        while (!queue.isEmpty()) {
            currentTile = queue.poll();
            x = currentTile.getXindex();
            y = currentTile.getYindex();
            List<Tile> neighbours = game.getNeighbours(x, y);
            if (neighbours.contains(null)) {
                lost = true;
                break;
            }
            for (int i = 0; i < neighbours.size(); i++) {
                Tile curTile = neighbours.get(i);
                int cX = curTile.getXindex();
                int cY = curTile.getYindex();
                if (curTile.getState() == State.FIRST && !visited[cX][cY]) {
                    queue.offer(curTile);
                    parentMap[cX][cY] = currentTile;
                    visited[cX][cY] = true;
                }
            }
        }
        if (penguin.getPos() == currentTile && lost) {
            game.setGameState(GameState.LOST);
        } else if (lost) {
            int i = currentTile.getXindex();
            int j = currentTile.getYindex();
            while (parentMap[i][j] != penguin.getPos()) {
                int iTMP = i;
                int jTMP = j;

                i = parentMap[iTMP][jTMP].getXindex();
                j = parentMap[iTMP][jTMP].getYindex();
            }
            Tile newTile = game.getTileByCoordinates(i, j);
            penguin.setPos(newTile);
        } else {
            game.setGameState(GameState.WON);
        }

    }

    @Override
    public String toString() {
        return "SHORT";
    }
}
