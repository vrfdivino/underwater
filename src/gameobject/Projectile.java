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
 * @author Von Divino
 */
public class Projectile extends GameObject {
	
	
	public static int HEIGHT = 64;
	public static int WIDTH = 20;
	public static int DIVER_X_OFFSET = 100;
	public static int DIVER_Y_OFFSET = 450 + 320;
	public static int SPEED = 10;
	public static int STARTING_ROT = 0;
	public static int DAMAGE = 20;
	
	private AnimatedSprite projectile_sprite;
	private GameObject parent;
	private boolean is_released = false;
	
	/**
	 * Creates a new weapon object.
	 * 
	 * @param parent The parent object to be attached.
	 * @author Von Divino
	 */
	
	public Projectile(GameObject parent) {
		this.parent = parent;
		setTransformations();
		setSpritesAndAnimations();
		setCollision();
	}
	
	/**
	 * Set the starting position and size of the weapon.
	 * 
	 * @author Von Divino
	 */
	
	private void setTransformations() {
		size.set(Projectile.HEIGHT, Projectile.WIDTH);
		rotation = Projectile.STARTING_ROT;
		setPositionWithParent();
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
		getInput();
		updateCollision();
		render(gc);
	}
	
	/**
	 * Describe how to render a weapon object.
	 * 
	 * @param gc The graphics context from the canvas.
	 * @author Von Divino
	 */
	
	private void render(GraphicsContext gc) {
		animation_player.playAnimation("IDLE");
		if(!is_released) {
			setPositionWithParent();
			animation_player.setPosition(position);	
		} else {
			setReleasePosition();
		}
		if (!collision.isColliding()) {
		} else {
			destroyCollidingObjects();
		}
		animation_player.render(gc);
	}
	
	/**
	 * Stick the weapon position relative to the parent.
	 * 
	 * @author Von Divino
	 */
	
	private void setPositionWithParent() {
		double x = parent.getPosition().x + Projectile.DIVER_X_OFFSET;
		double y = parent.getPosition().y + Projectile.DIVER_Y_OFFSET;
		position.set(x, y);
	}
	
	/**
	 * Get the input press by the user.
	 * Only handles the SPACE key.
	 * 
	 * @author Von Divino
	 */
	
	private void getInput() {
		if(INPUT_MANAGER.pressedInt("SPACE") == 1) is_released = true;
	}
	
	/**
	 * Set the position when the weapon is being released.
	 * 
	 * @author Von Divino
	 */
	
	private void setReleasePosition() {
		if(position.x < GameStage.WINDOW_WIDTH + Projectile.DIVER_X_OFFSET) {
			position.x += Projectile.SPEED;
			animation_player.setPosition(position);
		} else {
			parent.setChild(new Projectile(parent));
			destroy();
		}
	}
	
	/**
	 * Set the collision object attach to the weapon.
	 * 
	 * 	@author Von Divino
	 */
	
	private void setCollision() {
		collision.setCollide(true);
		collision.setOrigin(new Vector2(-(size.x/2) + 20, -(size.y/2) + 10));
		collision.setSize( new Vector2(64, 20));
		String[] collisions_objs = new String[2];
		collisions_objs[0] = SmallFish.class.getName();
		collisions_objs[1] = AnglerFish.class.getName();
		collision.setCollisions(collisions_objs);
	}
	
	/**
	 * Update the position of the collision object.
	 * 
	 * 	@author Von Divino
	 */
	
	private void updateCollision() {
		collision.setPosition(position);
	}
	
	/**
	 * Destroy the objects that collides with the weapon.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	private void destroyCollidingObjects() {
		ArrayList<GameObject> toremove_list = new ArrayList<GameObject>();
		for (GameObject other: collision.getOverlaps()) {
			toremove_list.add(other);
		}
		for (GameObject other: toremove_list) {
			collision.removeOverlap(other);
			// destroy fish immediately
			if(other instanceof SmallFish) {
				other.destroy();
			} else if(other instanceof AnglerFish) {
				// if collides with the bullet, deduct its own life
				AnglerFish af = (AnglerFish) other;
				af.setHP(af.getHP() - Projectile.DAMAGE);
				System.out.println(af.getHP());
			}
		}
	}

}
