package manager;

import java.util.ArrayList;
import java.util.Random;

import gameobject.AnglerFish;
import javafx.scene.control.Label;
import main.GameStage;
import parentclass.GameObject;

import runnableobject.RunnableObject;

/**
 * Singleton Class.
 * Handles managing game statistics.
 * @author Dave
 */
public class GameManager {
	
	public static int STARTING_TIME = 60;
	public static int SPAWN_NUM = 3;
	public static int TIMEOUT_BEFORE_BOSS = 30;
	
	private static GameManager instance;
	private int timeLeft = 60;
	private int timeElapsed;
	private int spawnInterval = 5;
	private int nextSpawn = timeLeft - spawnInterval;
	private boolean spawn = false;
	private boolean spawnBoss = false;
	
	private GameManager() {
		this.reset();
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
				this.spawn = true;
			} else {
				this.spawn = false;
			}
		}
	}
	
	public int getTimeLeft() {
		return this.timeLeft;
	}
	
	/**
	 * 
	 * Reset the game manager instance.
	 * 
	 * @author vondivino
	 */
	public void reset() {
		this.timeLeft = GameManager.STARTING_TIME;
		this.timeElapsed = 0;
		this.nextSpawn = this.timeLeft - this.spawnInterval;
	}
	
	/**
	 * 
	 * TODO:
	 * Spawn enemies enemies in the game.
	 * To be called in Level_001.
	 * 
	 * @param runnableObjectList
	 * @author vondivino
	 */
	public void spawnEnemy(ArrayList<RunnableObject> runnableObjectList) {
		Random r = new Random();
		for(int i = 0; i < GameManager.SPAWN_NUM; ++i) {			
			int x = r.nextInt(800) + 200;
			int y = r.nextInt(800) + 200;
//			runnableObjectList.add(new AnglerFish(x, y));
			System.out.println("spawn enemies");
		}
		this.spawn = false;
	}
	
	public boolean getSpawn() {
		return this.spawn;
	}
	
	/**
	 * 
	 * TODO:
	 * Randomize enemies speed.
	 * 
	 * @author vondivino
	 */
	public void randomizeEnemiesSpeed(ArrayList<RunnableObject> runnableObjectList) {
		
	}
	
	/**
	 * 
	 * Spawn boss.
	 * To be called in the updateObjects in Level_001.
	 * 
	 * @param runnableObjectList
	 */
	public void spawnBoss(ArrayList<RunnableObject> runnableObjectList) {
		runnableObjectList.add(new AnglerFish(GameStage.WINDOW_WIDTH/2-200, GameStage.WINDOW_HEIGHT/2-200));
	}
	
	/**
	 * 
	 * Set if we are to spawn the boss.
	 * 
	 * @author vondivino
	 */
	public void setSpawnBoss(boolean q) {
		this.spawnBoss = q;
	}
	
	public boolean getSpawnBoss() {
		return this.spawnBoss;
	}
}
