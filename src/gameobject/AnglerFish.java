package gameobject;

import java.util.ArrayList;

import component.AnimatedSprite;
import component.AnimationPlayer;
import component.Timer;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;
import parentclass.GameObject;

/**
 * The boss fish enemy.
 * It inherits the base properties and methods from the GameObject.
 * 
 * @author Dave Jimenez
 */

public class AnglerFish extends GameObject {
	
	/////////////////// PROPERTIES ///////////////////
	
	public static int DAMAGE = 50;
	public static int HP = 100;
	
	private Image[] anglerfish_move_sprites = new Image[8];
	private AnimatedSprite anglerfish_move;
	private int dir_x = -1;
	private int speed = 125;
	private int hp = AnglerFish.HP;
	
	/**
	 * Creates a new boss fish object.
	 * 
	 * @param x The starting x position.
	 * @param y The starting y position.
	 * @author  Dave Jimenez
	 */
	
	public AnglerFish(double x, double y) {
		setTransformations(x, y);
		setSpritesAndAnimations();
		setCollision();
	}
	
	/**
	 * Creates a new boss fish object.
	 * 
	 * @param position The vector position.
	 * @author Dave Jimenez
	 */
	
	public AnglerFish(Vector2 position) {
		setTransformations(position.x, position.y);
		setSpritesAndAnimations();
		setCollision();
	}
	
	/**
	 * Set the position and sizing properties of the boss fish.
	 *  
	 * @param x The x position.
	 * @param y The y position.
	 * @author Dave Jimenez
	 */
	
	private void setTransformations(double x, double y) {
		position.set(x, y);
		size.set(256, 256);
		rotation = 0;
	}
	
	/**
	 * Set the set of sprite and animation of the boss fish.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	private void setSpritesAndAnimations() {
		animation_player = new AnimationPlayer();
		for (int i = 0; i < 8; i++)	anglerfish_move_sprites[i] = new Image("/Enemy/Sprites/AnglerFish" + (i + 1) + ".png");
		anglerfish_move = new AnimatedSprite(anglerfish_move_sprites, 12, position, size);
		animation_player.addAnimation("MOVE", anglerfish_move);
		animation_player.setRotation(rotation);
	}
	
	/**
	 * Set the collision object attach to the small fish.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	private void setCollision() {
		collision.setCollide(true);
		collision.setOrigin(new Vector2(-(size.x/2) + 40, -(size.y/2) + 60));
		collision.setSize(new Vector2(140, 140));
		String[] collisions_objs = new String[2];
		collisions_objs[0] = Player.class.getName();
		collisions_objs[1] = Projectile.class.getName();
		collision.setCollisions(collisions_objs);
	}
	
	
	/**
	 * Update the state of the boss fish.
	 * 
	 * @author Dave Jimenez
	 */
	
	@Override
	public void update(GraphicsContext gc) {
		updatePosition();
		updateCollision();
		checkIfDead();
		render(gc);
	}
	
	/**
	 * Describe how to render a boss fish object.
	 * 
	 * @param gc The graphics context from the canvas.
	 * @author Dave Jimenez
	 */
	
	private void render(GraphicsContext gc) {
		animation_player.playAnimation("MOVE");
		animation_player.setPosition(position);
		animation_player.render(gc);
		if (!collision.isColliding()) {
			collision.renderCollision(gc);
		} else {
			destroyCollidingObjects();
		}
	}
	
	/**
	 * Update the position of the boss fish.
	 * 
	 * @author Dave Jimenez
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
	 * @author Dave Jimenez
	 */
	
	private void updateCollision() {
		collision.setPosition(position);
	}
	
	/**
	 * Check if needs to be destroy.
	 * 
	 * @author Von Divino
	 */
	
	private void checkIfDead() {
		if(hp == 0) {
			destroy();
		}
	}
	
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
				Projectile proj = (Projectile) other;
				if(proj.getIsReleased()) {
//					other.destroy();	
				}
			} else if(other instanceof Player) {
				Player player = (Player) other;
				if(player.getCanAbsorb()) {
					PLAYER_MANAGER.setHp(PLAYER_MANAGER.getHp() - AnglerFish.DAMAGE);
					player.setCanAbsorb(false);
				}		
			}
		}
	}
	
	public int getHP() {return this.hp;}
	public void setHP(int hp) {this.hp = hp;}
}
