package manager;

import java.util.ArrayList;
import java.util.Random;

import gameobject.AnglerFish;
import javafx.scene.control.Label;
import parentclass.GameObject;

import runnableobject.RunnableObject;

/**
 * Singleton Class.
 * Handles managing game statistics.
 * @author Dave
 */
public class GameManager {
	private static GameManager instance;
	
	private Random r = new Random();

	private int timeLeft = 60;
	private int timeElapsed;
	private int spawnInterval = 5;
	private int nextSpawn = timeLeft - spawnInterval;
	private boolean doSpawn = false;
	
	private GameManager() {
		this.timeLeft = 60;
	}
	
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		
		return instance;
	}
	
	/**
	 * 
	 * This should be called in the GameScene (a required method).
	 * 
	 * @param timeElapsed
	 */
	public void updateTimer(int timeElapsed) {
		
		if (this.timeElapsed != timeElapsed) {
			--this.timeLeft;
			this.timeElapsed = timeElapsed;
			if(this.timeLeft == this.nextSpawn) {
				this.nextSpawn -= this.spawnInterval;
				this.doSpawn = true;
			} else {
				this.doSpawn = false;
			}
		}
	}
	
	public int getTimeLeft() {
		return this.timeLeft;
	}
	
	public void resetTimeLeft() {
		this.timeLeft = 60;
		this.timeElapsed = 0;
		nextSpawn = timeLeft - spawnInterval;
	}
	
	public void spawnEnemy(ArrayList<RunnableObject> runnableObjectList) {
		Random r = new Random();
		for(int i = 0; i < 3; ++i) {			
			int x = r.nextInt(800) + 200;
			int y = r.nextInt(800) + 200;
			runnableObjectList.add(new AnglerFish(x, y));
		}
		this.doSpawn = false;
	}
	
	public boolean doSpawn() {
		return this.doSpawn;
	}
}
