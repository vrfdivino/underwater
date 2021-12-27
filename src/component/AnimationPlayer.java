package component;

import java.util.ArrayList;
import java.util.HashMap;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;

/**
 * Stores and handles playing AnimatedSprites.
 * 
 * @author Dave Jimenez
 */

public class AnimationPlayer {
	
	/////////////////// PROPERTIES ///////////////////
	
	private double alpha       = 1.0;
	private double rotation    = 0;
	private boolean is_playing = false;
	private boolean is_visible = true;
	private boolean is_hflip   = false;
	private boolean is_vflip   = false;
	private String current_animation = "";
	private Vector2 position   = new Vector2();
	private HashMap<String, AnimatedSprite> animations = new HashMap<String, AnimatedSprite>();

	/**
	 * Adds a new AnimatedSprite with an identifier.
	 * 
	 * @param name      ( String ) Identifier of the AnimatedSprite to add.
	 * @param animation ( AnimatedSprite ) The AnimatedSprite to add.
	 * @author Dave Jimenez
	 */
	
	public void addAnimation(String name, AnimatedSprite animation) {
		this.animations.put(name, animation);
		this.current_animation = name;
	}
	
	/**
	 * Plays the specified AnimatedSprite.
	 * 
	 * @param name ( String ) Name of the AnimatedSprite.
	 * @author Dave Jimenez
	 */
	
	public void playAnimation(String name) {
		if (!current_animation.equals(name) || !is_playing) {
			animations.get(current_animation).stop();
			animations.get(name).start();
			current_animation = name;
			is_playing = true;
		}
	}
	
	/**
	 * Renders the current AnimatedSprite into the canvas.
	 * 
	 * @param gc (GraphicsContext) The GraphicsContext of the current scene. 
	 * @author Dave Jimenez
	 */
	
	public void render(GraphicsContext gc) {
		animations.get(current_animation).render(gc);
	}
	
	/////////////////// GETTERS ///////////////////
	
	public Vector2 getPosition() {return position;}
	public boolean isHFlip() {return is_hflip;}
	public boolean isVFlip() {return is_vflip;}
	public boolean isVisible() {return is_visible;}
	public AnimatedSprite getAnimation(String name) {return animations.get(name);}
	public String getCurrentAnimationName() {return current_animation;}
	public double getRotation() {return rotation;}
	public double getAlpha() {return alpha;}
	public ArrayList<String> getAllAnimationNames() {
		ArrayList<String> animNames = new ArrayList<String>();
		for (String name: animations.keySet())	animNames.add(name);
		return animNames;
	}
	
	/////////////////// SETTERS ///////////////////
	
	public void setVisible(boolean is_visible) {this.is_visible = is_visible;}
	public void setPosition(double x, double y) {
		this.position.set(x, y);
		for (AnimatedSprite animation : animations.values())
			animation.setPosition(this.position);
	}
	public void setPosition(Vector2 position) {
		this.position.set(position.x, position.y);
		for (AnimatedSprite animation : animations.values()) 
			animation.setPosition(this.position);
	}
	public void setHFlip(boolean is_hflip) {
		this.is_hflip = is_hflip;
		for (AnimatedSprite animation : animations.values()) 
			animation.setHFlip(is_hflip);
	}
	public void setVFlip(boolean is_vflip) {
		this.is_vflip = is_vflip;
		for (AnimatedSprite animation : animations.values()) 
			animation.setVFlip(is_vflip);
	}	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
		for (AnimatedSprite animation: animations.values())
			animation.setAlpha(alpha);
	}
	public void setRotation(double rotation) {
		this.rotation = rotation;
		for (AnimatedSprite animation: animations.values()) 
			animation.setRotation(rotation);
	}
}
