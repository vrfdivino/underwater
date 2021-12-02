package component;

import java.util.HashMap;

import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;

/**
 * Stores and handles playing AnimatedSprites
 * @author Dave
 *
 */
public class AnimationPlayer {
	
	private Vector2 position = new Vector2();
	private boolean isVisible = true;
	private boolean isHFlip = false;
	private boolean isVFlip = false;
	private HashMap<String, AnimatedSprite> animations = new HashMap<String, AnimatedSprite>();
	private String current_animation;
	
	/**
	 * Adds a new AnimatedSprite with an identifier
	 * @param name Identifier of the AnimatedSprite to add
	 * @param animation The AnimatedSprite to add
	 */
	public void addAnimation(String name, AnimatedSprite animation) {
		this.animations.put(name, animation);
		this.current_animation = name;
	}
	
	/**
	 * Plays the specified AnimatedSprite
	 * @param name Name of the AnimatedSprite
	 * @author Dave
	 */
	public void playAnimation(String name) {
		if (!this.current_animation.equals(name)) {
			this.animations.get(current_animation).stop();
			this.animations.get(name).start();
			this.current_animation = name;
		}
	}
	
	/**
	 * Renders the current AnimatedSprite into the canvas
	 * @param gc The GraphicsContext of the current scene 
	 * @author Dave
	 */
	public void render(GraphicsContext gc) {
		animations.get(current_animation).render(gc);
	}
	
	//Setters
	public void setPosition(double x, double y) {
		this.position.set(x, y);
		
		for (AnimatedSprite animation : animations.values()) {
			animation.setPosition(this.position);
		}
	}
	
	public void setPosition(Vector2 position) {
		this.position.set(position.x, position.y);
		
		for (AnimatedSprite animation : animations.values()) {
			animation.setPosition(this.position);
		}
	}
	
	public void setHFlip(boolean isHFlip) {
		this.isHFlip = isHFlip;
		
		for (AnimatedSprite animation : animations.values()) {
			animation.setHFlip(isHFlip);
		}
	}
	
	public void setVFlip(boolean isVFlip) {
		this.isVFlip = isVFlip;
		
		for (AnimatedSprite animation : animations.values()) {
			animation.setVFlip(isVFlip);
		}
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	//Getters
	public boolean isHFlip() {
		return isHFlip;
	}
	
	public boolean isVFlip() {
		return isVFlip;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	public AnimatedSprite getAnimation(String name) {
		return animations.get(name);
	}
	
	public String getCurrentAnimationName() {
		return current_animation;
	}
}
