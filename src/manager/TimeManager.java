package manager;

/**
 * Singleton Class
 * Handles managing time.
 * @author Dave
 *
 */
public class TimeManager {
	private static TimeManager instance;
	
	private double delta_time = 0;
	private double last_time = System.nanoTime();
	private double time_elapsed = 0;
	
	private TimeManager() {}
	
	public static TimeManager getInstance() {
		if (instance == null) {
			instance = new TimeManager();
		}
		
		return instance;
	}
	
	private void setDeltaTime(double current_time) {
		delta_time = ((current_time - last_time)/1000000)/1000;
		last_time = current_time;
	}
	
	public void updateTime(double current_time) {
		setDeltaTime(current_time);
		
		time_elapsed += delta_time;
	}
	
	public double getTimeElapsed() {
		return time_elapsed;
	}
	
	public double getDeltaTime() {
		return delta_time;
	}
}
