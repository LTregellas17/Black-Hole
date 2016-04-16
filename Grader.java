package game;

import java.util.HashMap;

import players.*;

public class Grader {

	public static void main(String[] args) {
		Player p = null; //YOUR BOT HERE
		System.out.println("GRADE: " + (100 * grade(p, 1000)) + "%");
	}

	/**
	 * Determines your grade on the assignment by playing a bunch of games vs.
	 * the random bot.
	 * 
	 * @param p
	 *            The bot to be graded
	 * @param nTrials
	 *            The number of games to be played total. Make sure this is
	 *            large enough or else you won't get precise enough results.
	 * @return The percentage of games won vs. rando.
	 */
	public static double grade(Player p, int nTrials) {
		double wins = 0;
		Player rando = new RandomPlayer("Rando");
		for (int i = 0; i < nTrials; i++) {
			BlackHoleGame g = new BlackHoleGame(rando, p, 60);
			HashMap<Player, Integer> score = g.play();
			wins += score.get(p) > score.get(rando) ? 1 : 0;
		}
		return wins / nTrials;
	}

}
