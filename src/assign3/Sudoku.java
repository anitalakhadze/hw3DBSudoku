package assign3;

import java.util.ArrayList;
import java.util.List;

/*
 * Encapsulates a Sudoku grid to be solved.
 * CS108 Stanford.
 */
public class Sudoku {
	//grid is the original grid, result is the grid with the solution
	int[][] grid, result;
	// these ivars are needed so we can find out how much time did it take to
	// find the solution.
	long startTime, endTime;

	/**
	 * Spot represents a single spot on the board.
	 */
	public class Spot {
		int row, col, value;

		public Spot(int row, int col){
			this.row = row;
			this.col = col;
			value = grid[row][col];
		}

		/**
		 * Sets the value on the current spot.
		 * @param num value that is to be set on the current spot.
		 */
		public void setValue(int num){ grid[row][col] = num; }

		/**
		 * Gets value from the current spot
		 * @return value on the current spot.
		 */
		public int getValue(){ return grid[row][col]; }

		/**
		 * @return row of the spot
		 */
		public int getRow() { return row; }

		/**
		 * @return col of the spot
		 */
		public int getCol() { return col; }

		/**
		 * Checks if by setting the specified num at the current spot
		 * will cause any conflicts.
		 * @param num value which should be checked on the spot
		 * @return returns true if there are no conflicts in row, col and square,
		 * otherwise - returns false.
		 */
		public boolean noConflicts(int num){
			return !usedInRow(num)
					&& !usedInCol(num)
					&& !usedInSquare(row - row % PART, col - col % PART, num);
		}

		/**
		 * Checks if there are any conflicts in the row with the specified number.
		 * @param num number that needs to be checked against.
		 * @return returns true if none of the values in the row equals the specified
		 * number, otherwise - returns false.
		 */
		private boolean usedInRow (int num){
			for (int col = 0; col < SIZE; col++) {
				if(grid[row][col] == num) return true;
			}
			return false;
		}

		/**
		 * Checks if there are any conflicts in the col with the specified number.
		 * @param num number that needs to be checked against.
		 * @return returns true if none of the values in the col equals the specified
		 * number, otherwise - returns false.
		 */
		private boolean usedInCol (int num){
			for (int row = 0; row < SIZE; row++) {
				if(grid[row][col] == num) return true;
			}
			return false;
		}

		/**
		 * Checks if there are any conflicts in the square with the specified number.
		 * @param boxStartRow
		 * @param boxStartCol
		 * @param num number that needs to be checked against.
		 * @return returns true if none of the values in the square equals
		 * the specified number, otherwise - returns false.
		 */
		private boolean usedInSquare(int boxStartRow, int boxStartCol, int num){
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {
					if(grid[row + boxStartRow][col + boxStartCol] == num){
						return true;
					}
				}
			}
			return false;
		}
	}

	// Provided easy 1 6 grid
	// (can paste this text into the GUI too)
	public static final int[][] easyGrid = Sudoku.stringsToGrid(
	"1 6 4 0 0 0 0 0 2",
	"2 0 0 4 0 3 9 1 0",
	"0 0 5 0 8 0 4 0 7",
	"0 9 0 0 0 6 5 0 0",
	"5 0 0 1 0 2 0 0 8",
	"0 0 8 9 0 0 0 3 0",
	"8 0 9 0 4 0 2 0 0",
	"0 7 3 5 0 9 0 0 1",
	"4 0 0 0 0 0 6 7 9");
	
	
	// Provided medium 5 3 grid
	public static final int[][] mediumGrid = Sudoku.stringsToGrid(
	 "530070000",
	 "600195000",
	 "098000060",
	 "800060003",
	 "400803001",
	 "700020006",
	 "060000280",
	 "000419005",
	 "000080079");
	
	// Provided hard 3 7 grid
	// 1 solution this way, 6 solutions if the 7 is changed to 0
	public static final int[][] hardGrid = Sudoku.stringsToGrid(
	"3 0 0 0 0 0 0 8 0",
	"0 0 1 0 9 3 0 0 0",
	"0 4 0 7 8 0 0 0 3",
	"0 9 3 8 0 0 0 1 2",
	"0 0 0 0 4 0 0 0 0",
	"5 2 0 0 0 6 7 9 0",
	"6 0 0 0 2 1 0 4 0",
	"0 0 0 5 3 0 9 0 0",
	"0 3 0 0 0 0 0 5 1");
	
	
	public static final int SIZE = 9;  // size of the whole 9x9 puzzle
	public static final int PART = 3;  // size of each 3x3 part
	public static final int MAX_SOLUTIONS = 100;
	
	// Provided various static utility methods to
	// convert data formats to int[][] grid.
	
	/**
	 * Returns a 2-d grid parsed from strings, one string per row.
	 * The "..." is a Java 5 feature that essentially
	 * makes "rows" a String[] array.
	 * (provided utility)
	 * @param rows array of row strings
	 * @return grid
	 */
	public static int[][] stringsToGrid(String... rows) {
		int[][] result = new int[rows.length][];
		for (int row = 0; row<rows.length; row++) {
			result[row] = stringToInts(rows[row]);
		}
		return result;
	}
	
	
	/**
	 * Given a single string containing 81 numbers, returns a 9x9 grid.
	 * Skips all the non-numbers in the text.
	 * (provided utility)
	 * @param text string of 81 numbers
	 * @return grid
	 */
	public static int[][] textToGrid(String text) {
		int[] nums = stringToInts(text);
		if (nums.length != SIZE*SIZE) {
			throw new RuntimeException("Needed 81 numbers, but got:" + nums.length);
		}
		
		int[][] result = new int[SIZE][SIZE];
		int count = 0;
		for (int row = 0; row<SIZE; row++) {
			for (int col=0; col<SIZE; col++) {
				result[row][col] = nums[count];
				count++;
			}
		}
		return result;
	}
	
	
	/**
	 * Given a string containing digits, like "1 23 4",
	 * returns an int[] of those digits {1 2 3 4}.
	 * (provided utility)
	 * @param string string containing ints
	 * @return array of ints
	 */
	public static int[] stringToInts(String string) {
		int[] a = new int[string.length()];
		int found = 0;
		for (int i=0; i<string.length(); i++) {
			if (Character.isDigit(string.charAt(i))) {
				a[found] = Integer.parseInt(string.substring(i, i+1));
				found++;
			}
		}
		int[] result = new int[found];
		System.arraycopy(a, 0, result, 0, found);
		return result;
	}


	// Provided -- the deliverable main().
	public static void main(String[] args) {
		Sudoku sudoku;
		sudoku = new Sudoku(hardGrid);
		
		System.out.println(sudoku); // print the raw problem
		int count = sudoku.solve();
		System.out.println("solutions:" + count);
		System.out.println("elapsed:" + sudoku.getElapsed() + "ms");
		System.out.println(sudoku.getSolutionText());
	}

	/**
	 * Sets up based on the given ints.
	 */
	public Sudoku(int[][] ints) {
		startTime = 0;
		endTime = 0;
		grid = new int[SIZE][SIZE];
		result = new int[SIZE][SIZE];
		for (int i = 0; i < ints.length; i++) {
			System.arraycopy(ints[i], 0, grid[i], 0, ints[0].length);
			System.arraycopy(ints[i], 0, result[i], 0, ints[0].length);
		}
	}
	
	/**
	 * Solves the puzzle, invoking the underlying recursive search.
	 */
	public int solve() {
		List<Spot> spots = new ArrayList<>();
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				if(grid[row][col] == 0) {
					spots.add(new Spot(row, col));
				}
			}
		}
		startTime = System.currentTimeMillis();
		int result = solveRec(spots, 0);
		endTime = System.currentTimeMillis();
		return result;
	}

	/**
	 * recursive helper function to find the solution using backtracking.
	 * @param list list of the unassigned spots.
	 * @param idx index of the current spot in the list.
	 * @return number of solutions.
	 */
	private int solveRec(List<Spot> list, int idx) {
		if (idx >= list.size()){
			copyArray(grid, result);
			return 1;
		}
		int result = 0;
		Spot current = list.get(idx);
		for (int num = 1; num <= 9; num++) {
			if(current.noConflicts(num)){
				int initialVal = current.getValue();
				current.setValue(num);
				result += solveRec(list, idx + 1);
				current.setValue(initialVal);
				if (result >= MAX_SOLUTIONS) break;
			}
		}
		return result;
	}

	/**
	 * Utility function to copy 2-d arrays.
	 * @param src source array
	 * @param dst destination array
	 */
	private void copyArray(int[][] src, int[][] dst) {
		for (int i = 0; i < src.length; i++) {
			System.arraycopy(src[i], 0, dst[i], 0, src[i].length);
		}
	}

	/**
	 * @return solution as a string.
	 */
	public String getSolutionText() {
		return gridToText(result);
	}

	/**
	 * Utility function to return solution as a string.
	 * @param input grid which we want to turn into string
	 * @return string representing the grid.
	 */
	private String gridToText(int[][] input){
		StringBuilder result = new StringBuilder();
		for (int row = 0; row < SIZE; row++) {
			for (int col = 0; col < SIZE; col++) {
				result.append(input[row][col]);
				result.append(col == SIZE-1 ? "\n" : " ");
			}
		}
		return result.toString();
	}

	/**
	 * @return the time elapsed while solving the puzzle.
	 */
	public long getElapsed() {
		return endTime - startTime;
	}

	/**
	 * @return the original grid, to which we don't make changes.
	 */
	@Override
	public String toString() {
		return gridToText(grid);
	}
}
