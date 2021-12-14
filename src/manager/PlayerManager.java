package manager;

/**
 * Handles managing player status
 * @author Dave
 *
 */
public class PlayerManager {
	private static PlayerManager instance;
	
	private int strength = 200;
	
	private PlayerManager() {}
	
	public static PlayerManager getInstance() {
		if (instance == null) {
			instance = new PlayerManager();
		}
		
		return instance;
	}
	
	public void setStrength(int factor) {
		this.strength -= factor;
		System.out.println("Current strength of the player: " + this.strength);
	}
}
