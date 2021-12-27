package manager;

import java.util.ArrayList;
import java.util.Random;

import component.Timer;
import datatype.Vector2;
import gameobject.AnglerFish;
import gameobject.Lightning;
import gameobject.Pearl;
import gameobject.Player;
import gameobject.PowerUp;
import gameobject.SmallFish;
import gameobject.Star;
import main.GameStage;
import runnableobject.RunnableObject;

/**
 * Manage game statistics and events.
 * 
 * @author Dave Jimenez, Von Divino
 */
public class GameManager {
	
	/////////////////// PROPERTIES ///////////////////
	
	private static GameManager instance;
	
	private ArrayList<RunnableObject> runnableobject_list = new ArrayList<RunnableObject>();
	private ArrayList<RunnableObject> runnableobject_toadd = new ArrayList<RunnableObject>();
	
	private Player player;
	private AnglerFish boss;
	private Timer spawner;
	private Timer boss_spawner;
	private Timer powerup_spawner;
	
	private GameManager() {}
	
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		return instance;
	}
	
	/**
	 * Spawn a player.
	 * 
	 * @author Von Divino
	 */
	
	public void spawnPlayer() {
		player = new Player(0, -GameStage.WINDOW_HEIGHT/4);
		runnableobject_list.add(player);
	}
	
	/**
	 * Spawn initial enemies.
	 * 
	 * @author Von Divino
	 */
	
	public void spawnInitialEnemies() {
		for(int i = 0; i < 7; ++i) {
			addRunnableObject(new SmallFish(randomizeVectorPosition()));
		}
	}
	
	/**
	 * Spawn interval enemies.
	 * 
	 * @author Von Divino
	 */
	
	public void initIntervalEnemies() {
		spawner = new Timer(5);
		spawner.setLoop(true);
		spawner.setOnTimerTimeout(()->{
			for(int i = 0; i < 3; ++i) {
				addRunnableObject(new SmallFish(randomizeVectorPosition()));	
			}
		});
		spawner.start();
		TimeManager.getInstance().addTimer(spawner);
	}
	
	/**
	 * Initialize boss.
	 * 
	 * @author Von Divino
	 */
	
	public void initBoss() {
		boss_spawner = new Timer(30);
		boss_spawner.setLoop(false);
		boss_spawner.setOnTimerTimeout(()->{
			AnglerFish _boss = new AnglerFish(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT/2);
			runnableobject_list.add(_boss);
			boss = _boss;
		});
		boss_spawner.start();
		TimeManager.getInstance().addTimer(boss_spawner);
	}
	
	/**
	 * Initialize powerups.
	 * 
	 * @author Von Divino, Dave Jimenez
	 */
	
	public void initPowerups() {
		powerup_spawner = new Timer(10);
		powerup_spawner.setLoop(true);
		powerup_spawner.setOnTimerTimeout(()->{
			ArrayList<RunnableObject> toremove_list = new ArrayList<RunnableObject>();
			for(RunnableObject object: runnableobject_list) {
				if(object instanceof PowerUp) {
					toremove_list.add(object);
				}
			}
			for (RunnableObject object: toremove_list) {
				runnableobject_list.remove(object);
			}
			Random r = new Random();
			Pearl _pearl = new Pearl(
					(double) r.nextInt((int)GameStage.WINDOW_WIDTH), 
					(double) r.nextInt((int)GameStage.WINDOW_HEIGHT));
			Lightning _lightning = new Lightning
					((double) r.nextInt((int)GameStage.WINDOW_WIDTH), 
					(double) r.nextInt((int)GameStage.WINDOW_HEIGHT));
			Star _star = new Star 
					((double) r.nextInt((int)GameStage.WINDOW_WIDTH),
					(double) r.nextInt((int)GameStage.WINDOW_HEIGHT));
			addRunnableObject(_pearl);
			addRunnableObject(_lightning);
			addRunnableObject(_star);
		});
		powerup_spawner.start();
		TimeManager.getInstance().addTimer(powerup_spawner);
	}

	/**
	 * Randomize vector position.
	 * 
	 * @author Von Divino
	 */
	
	private Vector2 randomizeVectorPosition() {
		Random r = new Random();
		int x = r.nextInt((int) GameStage.WINDOW_WIDTH);
		int y = r.nextInt((int) GameStage.WINDOW_HEIGHT);
		return new Vector2(x, y);
	}
	
	/////////////////// GETTERS & SETTERS ///////////////////
	
	public Player getPlayer() {return player;}
	public AnglerFish getBoss() {return boss;}
	public ArrayList<RunnableObject> getRunnableObjects(){return runnableobject_list;}
	public void addRunnableObject(RunnableObject object) {runnableobject_toadd.add(object);}
	public void addBufferedRunnableObjectsToAdd() {
		ArrayList<RunnableObject> toremove_list = new ArrayList<RunnableObject>();
		if (!runnableobject_toadd.isEmpty()) {
			for (RunnableObject object: runnableobject_toadd) {
				runnableobject_list.add(object);
				toremove_list.add(object);
			}
			for (RunnableObject object: toremove_list) {
				runnableobject_toadd.remove(object);
			}
		}
	}
}
