package gameobject;

import java.util.ArrayList;
import component.AnimatedSprite;
import component.AnimationPlayer;
import component.AudioPlayer;
import component.Timer;
import constants.Assets;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import parentclass.GameObject;

/**
 * The main player object that can shoot enemies.
 * It inherits the base properties and methods from the GameObject.
 * 
 * @author Dave Jimenez, Von Divino
 */

public class Player extends GameObject{
	
	/////////////////// PROPERTIES ///////////////////
	
	public static int STARTING_X_POS = 256;
	public static int STARTING_Y_POS = 1696;
	public static int WIDTH = 256;
	public static int HEIGTH = 1696;
	public static int STARTING_ROT = 0;
	
	private static enum STATES {NORMAL, INVULNERABLE, SPEEDUP};
	private STATES state = STATES.NORMAL;
	
	private AnimatedSprite diver_idle;
	private Image[] diver_hit_sprites = new Image[4];
	private AnimatedSprite diver_hit;
	private Image[] diver_move_sprites = new Image[9];
	private AnimatedSprite diver_move;
	
	private double move_speed        = 10;
	private double move_acceleration = 20;
	private boolean can_reload = false;
	private boolean can_absorb = true;
	
	private Vector2 direction = new Vector2();
	private Vector2 velocity  = new Vector2();
	
	/**
	 * Creates a new player object.
	 * 
	 * @param x The starting x position.
	 * @param y The starting y position.
	 * @author Dave Jimenez
	 */
	
	public Player(double x, double y) {
		setTransformations(x, y);
		setSpritesAndAnimations();
		setCollision();
		SFX_MANAGER.addAudioPlayer("HIT", new AudioPlayer(Assets.HIT));
	}
	
	/**
	 * Creates a new player object.
	 * 
	 * @param position The vector position.
	 * @author Dave Jimenez
	 */
	
	public Player(Vector2 position) {
		setTransformations(position.x, position.y);
		setSpritesAndAnimations();
		setCollision();
		SFX_MANAGER.addAudioPlayer("HIT", new AudioPlayer(Assets.HIT));
	}
	
	
	/**
	 * Set the position and sizing properties of the player.
	 *  
	 * @param x The x position.
	 * @param y The y position.
	 * @author Dave Jimenez
	 */
	
	private void setTransformations(double x, double y) {
		size.set(Player.WIDTH, Player.HEIGTH);
		rotation = Player.STARTING_ROT;
		position.set(x, y);
	}
	
	/**
	 * Set the collision object attach to the player.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	private void setCollision() {
		collision.setCollide(true);
		collision.setOrigin(new Vector2(-(size.x/2) + 80, (size.y/2) - 230));
		collision.setSize( new Vector2(100, 190));
		collision.setCollisions(new String[] {AnglerFish.class.getName()});
	}
	
	/**
	 * Set the set of sprite and animation to the player.
	 * 
	 * 	@author Dave Jimenez
	 */
	private void setSpritesAndAnimations() {
		animation_player = new AnimationPlayer();
		// hit sprite
		diver_hit_sprites[0] = new Image("/Player/Sprites/Diver10.png");
		diver_hit_sprites[1] = new Image("/Player/Sprites/Diver1.png");
		diver_hit_sprites[2] = new Image("/Player/Sprites/Diver10.png");
		diver_hit_sprites[3] = new Image("/Player/Sprites/Diver1.png");
		diver_hit = new AnimatedSprite(diver_hit_sprites, 12 , position, size);
		diver_hit.setLoop(false);
		// idles sprite
		diver_idle = new AnimatedSprite(new Image[] {new Image("/Player/Sprites/Diver1.png")}, 1, position, size);
		// move sprite
		for (int i = 0; i < 9; i++) diver_move_sprites[i] = new Image("/Player/Sprites/Diver" + (i+1) + ".png");
		diver_move = new AnimatedSprite(diver_move_sprites, 12, position, size);
		// alias
		animation_player.addAnimation("HIT", diver_hit);
		animation_player.addAnimation("IDLE", diver_idle);
		animation_player.addAnimation("MOVE", diver_move);
	}
	
	/**
	 * Update the state of the player.
	 * 
	 * @author Dave Jimenez
	 */
	
	@Override
	public void update(GraphicsContext gc) {
		switch(state) {							
		case NORMAL:       normal();       break;
		case INVULNERABLE: invulnerable(); break;
		case SPEEDUP:      speedUp();      break;
		}
		render(gc);
	}
	
	/**
	 * Describe how to render a player object.
	 * 
	 * @param gc The graphics context from the canvas.
	 * @author Dave Jimenez
	 */
	
	private void render(GraphicsContext gc) {
		checkAnimation();
		animation_player.render(gc);
		if (!collision.isColliding()) {
		}else {
			SFX_MANAGER.stopAudioPlayer("HIT");
			SFX_MANAGER.playAudioPlayer("HIT");
			animation_player.playAnimation("HIT");
			destroyCollidingObjects();
		}
	}
	
	/**
	 * The normal state.
	 * 
	 * @author Dave Jimenez
	 */
	
	public void normal() {
		getInput();
		updatePosition();
		updateCollision();
	}
	
	// TODO
	public void invulnerable() {
		
	}
	
	// TODO
	public void speedUp() {
		
	}
	
	/**
	 * Get the input press by the user.
	 * Only handles UP, DOWN, LEFT, and RIGHT keys.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void getInput() {
		direction = new Vector2(INPUT_MANAGER.pressedInt("RIGHT") - INPUT_MANAGER.pressedInt("LEFT"),
								INPUT_MANAGER.pressedInt("DOWN") - INPUT_MANAGER.pressedInt("UP"));
	}
	
	/**
	 * Update the position of the player.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updatePosition() {
		direction = direction.normalize();
		velocity.moveTowards(velocity, 
				new Vector2(direction.x * move_speed, direction.y * move_speed),
				move_acceleration * TIME_MANAGER.getDeltaTime());
		position.add(velocity);
		animation_player.setPosition(position);
	}
	
	/**
	 * Update the position of the collision object.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updateCollision() {
		collision.setPosition(position);
		collision_pos = collision.getPosition();
	}
	
	/**
	 * Destroy the objects that collides with the player.
	 * 
	 * 	@author Dave Jimenez
	 */
	private void destroyCollidingObjects() {
		ArrayList<GameObject> toremove_list = new ArrayList<GameObject>();
		for (GameObject other: collision.getOverlaps()) {
			toremove_list.add(other);
		}
		//Solves ConcurrentModificationError
		for (GameObject other: toremove_list) {
			collision.removeOverlap(other);	
			// From von: I comment this out for the mean time, 
			// OBJECTS (enemies and boss) should be destroy when they are shoot, not collide
			// other.destroy();
		}
	}
	
	/**
	 * Check and plays the current player animation.
	 * 
	 * 	@author Dave Jimenez
	 */
	private void checkAnimation() {
		if (animation_player.getAnimation("HIT").isPlaying()) animation_player.playAnimation("HIT");
		else if (velocity.x == 0 && velocity.y == 0) animation_player.playAnimation("IDLE");
		else animation_player.playAnimation("MOVE");
	}
	

	/////////////////// GETTERS ///////////////////
	
	public Vector2 getPosition() {return position;}
	public Vector2 getVelocity() {return velocity;}
	public boolean getCanReload() {return can_reload;}
	public boolean getCanAbsorb() {return can_absorb;}
	
	/////////////////// SETTERS ///////////////////
	
	public void setPosition(double x, double y) {this.position.set(x, y);}
	public void setPosition(Vector2 position) {this.position.set(position);}
	public void setVelocity(double x, double y) {this.velocity.set(x, y);}
	public void setVelocity(Vector2 velocity) {this.velocity.set(velocity);}
	public void setCanReload(boolean can_reload) {this.can_reload = can_reload;}
	public void setCanAbsorb(boolean can_absorb) {
		this.can_absorb = can_absorb;
		Timer timer = new Timer(1);
		timer.setLoop(false);
		timer.onTimerTimeout(()->{
		   this.can_absorb = true;
		 });
		timer.start();
		TIME_MANAGER.addTimer(timer);
	}
}
