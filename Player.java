package game;

/**
 * The base class for players in the Black Hole game. Contains all the functions
 * you'll need to get information about the state of the game, along with the
 * abstract go function that you'll be overriding.
 * 
 * @author Eliot J. Kaplan
 *
 */
public abstract class Player {

	private String name;
	BlackHoleGame game;

	/**
	 * Creates the Player object.
	 * 
	 * @param name
	 *            The name of the player (for display purposes only)
	 */
	public Player(String name) {
		this.name = name;
	}

	/**
	 * Determines all legal coordinates that are adjacent to a given space.
	 * 
	 * @param row
	 * @param col
	 * @return An array of arrays of ints where each subarray is the coordinates
	 *         of an adjacent space.
	 */
	public final int[][] getAdjacent(int row, int col) {
		return game.getAdjacent(row, col);
	}

	/**
	 * Determines all legal coordinates that are adjacent to a given space.
	 * 
	 * @param coord
	 *            An int[] representing the coordinates of a space on the board.
	 * @return An array of arrays of ints where each subarray is the coordinates
	 *         of an adjacent space.
	 */
	public final int[][] getAdjacent(int[] coord) {
		return game.getAdjacent(coord);
	}

	/**
	 * Returns a copy of the board array. The 0th subarray represents the top
	 * row and is of length 1. The 1th subarray represents the middle row and is
	 * of length 2, and so on. Each of the Strings is of the form
	 * [color][number]. So, if a space contains a red 7, the String will be
	 * "R7". Empty spaces are always represented as "X0".
	 * 
	 * @return A ragged 2D array representing the board.
	 */
	public final String[][] getBoard() {
		return game.getBoardCopy();
	}

	/**
	 * 
	 * @return Your color, either 'R' or 'B'.
	 */
	public final char getColor() {
		return game.getColor(this);
	}

	/**
	 * Finds all of the empty spaces on the board. These are the legal spaces
	 * that you can potentially play in.
	 * 
	 * @return An array of arrays where each subarray is the coordinates of an
	 *         empty space.
	 */
	public final int[][] getEmpty() {
		return game.getEmpty();
	}

	/**
	 * Tells you how much time you've got remaining for all of the rest of the
	 * moves in the game. While your bot is thinking, this number ticks down,
	 * and if it hits zero, you immediately lose the game. NOTE: This number
	 * only updates after you've turned in a move, if you start the turn with 20
	 * seconds on the clock and then take a year to make your move, it'll still
	 * read 20 seconds until your go() function ends, at which point the clock
	 * will update to "negative a year minus 20 seconds" and you'll auto-forfeit
	 * the match.
	 * 
	 * @return The number of seconds left on your clock.
	 */
	public final double getTimeRemaining() {
		return game.getTimeRemaining(this);
	}

	/**
	 * Determines your next move. Called every time it's your turn. If you time
	 * out, crash, try to play in a space that is full or try to play in a space
	 * that doesn't exist, you'll immediately lose the match.
	 * 
	 * @param number
	 *            The number that you are currently required to play on the
	 *            board.
	 * @return The coordinates of the space that you want to play in, expressed
	 *         as an array of ints.
	 */
	public abstract int[] go(int number);

	public final String toString() {
		return name;
	}

}
