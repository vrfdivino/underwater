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
import gameobject.Projectile;
import gameobject.SmallFish;
import gameobject.Star;
import javafx.scene.control.Label;
import main.GameStage;
import parentclass.GameObject;
import runnableobject.RunnableObject;

/**
 * Singleton Class.
 * Manage game statistics and events.
 * @author Dave
 */
public class GameManager {
	
	private static GameManager instance;
	
	private ArrayList<RunnableObject> runnableobject_list = new ArrayList<RunnableObject>();
	private ArrayList<RunnableObject> runnableobject_toadd = new ArrayList<RunnableObject>();
	
	private Player player;
	private AnglerFish boss;
	private Timer spawner;
	private Timer boss_spawner;
	private Timer powerup_spawner;
	
	private GameManager() {
	}
	
	public static GameManager getInstance() {
		if (instance == null) {
			instance = new GameManager();
		}
		
		return instance;
	}
	
	public void spawnPlayer() {
		player = new Player(0, -GameStage.WINDOW_HEIGHT/4);
		runnableobject_list.add(player);
	}
	
	public void spawnInitialEnemies() {
		for(int i = 0; i < 7; ++i) {
			addRunnableObject(new SmallFish(randomizeVectorPosition()));
		}
	}
	
	public void initIntervalEnemies() {
		spawner = new Timer(5);
		spawner.setLoop(true);
		spawner.onTimerTimeout(()->{
			for(int i = 0; i < 3; ++i) {
				addRunnableObject(new SmallFish(randomizeVectorPosition()));	
			}
			//randomizeEnemiesMovement(list);
		});
		spawner.start();
		TimeManager.getInstance().addTimer(spawner);
	}
	
	public void initBoss() {
		boss_spawner = new Timer(30);
		boss_spawner.setLoop(false);
		boss_spawner.onTimerTimeout(()->{
			AnglerFish _boss = new AnglerFish(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT/2);
			runnableobject_list.add(_boss);
			boss = _boss;
		});
		boss_spawner.start();
		TimeManager.getInstance().addTimer(boss_spawner);
	}
	
	public void initPowerups() {
		powerup_spawner = new Timer(10);
		powerup_spawner.setLoop(true);
		powerup_spawner.onTimerTimeout(()->{
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

	private Vector2 randomizeVectorPosition() {
		Random r = new Random();
		int x = r.nextInt((int) GameStage.WINDOW_WIDTH);
		int y = r.nextInt((int) GameStage.WINDOW_HEIGHT);
		return new Vector2(x, y);
	}
	
	/*public void randomizeEnemiesMovement(ArrayList<RunnableObject> list) {
		Random r = new Random();
		for(RunnableObject object: list) {
			if(object instanceof SmallFish) {
				// TODO
//				SmallFish _small_fish = (SmallFish) object;
//				double speed = r.nextInt(100) + 1000;
//				_small_fish.getPosition().add(new Vector2(speed * TimeManager.getInstance().getDeltaTime(),0));
			}
		}
	}*/
	
	public Player getPlayer() {
		return player;
	}
	public AnglerFish getBoss() {
		return boss;
	}
	
	public ArrayList<RunnableObject> getRunnableObjects(){
		return runnableobject_list;
	}
	
	public void addRunnableObject(RunnableObject object) {
		runnableobject_toadd.add(object);
	}
	
	public void addBufferedRunnableObjectsToAdd(){
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

	
	/****** FINAL ***/
//	
//	/**
//	 * Spawn starting fishes in the game.
//	 * 
//	 * @param list The list of all runnable objects.
//	 * @author Von Divino 
//	 */
//	public void spawnInitialEnemies(ArrayList<RunnableObject> list) {
//		for(int i = 0; i < 7; ++i) {
//			list.add(new SmallFish(randomizeVectorPosition()));
//		}
//	}
//	
//	/**
//	 * Spawn an enemy in the game.
//	 * 
//	 * @param list The list of all runnable objects.
//	 * @author vondivino
//	 */
//	public void spawnEnemy(ArrayList<RunnableObject> list) {
//		list.add(new SmallFish(randomizeVectorPosition()));
//	}
//	
//	/**
//	 * Randomize a vector position.
//	 * 
//	 * @return Vector2
//	 */
//	private Vector2 randomizeVectorPosition() {
//		Random r = new Random();
//		int x = r.nextInt((int) GameStage.WINDOW_WIDTH);
//		int y = r.nextInt((int) GameStage.WINDOW_HEIGHT);
//		return new Vector2(x, y);
//	}
}
