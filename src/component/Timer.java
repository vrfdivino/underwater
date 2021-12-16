package component;

import manager.TimeManager;

/**
 * A timer object.
 * 
 * @author Dave Jimenez
 */

public class Timer {
	
	/////////////////// PROPERTIES ///////////////////
	
	TimeManager TIME_MANAGER = TimeManager.getInstance();
	private double duration;
	private double current_time;
	private TimeoutEvent timeout_event;
	private boolean can_start = false;
	private boolean can_loop = false;
	private boolean finished = false;

	/**
	 * Creates a new Timer instance.
	 * 
	 * @param Dave Jimenez
	 */
	public Timer(double duration) {
		this.duration = duration;
	}
	
	/**
	 * Update the time during the game loop.
	 * 
	 * @author Dave Jimenez
	 */
	
	public void update() {
		if (can_start) current_time += TIME_MANAGER.getDeltaTime();
		if (current_time >= duration) { 
			timeout_event.onTimerTimeoutMethod();
			if (!can_loop) finished = true;
			current_time = 0;
		}
	}
	
	/**
	 * Starts the timer.
	 * 
	 * @author Dave Jimenez
	 */
	public void start() {
		can_start = true;
		current_time = 0;
	}
	
	/**
	 * The callback handler everytime the timer updates.
	 * 
	 * @param e The callback function.
	 * @author Dave Jimenez
	 */
	public void onTimerTimeout(TimeoutEvent e) {
		timeout_event = e;
	}
	
	public interface TimeoutEvent {
		public void onTimerTimeoutMethod();
	}
	
	/////////////////// GETTERS ///////////////////
	
	public boolean isFinished() {return finished;}
	
	/////////////////// SETTERS ///////////////////
	
	public void setLoop(boolean can_loop) {this.can_loop = can_loop;}
}
