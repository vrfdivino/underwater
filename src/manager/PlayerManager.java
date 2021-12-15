package manager;

/**
 * Handles managing player status
 * @author Dave
 *
 */
public class PlayerManager {
	private static PlayerManager instance;
	
	private int hp;
	private int strength;
	private int fishKilled;
	
	private PlayerManager() {
		this.hp = 500;
		this.strength = 200;
		this.fishKilled = 0;
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
	
	public void setHp(int factor) {
		this.hp += factor;
	}
	
	public int getStrength() {
		return this.strength;
	}
	
	public void setStrength(int factor) {
		this.strength += factor;
	}
	
	public int getFishKilled() {
		return this.fishKilled;
	}
	
	public void setFishKilled(int factor) {
		this.fishKilled += factor;
	}
}
