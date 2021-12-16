package manager;

import java.util.ArrayList;
import java.util.Random;

import datatype.Vector2;
import gameobject.AnglerFish;
import gameobject.SmallFish;
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
	
	
	private static GameManager instance;
	private int time_left;
	int time_elapsed = 0;
	private int spawn_interval = 5;
	private int next_spawn = 60 - spawn_interval;
	private boolean spawn = false;
	private boolean spawn_boss = false;
	
	private GameManager() {
		reset();
	}
	
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		
		return instance;
	}

	/**
	 * 
	 * Reset the game manager instance.
	 * 
	 * @author vondivino
	 */
	public void reset() {
		time_left = 60;
		next_spawn = 60 - spawn_interval;
		spawn_boss = false;
	}
	

	
	public boolean getSpawn() {
		return spawn;
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
		spawn_boss = q;
	}
	
	public boolean getSpawnBoss() {
		return spawn_boss;
	}
	
	public int getTimeLeft() {
		return time_left;
	}
	
	public void setTimeLeft(int factor) {
		time_left += factor;
	}
	
	/****** FINAL ***/
	
	/**
	 * Spawn starting fishes in the game.
	 * 
	 * @param list The list of all runnable objects.
	 * @author Von Divino 
	 */
	public void spawnInitialEnemies(ArrayList<RunnableObject> list) {
		for(int i = 0; i < 7; ++i) {
			list.add(new SmallFish(randomizeVectorPosition()));
		}
	}
	
	/**
	 * Spawn an enemy in the game.
	 * 
	 * @param list The list of all runnable objects.
	 * @author vondivino
	 */
	public void spawnEnemy(ArrayList<RunnableObject> list) {
		list.add(new SmallFish(randomizeVectorPosition()));
	}
	
	/**
	 * Randomize a vector position.
	 * 
	 * @return Vector2
	 */
	private Vector2 randomizeVectorPosition() {
		Random r = new Random();
		int x = r.nextInt((int) GameStage.WINDOW_WIDTH);
		int y = r.nextInt((int) GameStage.WINDOW_HEIGHT);
		return new Vector2(x, y);
	}
}
