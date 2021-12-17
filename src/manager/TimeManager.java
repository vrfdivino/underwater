package manager;

import java.util.ArrayList;

import component.Timer;

/**
 * Singleton Class
 * Manages time.
 * @author Dave
 *
 */
public class TimeManager {
	private static TimeManager instance;
	
	private double delta_time = 0;
	private double last_time = System.nanoTime();
	private double time_elapsed = 0;
	private double time_left = 61;
	
	private ArrayList<Timer> timer_list = new ArrayList<Timer>();
	private ArrayList<Timer> timer_toadd = new ArrayList<Timer>();			//Buffer Timers to add to timer_list
	
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
		
		//Update Timers
		ArrayList<Timer> timer_toremove = new ArrayList<Timer>();
		for (Timer timer: timer_list) {
			timer.update();
			if (timer.canTerminate()) {
				timer_toremove.add(timer);
			}
		}
		
		//Remove finished Timers
		if (!timer_toremove.isEmpty()) {
			for (Timer timer: timer_toremove) {
				timer_list.remove(timer);
			}
		}
		
		//Add Buffered timers to timer_list
		timer_toremove = new ArrayList<Timer>();
		if (!timer_toadd.isEmpty()) {
			for (Timer timer: timer_toadd) {
				timer_list.add(timer);
				timer_toremove.add(timer);
			}
		}
		
		//Empty Timer Buffer
		if (!timer_toremove.isEmpty()) {
			for (Timer timer: timer_toremove) {
				timer_toadd.remove(timer);
			}
		}
	}
	
	public void addTimer(Timer timer) {
		timer_toadd.add(timer);
	}
	
	public void clearTimerList() {
		timer_list.removeAll(timer_list);
	}
	
	public double getTimeElapsed() {
		return time_elapsed;
	}
	
	public double getTimeLeft() {
		return time_left - time_elapsed;
	}
	
	public double getDeltaTime() {
		return delta_time;
	}
	
	public void reset() {
		time_elapsed = 0;
	}
}
