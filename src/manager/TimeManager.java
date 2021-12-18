package manager;

import java.util.ArrayList;

import component.Timer;

/**
 * Manages time.
 * 
 * @author Dave Jimenez
 */

public class TimeManager {
	
	/////////////////// PROPERTIES ///////////////////
	
	private static TimeManager instance;
	
	private double delta_time = 0;
	private double last_time = System.nanoTime();
	private double time_elapsed = 0;
	private double time_left = 61;
	
	private ArrayList<Timer> timer_list = new ArrayList<Timer>();
	private ArrayList<Timer> timer_toadd = new ArrayList<Timer>();		
	
	private TimeManager() {}
	
	public static TimeManager getInstance() {
		if (instance == null) {
			instance = new TimeManager();
		}
		return instance;
	}
	
	/**
	 * Set the delta time from the game loop.
	 * 
	 * @param current_time The current time.
	 * @author Dave Jimenez
	 */
	
	private void setDeltaTime(double current_time) {
		delta_time = ((current_time - last_time)/1000000)/1000;
		last_time = current_time;
	}
	
	/**
	 * Update the time.
	 * 
	 * @param current_time The current time.
	 * @author Dave Jimenez
	 */
	
	public void updateTime(double current_time) {
		setDeltaTime(current_time);
		
		time_elapsed += delta_time;
		
		ArrayList<Timer> timer_toremove = new ArrayList<Timer>();
		for (Timer timer: timer_list) {
			timer.update();
			if (timer.canTerminate()) {
				timer_toremove.add(timer);
			}
		}
		
		if (!timer_toremove.isEmpty()) {
			for (Timer timer: timer_toremove) {
				timer_list.remove(timer);
			}
		}

		timer_toremove = new ArrayList<Timer>();
		if (!timer_toadd.isEmpty()) {
			for (Timer timer: timer_toadd) {
				timer_list.add(timer);
				timer_toremove.add(timer);
			}
		}
		
		if (!timer_toremove.isEmpty()) {
			for (Timer timer: timer_toremove) {
				timer_toadd.remove(timer);
			}
		}
	}
	
	/////////////////// GETTERS & SETTERS ///////////////////
	
	public void addTimer(Timer timer) {timer_toadd.add(timer);}
	public void clearTimerList() {timer_list.removeAll(timer_list);}
	public double getTimeElapsed() {return time_elapsed;}
	public double getTimeLeft() {return time_left - time_elapsed;}
	public double getDeltaTime() {return delta_time;}
	public void reset() {time_elapsed = 0;}
}
