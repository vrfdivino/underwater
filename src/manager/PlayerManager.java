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
	private int fishKilled;
	private boolean isWon;
	
	private PlayerManager() {
		this.reset();
	}
	
	public static PlayerManager getInstance() {
		if (instance == null) {
			instance = new PlayerManager();
		}
		
		return instance;
	}
	
	public int getHp() {
		return this.hp;
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
		return this.strength;
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
		this.strength += factor;
		System.out.println(this.strength);
	}
	
	public int getFishKilled() {
		return this.fishKilled;
	}
	
	public void setFishKilled(int factor) {
		this.fishKilled += factor;
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
		return this.isWon;
	}
	
	
	/**
	 * 
	 * This should be called in game play screen.
	 * In this project, it should be in Level_001.
	 * 
	 * @param isWon
	 * @author vondivino
	 */
	public void setIsWon(boolean isWon) {
		this.isWon = isWon;
	}
	
	/**
	 * 
	 * Reset the properties of a player.
	 * 
	 * @author vondivino
	 */
	public void reset() {
		this.hp = PlayerManager.STARTING_HP;
		this.strength = PlayerManager.STARTING_STREGTH;
		this.fishKilled = 0;
		this.isWon = false;
	}
}
