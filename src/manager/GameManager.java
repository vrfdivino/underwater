package manager;

import java.util.Random;

import gameobject.AnglerFish;
import parentclass.GameObject;

/**
 * Singleton Class.
 * Handles managing game statistics.
 * @author Dave
 */
public class GameManager {
	private static GameManager instance;
	
	private Random r = new Random();
	private int hp = r.nextInt(140) + 60;
	
	private GameManager() {}
	
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		
		return instance;
	}
	
	public GameObject spawnEnemy() {
		Random r = new Random();
		int x = r.nextInt(400) + 400;
		int y = r.nextInt(400) + 400;
		return new AnglerFish(x,y);
	}
	
	public int getHp() {
		return this.hp;
	}
	
	public void setHp() {
		this.hp--;
	}
}
