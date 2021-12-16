package gameobject;

import java.util.ArrayList;
import java.util.Random;

import component.AnimatedSprite;
import component.AnimationPlayer;
import component.Timer;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;
import parentclass.GameObject;

public class SmallFish extends GameObject{
	
	/////////////////// PROPERTIES ///////////////////
	
	public static int DAMAGE = 50;
	
	private Image[] smallfish_move_sprites = new Image[8];
	private AnimatedSprite smallfish_move;
	private int dir_x = -1;
	private int speed = 125;
	private Timer timer;
	
	
	/**
	 * Creates a new small fish object.
	 * 
	 * @param x The starting x position.
	 * @param y The starting y position.
	 * @author Von Divino
	 */
	
	public SmallFish(double x, double y) {
		setTransformations(x, y);
		setSpritesAndAnimations();
		setCollision();
//		initTimer();
	}
	
	/**
	 * Creates a new small fish object.
	 * 
	 * @param position The vector position.
	 * @author Von Divino
	 */
	
	public SmallFish(Vector2 position) {
		setTransformations(position.x, position.y);
		setSpritesAndAnimations();
		setCollision();
//		initTimer();
	}
	
	/**
	 * Set the position and sizing properties of the small fish.
	 *  
	 * @param x The x position.
	 * @param y The y position.
	 * @author Von Divino
	 */
	
	private void setTransformations(double x, double y) {
		position.set(x, y);
		size.set(128, 128);
		rotation = 0;
	}
	
	/**
	 * Set the set of sprite and animation of the small fish.
	 * 
	 * 	@author Von Divino
	 */
	
	private void setSpritesAndAnimations() {
		animation_player = new AnimationPlayer();
		// TODO
		for (int i = 0; i < 8; i++)	smallfish_move_sprites[i] = new Image("/Enemy/Sprites/AnglerFish" + (i + 1) + ".png");
		smallfish_move = new AnimatedSprite(smallfish_move_sprites, 12, position, size);
		animation_player.addAnimation("MOVE", smallfish_move);
		animation_player.setRotation(rotation);
	}
	
	/**
	 * Set the collision object attach to the small fish.
	 * 
	 * 	@author Von Divino
	 */
	
	private void setCollision() {
		collision.setCollide(true);
		// TODO
		collision.setOrigin(new Vector2(-(size.x/2) + 40, -(size.y/2) + 60));
		// TODO
		collision.setSize(new Vector2(140, 140));
		String[] collisions_objs = new String[2];
		collisions_objs[0] = Player.class.getName();
		collisions_objs[1] = Projectile.class.getName();
		collision.setCollisions(collisions_objs);
	}
	
	/**
	 * Adds an independent state manipulator for the small fish.
	 * In this case, the small fish should have varying speed.
	 * 
	 * @author Von Divino
	 */
	public void initTimer() {
		Random r = new Random();
		timer = new Timer(r.nextInt(10));
		timer.onTimerTimeout(()->{
			speed = r.nextInt(125) + 125;
			System.out.println("changing speed");
		 });
		timer.setLoop(true);
		timer.start();
		TIME_MANAGER.addTimer(timer);
	}
	
	/**
	 * Update the state of the small fish.
	 * 
	 * @author Von Divino
	 */
	
	@Override
	public void update(GraphicsContext gc) {
		updatePosition();
		updateCollision();
		render(gc);
	}
	
	/**
	 * Describe how to render a small fish object.
	 * 
	 * @param gc The graphics context from the canvas.
	 * @author Von Divino
	 */
	
	private void render(GraphicsContext gc) {
		animation_player.playAnimation("MOVE");
		animation_player.setPosition(position);
		animation_player.render(gc);
		if (!collision.isColliding()) {
		} else {
			// deduct strength of the player 
			// PLAYER_MANAGER.setStrength(-SmallFish.DAMAGE);
			// PLAYER_MANAGER.setHp(-20);
			//collision.renderCollision(gc, Color.DARKORANGE, 0.5);
			
			// TODO:
			// if it collides with the player then the player should take the damage.
			// if it collides with a bullet then this instance should be destroyed.
			destroyCollidingObjects();
		}
	}
	
	/**
	 * Update the position of the small fish.
	 * 
	 * @author Von Divino
	 */
	
	private void updatePosition() {
		position.add(new Vector2(speed * dir_x * TIME_MANAGER.getDeltaTime(),0));
		if (position.x < 0)	{
			dir_x = 1;
			position.x = 0;
			animation_player.setHFlip(true);
			collision.setOrigin(new Vector2(-(size.x/2) + 70, -(size.y/2) + 60));
		}
		if (position.x > GameStage.WINDOW_WIDTH) {
			dir_x = -1;
			position.x = GameStage.WINDOW_WIDTH;
			animation_player.setHFlip(false);
			collision.setOrigin(new Vector2(-(size.x/2) + 40, -(size.y/2) + 60));
		}
	}
	
	/**
	 * Update the position of the collision object.
	 * 
	 * @author Von Divino
	 */
	
	private void updateCollision() {
		collision.setPosition(position);
	}
	
	/**
	 * Destroy the objects that collides with the weapon.
	 * 
	 * 	@author Von Divino
	 */
	
	private void destroyCollidingObjects() {
		ArrayList<GameObject> toremove_list = new ArrayList<GameObject>();
		for (GameObject other: collision.getOverlaps()) {
			toremove_list.add(other);
//			System.out.println(other);
		}
		for (GameObject other: toremove_list) {
			collision.removeOverlap(other);
			// destroy bullet when hit
			if(other instanceof Projectile) {
				other.destroy();
			}
		}
	}
}
