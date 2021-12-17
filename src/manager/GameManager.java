package manager;

import java.util.ArrayList;
import java.util.Random;

import component.Timer;
import datatype.Vector2;
import gameobject.AnglerFish;
import gameobject.Lightning;
import gameobject.Pearl;
import gameobject.Player;
import gameobject.Projectile;
import gameobject.SmallFish;
import javafx.scene.control.Label;
import main.GameStage;
import parentclass.GameObject;
import parentclass.PowerUp;
import runnableobject.RunnableObject;

/**
 * Singleton Class.
 * Handles managing game statistics.
 * @author Dave
 */
public class GameManager {
	
	private static GameManager instance;
	
	private Player player;
	private AnglerFish boss;
	private boolean can_shoot;
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
	
	public void spawnPlayer(ArrayList<RunnableObject> list) {
		player = new Player(0, -GameStage.WINDOW_HEIGHT/4);
		list.add(player);
		initWeapon(list);
		can_shoot = true;
	}
	
	private void initWeapon(ArrayList<RunnableObject> list) {
		Projectile _projectile = new Projectile(player);
		list.add(_projectile);
	}
	
	public void spawnInitialEnemies(ArrayList<RunnableObject> list) {
		for(int i = 0; i < 7; ++i) {
			list.add(new SmallFish(randomizeVectorPosition()));
		}
	}
	
	public void initIntervalEnemies(ArrayList<RunnableObject> list) {
		spawner = new Timer(5);
		spawner.setLoop(true);
		spawner.onTimerTimeout(()->{
			for(int i = 0; i < 3; ++i) {
				list.add(new SmallFish(randomizeVectorPosition()));	
			}
			randomizeEnemiesMovement(list);
		});
		spawner.start();
		TimeManager.getInstance().addTimer(spawner);
	}
	
	public void initBoss(ArrayList<RunnableObject> list) {
		boss_spawner = new Timer(30);
		boss_spawner.setLoop(false);
		boss_spawner.onTimerTimeout(()->{
			AnglerFish _boss = new AnglerFish(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT/2);
			list.add(_boss);
			boss = _boss;
		});
		boss_spawner.start();
		TimeManager.getInstance().addTimer(boss_spawner);
	}
	
	public void initPowerups(ArrayList<RunnableObject> list) {
		powerup_spawner = new Timer(10);
		powerup_spawner.setLoop(true);
		powerup_spawner.onTimerTimeout(()->{
			for(RunnableObject object: list) {
				if(object instanceof PowerUp) {
					list.remove(object);
				}
			}
			Random r = new Random();
			Pearl _pearl = new Pearl(
					(double) r.nextInt((int)GameStage.WINDOW_WIDTH), 
					(double) r.nextInt((int)GameStage.WINDOW_HEIGHT));
			Lightning _lightning = new Lightning
					((double) r.nextInt((int)GameStage.WINDOW_WIDTH), 
					(double) r.nextInt((int)GameStage.WINDOW_HEIGHT));
			list.add(_pearl);
			list.add(_lightning);
		});
		powerup_spawner.start();
		TimeManager.getInstance().addTimer(powerup_spawner);
	}
	
	
	public void playerReload(ArrayList<RunnableObject> list) {
		initWeapon(list);
	}
	

	private Vector2 randomizeVectorPosition() {
		Random r = new Random();
		int x = r.nextInt((int) GameStage.WINDOW_WIDTH);
		int y = r.nextInt((int) GameStage.WINDOW_HEIGHT);
		return new Vector2(x, y);
	}
	
	public void randomizeEnemiesMovement(ArrayList<RunnableObject> list) {
		Random r = new Random();
		for(RunnableObject object: list) {
			if(object instanceof SmallFish) {
				// TODO
//				SmallFish _small_fish = (SmallFish) object;
//				double speed = r.nextInt(100) + 1000;
//				_small_fish.getPosition().add(new Vector2(speed * TimeManager.getInstance().getDeltaTime(),0));
			}
		}
	}
	
	public Player getPlayer() {
		return player;
	}
	public AnglerFish getBoss() {
		return boss;
	}
	
	public boolean getCanShoot() {
		return can_shoot;
	}
	
	public void setCanShoot(boolean can_shoot) {
		this.can_shoot = can_shoot;
		Timer delay = new Timer(0.1d);
		delay.setLoop(false);
		delay.onTimerTimeout(()->{
			this.can_shoot = true;
		});
		delay.start();
		TimeManager.getInstance().addTimer(delay);
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
