package assign3;

import org.junit.Test;
import org.junit.Before;

import static junit.framework.TestCase.*;

public class SudokuTest {
    Sudoku easy, medium, hard, customGrid2;
    int[][] customGrid;
    String customString;

    @Before
    public void setUp(){
        easy = new Sudoku(Sudoku.easyGrid);
        medium = new Sudoku(Sudoku.mediumGrid);
        hard = new Sudoku(Sudoku.hardGrid);
        customGrid = Sudoku.stringsToGrid(
                "1 6 4 0 0 0 0 0 2",
                "2 0 0 4 0 3 9 1 0",
                "0 0 5 0 8 0 4 0 7",
                "0 9 0 0 0 6 5 0 0",
                "5 0 0 1 0 2 0 0 8",
                "0 0 8 9 0 0 0 3 0",
                "8 0 9 0 4 0 2 0 0",
                "0 7 3 5 0 9 0 0 1",
                "4 0 0 0 0 0 6 7 9");
        customString = "0, 3, 0, 2, 8, 7, 0, 5, 0, " +
                "5, 8, 0, 6, 4, 1, 9, 0, 0, " +
                "1, 0, 6, 9, 0, 0, 0, 2, 4," +
                "2, 0, 0, 0, 6, 0, 3, 0, 8," +
                "0, 9, 5, 0, 0, 0, 2, 6, 0," +
                "8, 0, 4, 0, 3, 0, 0, 0, 9," +
                "6, 2, 0, 0, 0, 5, 4, 0, 3," +
                "0, 0, 3, 8, 2, 6, 0, 1, 5," +
                "0, 5, 0, 3, 1, 4, 0, 9, 0";
        customGrid2 = new Sudoku(Sudoku.textToGrid(customString));
    }

    @Test
    public void testThatSpotSavesAndGetsValues() {
        for (int i = 0; i < 9; i++) {
            Sudoku.Spot s = easy.new Spot(0, i);
            s.setValue(i);
            assertEquals(i, s.getValue());
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

    @Test
    public void testThatPuzzleIsSolved(){
        assertEquals(1, easy.solve());
        assertEquals(1, medium.solve());
        assertEquals(6, hard.solve());
        easy.getSolutionText();
    }

    @Test
    public void testThatPuzzleFailed(){
        easy.grid[0][4] = 7;
        assertEquals(0, easy.solve());
    }

    @Test (expected = RuntimeException.class)
    public void testThatInvalidStringToGridThrowsException(){
        String customString2 = "0, 3, 0, 2, 8, 7, 0, 5, 0, " +
                "5, 8, 0, 6, 4, 1, 9, 0, 0, " +
                "1, 0, 6, 9, 0, 0, 0, 2, 4," +
                "2, 0, 0, 0, 6, 0, 3, 0, 8,";
        customGrid2 = new Sudoku(Sudoku.textToGrid(customString2));
    }
}