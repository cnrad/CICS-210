package tictactoe;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GameTest {
    @Test
    public void testEmptyBoard() {
        Game g = new Game();
        assertEquals("...\n...\n...\n", g.toString());
    }

    @Test
    public void testOneMove() {
        Game g = new Game();
        assertEquals("X", g.getCurrentPlayer());
        g.move(0);
        assertEquals("O", g.getCurrentPlayer());
    }
}
