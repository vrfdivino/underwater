package manager;

/**
 * Handles managing player status
 * @author Dave
 *
 */
public class PlayerManager {

	public static int STARTING_HP = 100;
	public static int STARTING_STREGTH = 100;
	
	private static PlayerManager instance;
	private int hp;
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
	
	public int getHp() {
		return hp;
	}
	
	/**
	 * 
	 * TODO:
	 * To be called when a Fish or the Boss hits the player.
	 * 
	 * @param factor
	 * @author vondivino
	 */
	public void setHp(int factor) {
		this.hp += factor;
	}
	
	public int getStrength() {
		return strength;
	}
	
	/**
	 * 
	 * TODO:
	 * To be called when the Boss (AnglerFish) hits the player.
	 * The AnglerFish will have an effect of -50.
	 * 
	 * @param factor
	 * @author vondivino
	 */
	public void setStrength(int factor) {
		strength += factor;
		System.out.println(strength);
	}
	
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
		strength = PlayerManager.STARTING_STREGTH;
		fish_killed = 0;
		is_won = false;
	}
}
