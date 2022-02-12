package gameobject;

import component.AnimatedSprite;
import component.AnimationPlayer;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;
import parentclass.GameObject;

/**
 * The spike object.
 * 
 * @author Dave Jimenez
 */

public class Spike extends GameObject {
	
	/////////////////// PROPERTIES ///////////////////
	public static int DAMAGE = 50;
	private AnimatedSprite spike_sprite;
	
	private double move_speed = 10;
	private Vector2 direction = new Vector2();
	
	/**
	 * Creates a Spike object.
	 * 
	 * @param x The starting x position.
	 * @param y The starting y position.
	 * @param direction The direction of the spike trajectory.
	 * @author Dave Jimenez
	 */
	
	public Spike(double x, double y, Vector2 direction) {
		setTransformations(new Vector2(x, y), direction);
		setSpritesAndAnimations();
		setCollisions();
	}
	
	/**
	 * Creates a Spike object.
	 * 
	 * @param position The vector position.
	 * @param direction The direction of the spike trajectory.
	 * @author Dave Jimenez
	 */
	
	public Spike(Vector2 position, Vector2 direction) {
		setTransformations(position, direction);
		setSpritesAndAnimations();
		setCollisions();
	}
	
	/**
	 * Set the starting position and size of the spike.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void setTransformations(Vector2 position, Vector2 direction) {
		this.position.set(position);
		this.size.set(80, 80);
		this.rotation = 0;
		this.direction = direction;
	}
	
	
	/**
	 * Set the sprite and animation for the spike.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void setSpritesAndAnimations() {
		animation_player = new AnimationPlayer();
		spike_sprite = new AnimatedSprite(new Image[] {new Image("/Game/SpikeBall.png")}, 1, position, size);
	}
	
	/**
	 * Set the collision object attach to the spike.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	private void setCollisions() {
		collider.setCanCollide(true);
		collider.setOrigin(new Vector2(-(size.x/2), -(size.y/2)));
		collider.setSize( new Vector2(80, 80));
		String[] collisions_objs = new String[1];
		collisions_objs[0] = Player.class.getName();
		collider.setCollisionMasks(collisions_objs);
	}
	
	/**
	 * Update the state of the spike.
	 * 
	 * @author Dave Jimenez
	 */
	
	@Override
	public void update(GraphicsContext gc) {
		updatePosition();
		updateCollision();
		render(gc);
	}
	
	/**
	 * Describe how to render a spike object.
	 * 
	 * @param gc The graphics context from the canvas.
	 * @author Dave Jimenez
	 */
	
	public void render(GraphicsContext gc) {
		spike_sprite.render(gc);
		//collision.renderCollision(gc);
	}
	
	/**
	 * Update the position of the spike.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updatePosition() {
		position.add(new Vector2(direction.x * move_speed, direction.y * move_speed));
		spike_sprite.setPosition(position);
		if (position.x > GameStage.WINDOW_WIDTH + 50 || position.x < -50) {
			destroy();
		}
	}
	
	/**
	 * Update the position of the collision object.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updateCollision() {
		collider.setPosition(position);	
		if (collider.isColliding()) {
			destroy();
		}
	}
}
