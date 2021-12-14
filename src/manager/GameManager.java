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
	//private int hp = r.nextInt(140) + 60;

	private int timeLeft = 60;
	private int timeElapsed;
	private int spawnInterval = 5;
	private int nextSpawn = timeLeft - spawnInterval;
	private boolean doSpawn = false;
	
	private GameManager() {}
	
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		
		return instance;
	}
	/*
	public int getHp() {
		return this.hp;
	}
	
	public void setHp() {
		this.hp--;
	}
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
			System.out.println(doSpawn);
			System.out.println(this.timeElapsed);
		}
//		
//		if (timeLeft <= 0) {
//			this.timeCount.setText("00:00");
//		}else if (timeLeft >= 10) {
//			this.timeCount.setText("00:" + this.timeLeft);
//		} else {
//			this.timeCount.setText("00:0" + this.timeLeft);
//		}
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
