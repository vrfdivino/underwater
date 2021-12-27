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
	
	private AnimatedSprite spear_sprite;
	
	private double move_speed        = 10;
	private double move_acceleration = 20;
	private boolean can_absorb = true;
	
	private Vector2 direction = new Vector2();
	private Vector2 velocity  = new Vector2();
	
	private Timer reload_timer;
	private boolean can_shoot = true;
	
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
		setOthers();
		setAudio();
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
		setOthers();
		setAudio();
	}
	
	/**
	 * Set other properties.
	 * 
	 * @author Dave Jimenez
	 */
	private void setOthers() {
		reload_timer = new Timer(0.5);
		reload_timer.setOnTimerTimeout(()->{
			can_shoot = true;
			spear_sprite.setVisible(true);
		});
		reload_timer.setLoop(false);
		reload_timer.terminateOnEnd(false);
		TIME_MANAGER.addTimer(reload_timer);
	}
	
	/**
	 * Set audio effects properties.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void setAudio() {
		SFX_MANAGER.addAudioPlayer("HIT", new AudioPlayer(Assets.HIT));
		SFX_MANAGER.addAudioPlayer("SHOOT", new AudioPlayer(Assets.SHOOT));
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
		collider.setCanCollide(true);
		collider.setOrigin(new Vector2(-(size.x/2) + 80, (size.y/2) - 230));
		collider.setSize( new Vector2(100, 190));
		String[] collisions_objs = new String[6];
		collisions_objs[0] = SmallFish.class.getName();
		collisions_objs[1] = AnglerFish.class.getName();
		collisions_objs[2] = Pearl.class.getName();
		collisions_objs[3] = Lightning.class.getName();
		collisions_objs[4] = Star.class.getName();
		collisions_objs[5] = Spike.class.getName();
		collider.setCollisionMasks(collisions_objs);
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
		
		spear_sprite = new AnimatedSprite(new Image[] {new Image("/Game/Spear.png")}, 1, new Vector2(position.x + 100, position.y + 450 + 320), new Vector2(64, 20));
	}
	
	/**
	 * Update the state of the player.
	 * 
	 * @author Dave Jimenez
	 */
	
	@Override
	public void update(GraphicsContext gc) {
		updateAudio();
		updateAnimation();
		
		switch(state) {							
		case NORMAL:       normal();       break;
		case INVULNERABLE:
			break;
		case SPEEDUP:
			break;
		default:
			break;
		}
		render(gc);
	}
	
	/**
	 * Describe how to render a player object.
	 * 
	 * @param gc ( GraphicsContext ) The graphics context from the canvas.
	 * @author Dave Jimenez
	 */
	
	private void render(GraphicsContext gc) {
		animation_player.render(gc);
		spear_sprite.render(gc);
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
		updateWeapon();
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
		velocity.moveTowards( 
				new Vector2(direction.x * move_speed, direction.y * move_speed),
				move_acceleration * TIME_MANAGER.getDeltaTime());
		position.add(velocity);
		
		//Update Sprite Position
		animation_player.setPosition(position);
		spear_sprite.setPosition(new Vector2(position.x + 100, position.y + 450 + 320));
	}
	
	/**
	 * Update the position of the collision object.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updateCollision() {
		collider.setPosition(position);
		
		if (animation_player.getAnimation("HIT").isPlaying())	collider.setCanCollide(false);
		else	collider.setCanCollide(true);
		
		if (!collider.isColliding()) {
			
		} else {
			for (GameObject other: collider.getOverlaps()) {
				if (other instanceof Pearl) {
					PLAYER_MANAGER.setHp(PLAYER_MANAGER.getHp() + 50);
				}
				
				if (other instanceof Star) {
					
					String[] collisions_objs = new String[2];
					collisions_objs[0] = Pearl.class.getName();
					collisions_objs[1] = Lightning.class.getName();
					collider.setCollisionMasks(collisions_objs);
					
					animation_player.setAlpha(0.75);
					
					Timer invul_timer = new Timer(3);
					invul_timer.setLoop(false);
					invul_timer.setOnTimerTimeout(()->{
						
						String[] new_collisions_objs = new String[6];
						new_collisions_objs[0] = SmallFish.class.getName();
						new_collisions_objs[1] = AnglerFish.class.getName();
						new_collisions_objs[2] = Pearl.class.getName();
						new_collisions_objs[3] = Lightning.class.getName();
						new_collisions_objs[4] = Star.class.getName();
						new_collisions_objs[5] = Spike.class.getName();
						collider.setCollisionMasks(new_collisions_objs);
						
						animation_player.setAlpha(1.0);
					});
					invul_timer.start();
					TIME_MANAGER.addTimer(invul_timer);
				}
				
				if (other instanceof Lightning) {
					move_speed = 20;
					move_acceleration = 100;
					
					Timer speedup_timer = new Timer(5);
					speedup_timer.setLoop(false);
					speedup_timer.setOnTimerTimeout(()->{
						move_speed = 10;
						move_acceleration = 20;
					});
					speedup_timer.start();
					TIME_MANAGER.addTimer(speedup_timer);
				}
			}
			destroyCollidingObjects();
		}
		
	}
	
	/**
	 * Update the weapon of the player.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updateWeapon() {
		if (INPUT_MANAGER.justPressed("SPACE") && can_shoot) {
			GAME_MANAGER.addRunnableObject(new Spear(position.x + 100, position.y + 450 + 320));
			
			SFX_MANAGER.stopAudioPlayer("SHOOT");
			SFX_MANAGER.playAudioPlayer("SHOOT");	
			
			can_shoot = false;
			spear_sprite.setVisible(false);
			
			reload_timer.start();
		}
	}
	
	/**
	 * Destroy the objects that collides with the player.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	private void destroyCollidingObjects() {
		ArrayList<GameObject> toremove_list = new ArrayList<GameObject>();
		for (GameObject other: collider.getOverlaps()) {
			toremove_list.add(other);
		}
		//Solves ConcurrentModificationError
		for (GameObject other: toremove_list) {
			collider.removeOverlap(other);	
			if(other instanceof SmallFish) {
				other.destroy();
				PLAYER_MANAGER.setHp(PLAYER_MANAGER.getHp() - SmallFish.DAMAGE);
			} else if(other instanceof AnglerFish) {
				PLAYER_MANAGER.setHp(PLAYER_MANAGER.getHp() - AnglerFish.DAMAGE);
			} else if (other instanceof Spike){
				PLAYER_MANAGER.setHp(PLAYER_MANAGER.getHp() - Spike.DAMAGE);
				other.destroy();
			} else {
				other.destroy();
			}
		}
	}
	
	/**
	 * Check and plays the current player animation.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	private void updateAnimation() {
		if (!collider.isColliding()) {
		}else {
			animation_player.playAnimation("HIT");
		}
		
		if (animation_player.getAnimation("HIT").isPlaying()) animation_player.playAnimation("HIT");
		else if (velocity.x == 0 && velocity.y == 0) animation_player.playAnimation("IDLE");
		else animation_player.playAnimation("MOVE");
	}
	
	/**
	 * Update audio properties.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	private void updateAudio() {
		if (!collider.isColliding()) {

		} else {
			for (GameObject other: collider.getOverlaps()) {
				if (other instanceof SmallFish ||
					other instanceof AnglerFish ||
					other instanceof Spike) {
					SFX_MANAGER.stopAudioPlayer("HIT");
					SFX_MANAGER.playAudioPlayer("HIT");
				}
			}
		}
	}

	/////////////////// GETTERS ///////////////////
	
	public Vector2 getPosition() {return position;}
	public Vector2 getVelocity() {return velocity;}
	public boolean getCanAbsorb() {return can_absorb;}
	
	/////////////////// SETTERS ///////////////////
	
	public void setPosition(double x, double y) {this.position.set(x, y);}
	public void setPosition(Vector2 position) {this.position.set(position);}
	public void setVelocity(double x, double y) {this.velocity.set(x, y);}
	public void setVelocity(Vector2 velocity) {this.velocity.set(velocity);}
}
