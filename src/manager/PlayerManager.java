package manager;

/**
 * Handles managing player status
 * @author Dave
 *
 */
public class PlayerManager {
	private static PlayerManager instance;
	
	private PlayerManager() {}
	
	public static PlayerManager getInstance() {
		if (instance == null) {
			instance = new PlayerManager();
		}
		
		return instance;
	}
}
