package game;

import java.util.ArrayList;
import java.util.HashMap;
import players.*;

/**
 * The main game class. To run a game, create a game object using the
 * constructor and call play. (See the provided main method)
 * 
 * @author Eliot J. Kaplan
 *
 */
public class BlackHoleGame {

	public static void main(String[] args) {
		BlackHoleGame g = new BlackHoleGame(new RandomPlayer("Rando Calrissian"), new RandomPlayer(
				"Marlon Rando"), 60);
		g.play(true);
	}

	private String[][] board;
	private Player[] players;
	private HashMap<Player, Double> time;

	/**
	 * Initializes a new game of Black Hole. Note that although the players are
	 * called p0 and p1, who plays red and who plays blue is randomized. (Red
	 * goes first.)
	 * 
	 * @param p0
	 * @param p1
	 * @param timeLimit
	 *            The amount of time that each player gets to play all of their
	 *            moves in seconds.
	 */
	public BlackHoleGame(Player p0, Player p1, double timeLimit) {
		board = new String[6][];
		for (int i = 0; i < 6; i++) {
			board[i] = new String[i + 1];
			for (int j = 0; j < i + 1; j++) {
				board[i][j] = "X0";
			}
		}
		if (Math.random() < .5) players = new Player[] { p0, p1 };
		else players = new Player[] { p1, p0 };
		time = new HashMap<Player, Double>();
		for (Player p : players) {
			time.put(p, timeLimit);
			p.game = this;
		}
	}

	public String toString() {
		String out = "";
		for (int row = 0; row < board.length; row++) {
			for (int sp = 0; sp < 6 - row - 1; sp++)
				out += "  ";
			for (int i = 0; i < board[row].length; i++) {
				String txt = board[row][i];
				while (txt.length() < 4)
					txt += " ";
				out += txt;
			}
			out += "\n";
		}
		out += "RED: " + players[0] + ", " + time.get(players[0]) + " seconds left.\n";
		out += "BLU: " + players[1] + ", " + time.get(players[1]) + " seconds left.\n";
		return out;
	}

	/**
	 * Plays out a full game of Black Hole. This version of the function
	 * defaults to silent play.
	 * 
	 * @return A hashmap with Player objects as keys and Integer scores as
	 *         values. (You can tell who won by checking who has the higher
	 *         score.
	 */
	public HashMap<Player, Integer> play() {
		return play(false);
	}

	/**
	 * Plays out a full game of Black Hole.
	 * 
	 * @param verbose
	 *            Whether or not you'd like the game to print out everything
	 *            that happens.
	 * @return A hashmap with Player objects as keys and Integer scores as
	 *         values. (You can tell who won by checking who has the higher
	 *         score.
	 */
	public HashMap<Player, Integer> play(boolean verbose) {
		HashMap<Player, Integer> score = new HashMap<Player, Integer>();
		if (verbose) System.out.println(this);
		for (Player p : players)
			score.put(p, 0);
		for (int i = 1; i <= 10; i++) {
			for (int p = 0; p < players.length; p++) {
				int[] move = null;
				double t = System.currentTimeMillis();
				try {
					move = players[p].go(i);
				} catch (Exception e) {
					if (verbose) e.printStackTrace();
					for (Player dood : players)
						score.put(dood, dood == players[p] ? 0 : Integer.MAX_VALUE);
					return score;
				}
				t = System.currentTimeMillis() - t;
				t /= 1000.0;
				time.put(players[p], time.get(players[p]) - t);
				if (!contains(getEmpty(), move) || time.get(players[p]) < 0) {
					if (verbose)
						System.out.println(players[p] + " attempted an illegal move or timed out.");
					for (Player dood : players)
						score.put(dood, dood == players[p] ? 0 : Integer.MAX_VALUE);
					return score;
				}
				board[move[0]][move[1]] = "" + "RB".charAt(p) + i;
				if (verbose) System.out.println(this);
			}
		}
		int[] hole = getEmpty()[0];
		for (int[] coord : getAdjacent(hole)) {
			board[coord[0]][coord[1]] = "X0";
		}
		for (String[] row : board) {
			for (String data : row) {
				if (data.charAt(0) == 'X') continue;
				Player p = players["RB".indexOf(data.charAt(0))];
				int val = Integer.parseInt(data.substring(1));
				score.put(p, score.get(p) + val);
			}
		}
		if (verbose) {
			System.out.println(this);
			for (Player p : players) {
				System.out.println(p + ": " + score.get(p));
			}
		}
		return score;
	}

	boolean contains(int[][] points, int[] point) {
		for (int[] curr : points) {
			boolean flag = true;
			if (curr.length != point.length) continue;
			for (int i = 0; i < curr.length; i++) {
				if (curr[i] != point[i]) {
					flag = false;
					break;
				}
			}
			if (flag) return true;
		}
		return false;
	}

	int[][] getAdjacent(int[] coord) {
		return getAdjacent(coord[0], coord[1]);
	}

	int[][] getAdjacent(int row, int col) {
		int[][] coords = new int[][] { { row, col - 1 }, { row, col + 1 }, { row - 1, col - 1 },
				{ row - 1, col }, { row + 1, col }, { row + 1, col + 1 } };
		ArrayList<int[]> bad = new ArrayList<int[]>();
		for (int[] coord : coords) {
			if (coord[0] < 0 || coord[1] < 0 || coord[0] >= board.length
					|| coord[1] >= board[coord[0]].length) {
				bad.add(coord);
			}
		}
		int[][] out = new int[coords.length - bad.size()][];
		int i = 0;
		for (int[] coord : coords) {
			if (!bad.contains(coord)) {
				out[i] = coord;
				i++;
			}
		}
		return out;
	}

	int[][] getEmpty() {
		ArrayList<int[]> empty = new ArrayList<int[]>();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				if (board[row][col].equals("X0")) empty.add(new int[] { row, col });
			}
		}
		int[][] out = new int[empty.size()][];
		for (int i = 0; i < empty.size(); i++) {
			out[i] = empty.get(i);
		}
		return out;
	}

	String[][] getBoardCopy() {
		String[][] copy = new String[6][];
		for (int i = 0; i < 6; i++) {
			copy[i] = new String[i + 1];
			for (int j = 0; j < i + 1; j++) {
				copy[i][j] = board[i][j];
			}
		}
		return copy;
	}

	double getTimeRemaining(Player p) {
		return time.get(p);
	}

	char getColor(Player p) {
		for (int i = 0; i < players.length; i++) {
			if (players[i] == p) return "RB".charAt(i);
		}
		return 0;
	}
}
