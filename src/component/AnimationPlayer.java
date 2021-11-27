package component;

import java.util.HashMap;

import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;

public class AnimationPlayer {
	
	private Vector2 position = new Vector2();
	private boolean isVisible = true;
	private boolean isHFlip = false;
	private boolean isVFlip = false;
	private HashMap<String, AnimatedSprite> animations = new HashMap<String, AnimatedSprite>();
	private String current_animation;
	
	public void addAnimation(String name, AnimatedSprite animation) {
		this.animations.put(name, animation);
		this.current_animation = name;
	}
	
	public void playAnimation(String name) {
		this.animations.get(name).start();
		this.current_animation = name;
	}
	
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
	
	public boolean isHFlip() {
		return this.isHFlip;
	}
	
	public void setVFlip(boolean isVFlip) {
		this.isVFlip = isVFlip;
		
		for (AnimatedSprite animation : animations.values()) {
			animation.setVFlip(isVFlip);
		}
	}
	
	public boolean isVFlip() {
		return this.isVFlip;
	}
	
	public boolean isVisible() {
		return this.isVisible;
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public AnimatedSprite getAnimation(String name) {
		return this.animations.get(name);
	}
	
	public String getCurrentAnimationName() {
		return current_animation;
	}
	
	public void render(GraphicsContext gc) {
		this.animations.get(current_animation).render(gc);
	}
}
