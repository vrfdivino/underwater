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
		this.animationPlayer = new AnimationPlayer();
		
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
		this.witchAnimSprite_idle_down = new AnimatedSprite(witchAnimSprites_idle_down, 12,  this.position, this.size);
		this.witchAnimSprite_idle_right = new AnimatedSprite(witchAnimSprites_idle_right, 12,  this.position, this.size);
		this.witchAnimSprite_idle_left = new AnimatedSprite(witchAnimSprites_idle_left, 12,  this.position, this.size);
		this.witchAnimSprite_idle_up = new AnimatedSprite(witchAnimSprites_idle_up, 12,  this.position, this.size);
		this.witchAnimSprite_down = new AnimatedSprite(witchAnimSprites_down, 12, this.position, this.size);
		this.witchAnimSprite_right = new AnimatedSprite(witchAnimSprites_right, 12,  this.position, this.size);
		this.witchAnimSprite_left = new AnimatedSprite(witchAnimSprites_left, 12,  this.position, this.size);
		this.witchAnimSprite_up = new AnimatedSprite(witchAnimSprites_up, 12, this.position, this.size);
		
		//Add AnimatedSprites to animationPlayer
		this.animationPlayer.addAnimation("IDLE_DOWN", this.witchAnimSprite_idle_down);
		this.animationPlayer.addAnimation("IDLE_RIGHT", this.witchAnimSprite_idle_right);
		this.animationPlayer.addAnimation("IDLE_LEFT", this.witchAnimSprite_idle_left);
		this.animationPlayer.addAnimation("IDLE_UP", this.witchAnimSprite_idle_up);
		this.animationPlayer.addAnimation("RUN_DOWN", this.witchAnimSprite_down);
		this.animationPlayer.addAnimation("RUN_RIGHT", this.witchAnimSprite_right);
		this.animationPlayer.addAnimation("RUN_LEFT", this.witchAnimSprite_left);
		this.animationPlayer.addAnimation("RUN_UP", this.witchAnimSprite_up);
	}
	
	@Override
	public void update(GraphicsContext gc) {
		//State Machine
		switch(state) {							
		case NORMAL:
			this.normal();
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
		this.getInput();
		this.updatePosition();
	}
	
	private void getInput() {
		this.direction = new Vector2(INPUT_MANAGER.pressedInt("RIGHT") - INPUT_MANAGER.pressedInt("LEFT"),
									 INPUT_MANAGER.pressedInt("DOWN") - INPUT_MANAGER.pressedInt("UP"));
	}
	
	private void updatePosition() {
		this.direction = direction.normalize();
		this.velocity = direction.multiply(moveSpeed);
		
		this.position.add(velocity);
		this.animationPlayer.setPosition(this.position);
	}
	
	private void render(GraphicsContext gc) {
		checkFlips();
		
		this.animationPlayer.render(gc);
	}
	
	private void checkFlips() {
		
		if (this.direction.x < 0) {
			//this.animationPlayer.setHFlip(true);
			this.animationPlayer.playAnimation("RUN_LEFT");
		} else if(this.direction.x > 0) {
			//this.animationPlayer.setHFlip(false);
			this.animationPlayer.playAnimation("RUN_RIGHT");
		}
		
		if (this.direction.y < 0) {
			//this.animationPlayer.setVFlip(false);
			this.animationPlayer.playAnimation("RUN_UP");
		}else if (this.direction.y > 0) {
			//this.animationPlayer.setVFlip(true);
			this.animationPlayer.playAnimation("RUN_DOWN");
		}
		
		if (this.direction.x == 0 && this.direction.y == 0) {
			switch(this.animationPlayer.getCurrentAnimationName()) {
			case "RUN_LEFT":
				this.animationPlayer.playAnimation("IDLE_LEFT");
				break;
			case "RUN_RIGHT":
				this.animationPlayer.playAnimation("IDLE_RIGHT");
				break;
			case "RUN_UP":
				this.animationPlayer.playAnimation("IDLE_UP");
				break;
			case "RUN_DOWN":
				this.animationPlayer.playAnimation("IDLE_DOWN");
				break;
			}
		}		
	}
}
