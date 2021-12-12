package gameobject;

import component.AnimatedSprite;
import component.AnimationPlayer;
import component.Collision;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import parentclass.GameObject;

public class Player extends GameObject{
	private static enum STATES {NORMAL, SLOWED, STUNNED, DAMAGED};
	private STATES state = STATES.NORMAL;
	
	private AnimatedSprite diverAnimSpriteIdle;
	
	private Image[] diverAnimSprites = new Image[9];
	private AnimatedSprite diverAnimSprite;
	
	private double move_speed = 5;
	
	private Vector2 direction = new Vector2();
	private Vector2 velocity = new Vector2();
	
	public Player(double x, double y) {
		this.setTransformations(x, y);
		this.setSpritesAndAnimations();
		this.setCollision();
	}
	
	public Player(Vector2 position) {
		this.setTransformations(position.x, position.y);
		this.setSpritesAndAnimations();
		this.setCollision();
	}
	
	private void setTransformations(double x, double y) {
		
		this.size.set(256, 1696);
		this.rotation = 0;
		this.position.set(x, y);
	}
	
	private void setCollision() {
		collision.setCollide(true);
		collision.setOrigin(new Vector2(-(size.x/2) + 80, (size.y/2) - 230));
		collision.setSize( new Vector2(100, 190));
		collision.setCollisions(new String[] {AnglerFish.class.getName()});
	}
	
	private void setSpritesAndAnimations() {
		animationPlayer = new AnimationPlayer();
		
		diverAnimSpriteIdle = new AnimatedSprite(new Image[] {new Image("/Player/Sprites/Diver1.png")}, 1, position, size);
		for (int i = 0; i < 9; i++) diverAnimSprites[i] = new Image("/Player/Sprites/Diver" + (i+1) + ".png");
		
		diverAnimSprite = new AnimatedSprite(diverAnimSprites, 12, position, size);
		
		animationPlayer.addAnimation("IDLE", diverAnimSpriteIdle);
		animationPlayer.addAnimation("MOVE", diverAnimSprite);
	}
	public void setPosition(double x, double y) {
		this.position.set(x, y);
	}
	
	public void setPosition(Vector2 position) {
		this.position.set(position);
	}
	
	public void setVelocity(double x, double y) {
		this.velocity.set(x, y);
	}
	
	public void setVelocity(Vector2 velocity) {
		this.velocity.set(velocity);
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Vector2 getVelocity() {
		return velocity;
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
		
		render(gc);
	}
	
	public void normal() {
		getInput();
		updatePosition();
		updateCollision();
	}
	
	private void getInput() {
		direction = new Vector2(INPUT_MANAGER.pressedInt("RIGHT") - INPUT_MANAGER.pressedInt("LEFT"),
								INPUT_MANAGER.pressedInt("DOWN") - INPUT_MANAGER.pressedInt("UP"));
	}
	
	private void updatePosition() {
		direction = direction.normalize();
		velocity.moveTowards(velocity, new Vector2(direction.x * move_speed, direction.y * move_speed), 5 * TIME_MANAGER.getDeltaTime());

		position.add(velocity);
		animationPlayer.setPosition(position);
		
		
	}
	
	private void updateCollision() {
		collision.setPosition(position);
		collision_pos = collision.getPosition();
		//System.out.println(collision.isColliding());
		//System.out.println("Player Collision: (" + collision.getPosition().x + ", " + collision.getPosition().y + ")");
	}
	
	private void render(GraphicsContext gc) {
		checkAnimation();
		
		animationPlayer.render(gc);
		if (!collision.isColliding()) {
			//collision.renderCollision(gc);
		}else {
			//collision.renderCollision(gc, Color.DARKORANGE, 0.5);
		}
		
	}
	
	private void checkAnimation() {
		if (velocity.x == 0 && velocity.y == 0) animationPlayer.playAnimation("IDLE");
		else animationPlayer.playAnimation("MOVE");
	}
}
