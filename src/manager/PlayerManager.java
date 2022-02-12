package manager;

import java.util.Random;

/**
 * Handles managing player states.
 * 
 * @author Von Divino, Dave Jimenez
 */
public class PlayerManager {

	/////////////////// PROPERTIES ///////////////////
	
	private static PlayerManager instance;
	
	private int hp;
	private int score;
	private boolean is_won;
	
	private PlayerManager() {}
	
	public static PlayerManager getInstance() {
		if (instance == null) {
			instance = new PlayerManager();
		}
		
		return instance;
	}
	
	/**
	 * Initialize player stats.
	 * 
	 * @author Von Divino
	 */
	public void initializeStats() {
		Random r = new Random();
		int x = r.nextInt(151) + 100;
		hp = x > 150 ? 150 : x;
		score = 0;
		is_won = false;
	}
	
	/////////////////// GETTERS & SETTERS ///////////////////
	
	public int getHp() {return hp;}
	public int getScore() {return score;}
	public boolean getIsWon() {return is_won;}
	
	public void setHp(int hp) {this.hp = hp;}
	public void setScore(int score) {this.score = score;};
	public void setIsWon(boolean is_won) {this.is_won = is_won;};
}
