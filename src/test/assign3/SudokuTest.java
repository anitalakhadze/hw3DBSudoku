package assign3;

import org.junit.Test;
import org.junit.Before;

import static junit.framework.TestCase.*;

public class SudokuTest {
    Sudoku easy, medium, hard;

    @Before
    public void setUp(){
        easy = new Sudoku(Sudoku.easyGrid);
        medium = new Sudoku(Sudoku.mediumGrid);
        hard = new Sudoku(Sudoku.hardGrid);
    }

    @Test
    public void testThatSpotSavesAndGetsValues() {
        for (int i = 0; i < 9; i++) {
            Sudoku.Spot s = easy.new Spot(0, i);
            s.set(i);
            assertEquals(i, s.get());
        }
    }

    @Test
    public void testThatSpotGetsRowsAndCols() {
        for (int i = 0; i < easy.grid.length; i++) {
            for (int j = 0; j < easy.grid[0].length; j++) {
                Sudoku.Spot s = easy.new Spot(i, j);
                assertEquals(i, s.getRow());
                assertEquals(j, s.getCol());
            }
        }
    }

    @Test
    public void testThatSpotFindsConflicts() {
        Sudoku.Spot s = easy.new Spot(0, 3);
        assertTrue(s.noConflicts(7));
        assertFalse(s.noConflicts(6));
        assertFalse(s.noConflicts(8));
    }
}