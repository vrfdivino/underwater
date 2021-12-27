package gameobject;

import component.AnimatedSprite;
import component.AnimationPlayer;
import component.AudioPlayer;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import parentclass.GameObject;

/**
 * The PowerUp object.
 * All powerups inherits with this class.
 * 
 * @author Von Divino, Dave Jimenez
 */

public class PowerUp extends GameObject {
	
	/////////////////// PROPERTIES ///////////////////
	
	public static double WIDTH = 92d;
	public static double HEIGHT = 120d;
	public static String PEARL_PATH = "/Game/Pearl.png";
	public static String LIGHTNING_PATH = "/Game/Lightning.png";
	public static String STAR_PATH = "Game/Star.png";
	
	protected AnimatedSprite animated_sprite;
	protected AudioPlayer sfx;
	
	/**
	 * Creates a PowerObject object.
	 * 
	 * @param x The starting x position.
	 * @param y The starting y position.
	 * @author Von Divino, Dave Jimenez
	 */
	
	public PowerUp(double x, double y) {
		setTransformations(x, y);
		setCollision();
	}
	
	/**
	 * Set transformations on a powerup.
	 * 
	 * @param x The starting x position.
	 * @param y The starting y position.
	 * @author Von Divino
	 */
	
	private void setTransformations(double x, double y) {
		size.set(PowerUp.WIDTH, PowerUp.HEIGHT);
		position.set(x,y);
	}
	
	/**
	 * Set sprites and animations on a powerup.
	 * 
	 * @param path The asset path.
	 * @author Dave Jimenez
	 */
	
	public void setSpritesAndAnimation(String path) {
		animation_player = new AnimationPlayer();
		animated_sprite = new AnimatedSprite(new Image[] {new Image(path)},1,position,size);
		animation_player.addAnimation("IDLE", animated_sprite);
	}
	
	/**
	 * Set the collision object attach to the powerup.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	private void setCollision() {
		collider.setCanCollide(true);
		collider.setOrigin(new Vector2(-(size.x/2), -(size.y/2) + 10));
		collider.setSize( new Vector2(92, 100));
		String[] collisions_objs = new String[1];
		collisions_objs[0] = Player.class.getName();
		collider.setCollisionMasks(collisions_objs);
	}
	
	/**
	 * Update the state of the powerup.
	 * 
	 * @author Dave Jimenez
	 */
	
	@Override
	public void update(GraphicsContext gc) {
		render(gc);
		updateCollision();
		updateAudio();
	}
	
	/**
	 * Describe how to render a powerup object.
	 * 
	 * @param gc The graphics context from the canvas.
	 * @author Dave Jimenez
	 */

	private void render(GraphicsContext gc) {
		animation_player.playAnimation("IDLE");
		animation_player.render(gc);
	}
	
	/**
	 * Update the position of the collision object.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updateCollision() {
		collider.setPosition(position);
	}
	
	/**
	 * Update audio of the powerup.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updateAudio() {
		if (collider.isColliding()) {
			AUDIO_MANAGER.removeAudioPlayer("POWERUP");
			AUDIO_MANAGER.addAudioPlayer("POWERUP", sfx);
			AUDIO_MANAGER.stopAudioPlayer("POWERUP");
			AUDIO_MANAGER.playAudioPlayer("POWERUP");
		}	
	}
}
