package manager;

public class SFXManager extends AudioManager{
	private static SFXManager instance;
	
	private SFXManager () {}
	
	public static SFXManager getInstance() {
		if (instance == null) {
			instance = new SFXManager();
		}
		
		return instance;
	}
}
