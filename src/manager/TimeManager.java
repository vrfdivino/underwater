package manager;

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
		this.delta_time = ((current_time - last_time)/1000000)/1000;
		this.last_time = current_time;
	}
	
	public void updateTime(double current_time) {
		this.setDeltaTime(current_time);
		
		this.time_elapsed += this.delta_time;
	}
	
	public double getTimeElapsed() {
		return this.time_elapsed;
	}
}
