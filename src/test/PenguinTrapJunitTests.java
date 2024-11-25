package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.hamcrest.CoreMatchers.allOf;
import main.*;

public class PenguinTrapJunitTests {
    Game game;

    @Before
    public void setup() throws IOException {
        game = new Game();
    }
    @Test
    public void testIsNotCenter(){
        boolean center=game.isCenter(0, 0);
        assertFalse(center);
    }

    @Test
    public void testIsCenter(){
        boolean center=game.isCenter((Game.boardSize-1)/2, (Game.boardSize-1)/2);
        assertTrue(center);
    }
    @Test
    public void testGenerateRandom(){
        int r=Game.generateRandom(1, 10);
        int high = 10;
        int low = 1;
        assertTrue("Error, random is too high", high >= r);
        assertTrue("Error, random is too low",  low  <= r);
    }
    @Test
    public void testNeighboursOfLeftCorner(){
       List<Tile> expected = Arrays.asList(null, game.tiles.get(0).get(1), game.tiles.get(1).get(0), null, null, null);
        List<Tile> result =game.getNeighbours(0, 0);
        assertEquals(expected, result);

    }
    @Test 
    public void testNeighboursOfAnEvenTile(){
        List<Tile> expected = Arrays.asList(game.tiles.get(1).get(2),game.tiles.get(2).get(3),
        game.tiles.get(3).get(2),game.tiles.get(3).get(1),game.tiles.get(2).get(1),game.tiles.get(1).get(1));
        List<Tile> result =game.getNeighbours(2, 2);
        assertEquals(expected, result);
    }

    @Test 
    public void testNeighboursOfAnOddTile(){
        List<Tile> expected = Arrays.asList(game.tiles.get(2).get(3),game.tiles.get(3).get(3),
        game.tiles.get(4).get(3),game.tiles.get(4).get(2),game.tiles.get(3).get(1),game.tiles.get(2).get(2));
        List<Tile> result =game.getNeighbours(3, 2);
        assertEquals(expected, result);
    }


}
