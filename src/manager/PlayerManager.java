package manager;

/**
 * Handles managing player states.
 * 
 * @author Von Divino, Dave Jimenez
 */
public class PlayerManager {

	public static int STARTING_HP = 100;
	
	private static PlayerManager instance;
	private int hp = PlayerManager.STARTING_HP;
	private int strength;
	private int fish_killed;
	private boolean is_won;
	
	private PlayerManager() {
		reset();
	}
	
	public static PlayerManager getInstance() {
		if (instance == null) {
			instance = new PlayerManager();
		}
		
		return instance;
	}
	
	public int getHp() {return hp;}
	

	public void setHp(int hp) {this.hp = hp;}
	
	public int getFishKilled() {
		return fish_killed;
	}
	
	public void setFishKilled(int factor) {
		this.fish_killed += factor;
	}
	
	/**
	 * 
	 * Use this getter end the EndScreen.
	 * This is used to decide whether a Win or Lose GUI to render.
	 * 
	 * @return
	 * @author vondivino
	 */
	public boolean getIsWon() {
		return is_won;
	}
	
	
	/**
	 * 
	 * This should be called in game play screen.
	 * In this project, it should be in Level_001.
	 * 
	 * @param is_won
	 * @author vondivino
	 */
	public void setIsWon(boolean is_won) {
		this.is_won = is_won;
	}
	
	/**
	 * 
	 * Reset the properties of a player.
	 * 
	 * @author vondivino
	 */
	public void reset() {
		hp = PlayerManager.STARTING_HP;
		fish_killed = 0;
		is_won = false;
	}
}
