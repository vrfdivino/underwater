package gameobject;

import component.AnimatedSprite;
import component.AnimationPlayer;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import parentclass.GameObject;

public class Player extends GameObject{
	private static enum STATES {NORMAL, SLOWED, STUNNED, DAMAGED};
	private STATES state = STATES.NORMAL;
	
	private Image[] witchAnimSprites_idle_down = new Image[1];
	private Image[] witchAnimSprites_idle_right = new Image[1];
	private Image[] witchAnimSprites_idle_left = new Image[1];
	private Image[] witchAnimSprites_idle_up = new Image[1];
	
	private Image[] witchAnimSprites_down = new Image[8];
	private Image[] witchAnimSprites_right = new Image[8];
	private Image[] witchAnimSprites_left = new Image[8];
	private Image[] witchAnimSprites_up = new Image[8];
	
	private AnimatedSprite witchAnimSprite_down;
	private AnimatedSprite witchAnimSprite_right;
	private AnimatedSprite witchAnimSprite_left;
	private AnimatedSprite witchAnimSprite_up;
	private AnimatedSprite witchAnimSprite_idle_down;
	private AnimatedSprite witchAnimSprite_idle_right;
	private AnimatedSprite witchAnimSprite_idle_left;
	private AnimatedSprite witchAnimSprite_idle_up;
	
	private double moveSpeed = 5;
	
	private Vector2 direction = new Vector2();
	private Vector2 velocity = new Vector2();
	
	public Player(double x, double y) {
		this.setTransformations(x, y);
		this.setSpritesAndAnimations();
	}
	
	public Player(Vector2 position) {
		this.setTransformations(position.x, position.y);
		this.setSpritesAndAnimations();
	}
	
	private void setTransformations(double x, double y) {
		this.position.set(x, y);
		this.size.set(200, 200);
		this.rotation = 0;
	}
	
	private void setSpritesAndAnimations() {
		animationPlayer = new AnimationPlayer();
		
		//Get Image Resources
		witchAnimSprites_idle_down[0] = new Image("/Player/Sprites/Witch_1.png");
		witchAnimSprites_idle_right[0] = new Image("/Player/Sprites/Witch_9.png");
		witchAnimSprites_idle_left[0] = new Image("/Player/Sprites/Witch_17.png");
		witchAnimSprites_idle_up[0] = new Image("/Player/Sprites/Witch_25.png");
		for (int i = 0; i < 8; i++) this.witchAnimSprites_down[i] = new Image("/Player/Sprites/Witch_" + (i+1) + ".png");
		for (int i = 0; i < 8; i++) this.witchAnimSprites_right[i] = new Image("/Player/Sprites/Witch_" + (i+9) + ".png");
		for (int i = 0; i < 8; i++) this.witchAnimSprites_left[i] = new Image("/Player/Sprites/Witch_" + (i+17) + ".png");
		for (int i = 0; i < 8; i++) this.witchAnimSprites_up[i] = new Image("/Player/Sprites/Witch_" + (i+25) + ".png");
		
		//Set ImageResources to AnimatedSprites
		witchAnimSprite_idle_down = new AnimatedSprite(witchAnimSprites_idle_down, 12,  this.position, this.size);
		witchAnimSprite_idle_right = new AnimatedSprite(witchAnimSprites_idle_right, 12,  this.position, this.size);
		witchAnimSprite_idle_left = new AnimatedSprite(witchAnimSprites_idle_left, 12,  this.position, this.size);
		witchAnimSprite_idle_up = new AnimatedSprite(witchAnimSprites_idle_up, 12,  this.position, this.size);
		witchAnimSprite_down = new AnimatedSprite(witchAnimSprites_down, 12, this.position, this.size);
		witchAnimSprite_right = new AnimatedSprite(witchAnimSprites_right, 12,  this.position, this.size);
		witchAnimSprite_left = new AnimatedSprite(witchAnimSprites_left, 12,  this.position, this.size);
		witchAnimSprite_up = new AnimatedSprite(witchAnimSprites_up, 12, this.position, this.size);
		
		//Add AnimatedSprites to animationPlayer
		animationPlayer.addAnimation("IDLE_DOWN", this.witchAnimSprite_idle_down);
		animationPlayer.addAnimation("IDLE_RIGHT", this.witchAnimSprite_idle_right);
		animationPlayer.addAnimation("IDLE_LEFT", this.witchAnimSprite_idle_left);
		animationPlayer.addAnimation("IDLE_UP", this.witchAnimSprite_idle_up);
		animationPlayer.addAnimation("RUN_DOWN", this.witchAnimSprite_down);
		animationPlayer.addAnimation("RUN_RIGHT", this.witchAnimSprite_right);
		animationPlayer.addAnimation("RUN_LEFT", this.witchAnimSprite_left);
		animationPlayer.addAnimation("RUN_UP", this.witchAnimSprite_up);
	}
	
	@Override
	public void update(GraphicsContext gc) {
		//State Machine
		switch(state) {							
		case NORMAL:
			normal();
			break;
		case SLOWED:
			break;
		case STUNNED:
			break;
		case DAMAGED:
			break;
		}
		
		this.render(gc);
	}
	
	public void normal() {
		getInput();
		updatePosition();
	}
	
	private void getInput() {
		direction = new Vector2(INPUT_MANAGER.pressedInt("RIGHT") - INPUT_MANAGER.pressedInt("LEFT"),
								INPUT_MANAGER.pressedInt("DOWN") - INPUT_MANAGER.pressedInt("UP"));
	}
	
	private void updatePosition() {
		direction = direction.normalize();
		velocity = direction.multiply(moveSpeed);
		
		position.add(velocity);
		animationPlayer.setPosition(position);
	}
	
	private void render(GraphicsContext gc) {
		checkFlips();
		
		animationPlayer.render(gc);
	}
	
	private void checkFlips() {
		
		if (direction.x < 0) {
			//this.animationPlayer.setHFlip(true);
			animationPlayer.playAnimation("RUN_LEFT");
		} else if(direction.x > 0) {
			//this.animationPlayer.setHFlip(false);
			animationPlayer.playAnimation("RUN_RIGHT");
		}
		
		if (direction.y < 0) {
			//this.animationPlayer.setVFlip(false);
			animationPlayer.playAnimation("RUN_UP");
		}else if (direction.y > 0) {
			//this.animationPlayer.setVFlip(true);
			animationPlayer.playAnimation("RUN_DOWN");
		}
		
		if (direction.x == 0 && direction.y == 0) {
			switch(animationPlayer.getCurrentAnimationName()) {
			case "RUN_LEFT":
				animationPlayer.playAnimation("IDLE_LEFT");
				break;
			case "RUN_RIGHT":
				animationPlayer.playAnimation("IDLE_RIGHT");
				break;
			case "RUN_UP":
				animationPlayer.playAnimation("IDLE_UP");
				break;
			case "RUN_DOWN":
				animationPlayer.playAnimation("IDLE_DOWN");
				break;
			}
		}		
	}
}
