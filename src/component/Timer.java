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
	private boolean terminate_on_end = true;
	private boolean terminated = false;

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
		if (current_time >= duration && !finished) { 
			timeout_event.onTimerTimeout();
			if (!can_loop && terminate_on_end) terminated = true;
			else if (!can_loop)	finished = true;
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
		finished = false;
	}
	
	/**
	 * The callback handler everytime the timer updates.
	 * 
	 * @param e The callback function.
	 * @author Dave Jimenez
	 */
	
	public void setOnTimerTimeout(TimeoutEvent e) {
		timeout_event = e;
	}
	
	public interface TimeoutEvent {
		public void onTimerTimeout();
	}
	
	/////////////////// GETTERS ///////////////////
	
	public boolean isFinished() {return finished;}
	public boolean isTerminated() {return terminated;}
	
	/////////////////// SETTERS ///////////////////
	
	public void setLoop(boolean can_loop) {this.can_loop = can_loop;}
	public void terminateOnEnd(boolean terminate_on_end) {this.terminate_on_end = terminate_on_end;}
}
