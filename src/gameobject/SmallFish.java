package gameobject;

import java.util.ArrayList;
import java.util.Random;

import component.AnimatedSprite;
import component.AnimationPlayer;
import component.AudioPlayer;
import component.Timer;
import constants.Assets;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;
import parentclass.GameObject;

/**
 * The small fish enemies.
 * It inherits the base properties and methods from the GameObject.
 * 
 * @author Von Divino
 */

public class SmallFish extends GameObject{
	
	/////////////////// PROPERTIES ///////////////////
	
	public static int DAMAGE = 30;
	
	private Image[] smallfish_move_sprites = new Image[1];
	private AnimatedSprite smallfish_move;
	private int dir_x = -1;
	private int speed = 125;
	private Timer timer;
	
	/**
	 * Creates a new small fish object.
	 * 
	 * @param x The starting x position.
	 * @param y The starting y position.
	 * @author Von Divino, Dave Jimenez
	 */
	
	public SmallFish(double x, double y) {
		setTransformations(x, y);
		setSpritesAndAnimations();
		setCollision();
		initTimer();
		setAudio();
	}
	
	/**
	 * Creates a new small fish object.
	 * 
	 * @param position The vector position.
	 * @author Von Divino, Dave Jimenez
	 */
	
	public SmallFish(Vector2 position) {
		setTransformations(position.x, position.y);
		setSpritesAndAnimations();
		setCollision();
		initTimer();
		setAudio();
	}
	
	/**
	 * Set the audio property of the small fish.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void setAudio() {
		SFX_MANAGER.addAudioPlayer("FISH HIT", new AudioPlayer(Assets.FISH_HIT));
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
		size.set(144, 96);
		rotation = 0;
	}
	
	/**
	 * Set the set of sprite and animation of the small fish.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	private void setSpritesAndAnimations() {
		animation_player = new AnimationPlayer();
		smallfish_move_sprites[0] = new Image("/Enemy/Sprites/Fish.png");
		smallfish_move = new AnimatedSprite(smallfish_move_sprites, 1, position, size);
		animation_player.addAnimation("MOVE", smallfish_move);
		animation_player.setRotation(rotation);
	}
	
	/**
	 * Set the collision object attach to the small fish.
	 * 
	 * 	@author Von Divino, Dave Jimenez
	 */
	
	private void setCollision() {
		collider.setCanCollide(true);
		collider.setOrigin(new Vector2(-(size.x/2), -(size.y/2)));
		collider.setSize(new Vector2(144, 96));
		String[] collisions_objs = new String[2];
		collisions_objs[0] = Player.class.getName();
		collisions_objs[1] = Spear.class.getName();
		collider.setCollisionMasks(collisions_objs);
	}
	
	/**
	 * Adds an independent state manipulator for the small fish.
	 * In this case, the small fish should have varying speed.
	 * 
	 * @author Von Divino, Dave Jimenez
	 */
	
	public void initTimer() {
		Random r = new Random();
		timer = new Timer(r.nextInt(10));
		timer.setOnTimerTimeout(()->{
			speed = r.nextInt(125) + 125;
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
	 * @author Dave Jimenez
	 */
	
	private void render(GraphicsContext gc) {
		animation_player.playAnimation("MOVE");
		animation_player.setPosition(position);
		animation_player.render(gc);
		if (!collider.isColliding()) {
		} else {
			destroyCollidingObjects();
		}
	}
	
	/**
	 * Update the position of the small fish.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updatePosition() {
		position.add(new Vector2(speed * dir_x * TIME_MANAGER.getDeltaTime(),0));
		if (position.x < 0)	{
			dir_x = 1;
			position.x = 0;
			animation_player.setHFlip(true);
			collider.setOrigin(new Vector2(-(size.x/2), -(size.y/2)));
		}
		if (position.x > GameStage.WINDOW_WIDTH) {
			dir_x = -1;
			position.x = GameStage.WINDOW_WIDTH;
			animation_player.setHFlip(false);
			collider.setOrigin(new Vector2(-(size.x/2), -(size.y/2)));
		}
	}
	
	/**
	 * Update the position of the collision object.
	 * 
	 * @author Von Divino
	 */
	
	private void updateCollision() {
		collider.setPosition(position);
	}
	
	/**
	 * Destroy the objects that collides with the weapon.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	private void destroyCollidingObjects() {
		ArrayList<GameObject> toremove_list = new ArrayList<GameObject>();
		for (GameObject other: collider.getOverlaps()) {
			toremove_list.add(other);
		}
		for (GameObject other: toremove_list) {
			collider.removeOverlap(other);
			if(other instanceof Spear) {
				SFX_MANAGER.stopAudioPlayer("FISH HIT");
				SFX_MANAGER.playAudioPlayer("FISH HIT");
				other.destroy();
			}
		}
	}
}
