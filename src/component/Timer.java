package component;

import manager.TimeManager;

public class Timer {
	TimeManager TIME_MANAGER = TimeManager.getInstance();
	
	private boolean can_start = false;
	private boolean can_loop = false;
	private double duration;
	private double current_time;
	
	private boolean finished = false;
	private TimeoutEvent timeout_event;
	
	public Timer(double duration) {
		this.duration = duration;
	}
	
	public void update() {
		if (can_start) {
			current_time += TIME_MANAGER.getDeltaTime();
		}
		if (current_time >= duration) { 
			timeout_event.onTimerTimeoutMethod();
			if (!can_loop) {
				finished = true;
			}
			current_time = 0;
		}
	}
	
	public void start() {
		can_start = true;
		current_time = 0;
	}
	
	public void setLoop(boolean can_loop) {
		this.can_loop = can_loop;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void onTimerTimeout(TimeoutEvent e) {
		timeout_event = e;
	}
	
	public interface TimeoutEvent {
		public void onTimerTimeoutMethod();
	}
	

}
