package gameobject;

import java.util.ArrayList;

import component.AnimatedSprite;
import component.AnimationPlayer;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;
import parentclass.GameObject;

/**
 * The weapon shoots by the player.
 * It inherits the base properties and methods from the GameObject.
 * 
 * @author Von Divino, Dave Jimenez
 */

public class Spear extends GameObject {
	
	/////////////////// PROPERTIES ///////////////////
	
	public static int HEIGHT = 64;
	public static int WIDTH = 20;
	public static int DIVER_X_OFFSET = 100;
	public static int DIVER_Y_OFFSET = 450 + 320;
	public static int SPEED = 14;
	public static int STARTING_ROT = 0;
	public static int DAMAGE = 500;
	public static double DELAY = 0.1d;
	
	private AnimatedSprite projectile_sprite;
	private boolean is_released = false;
	
	/**
	 * Creates a new weapon object.
	 * 
	 * @param x The x position.
	 * @param y The y position.
	 * @author Von Divino, Dave Jimenez
	 */
	
	public Spear(double x, double y) {
		setTransformations(x, y);
		setSpritesAndAnimations();
		setCollision();
	}
	
	/**
	 * Set the starting position and size of the weapon.
	 * 
	 * @author Von Divino, Dave Jimenez
	 */
	
	private void setTransformations(double x, double y) {
		size.set(Spear.HEIGHT, Spear.WIDTH);
		rotation = Spear.STARTING_ROT;
		position.set(x, y);
	}
	
	/**
	 * Set the sprite and animation for the weapon.
	 * 
	 * @author Von Divino
	 */
	
	private void setSpritesAndAnimations() {
		animation_player = new AnimationPlayer();
		projectile_sprite = new AnimatedSprite(new Image[] {new Image("/Game/Spear.png")}, 1, position, size);
		animation_player.addAnimation("IDLE", projectile_sprite);
	}
	
	/**
	 * Update the state of the weapon.
	 * 
	 * @author Von Divino
	 */
	
	@Override
	public void update(GraphicsContext gc) {
		updateCollision();
		render(gc);
	}
	
	/**
	 * Describe how to render a weapon object.
	 * 
	 * @param gc The graphics context from the canvas.
	 * @author Von Divino, Dave Jimenez
	 */
	
	private void render(GraphicsContext gc) {
		animation_player.playAnimation("IDLE");
		if(!is_released) {
			updatePosition();
			animation_player.setPosition(position);	
		} 
		if (!collider.isColliding()) {
		} else {
			destroyCollidingObjects();
		}
		animation_player.render(gc);
	}
	
	/**
	 * Update the position of the projectile.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updatePosition() {
		position.set(position.x + SPEED, position.y);
		if (position.x > GameStage.WINDOW_WIDTH + 50) {
			destroy();
		}
	}
	

	/**
	 * Set the collision object attach to the weapon.
	 * 
	 * 	@author Von Divino, Dave Jimenez
	 */
	
	private void setCollision() {
		collider.setCanCollide(true);
		collider.setOrigin(new Vector2(-(size.x/2) + 20, -(size.y/2) + 10));
		collider.setSize( new Vector2(64, 20));
		String[] collisions_objs = new String[2];
		collisions_objs[0] = SmallFish.class.getName();
		collisions_objs[1] = AnglerFish.class.getName();
		collider.setCollisionMasks(collisions_objs);
	}
	
	/**
	 * Update the position of the collision object.
	 * 
	 * 	@author Von Divino
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
			// destroy fish immediately
			if(other instanceof SmallFish && !other.isDestroyed()) {
				PLAYER_MANAGER.setScore(PLAYER_MANAGER.getScore() + 1);
				SFX_MANAGER.stopAudioPlayer("FISH HIT");
				SFX_MANAGER.playAudioPlayer("FISH HIT");
				other.destroy();
			} else if(other instanceof AnglerFish) {
				SFX_MANAGER.stopAudioPlayer("FISH HIT");
				SFX_MANAGER.playAudioPlayer("FISH HIT");
			}
		}
	}
	
	/////////////////// GETTERS ///////////////////
	
	public boolean getIsReleased() {return is_released;}
	public int getDamage() {return DAMAGE;}
}
