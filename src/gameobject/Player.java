package gameobject;

import java.util.ArrayList;

import component.AnimatedSprite;
import component.AnimationPlayer;
import component.AudioPlayer;
import component.Timer;
import constants.Assets;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import parentclass.GameObject;

public class Player extends GameObject{
	private static enum STATES {NORMAL, INVULNERABLE, SPEEDUP};
	private STATES state = STATES.NORMAL;
	
	private AnimatedSprite diver_idle;
	
	private Image[] diver_hit_sprites = new Image[4];
	private AnimatedSprite diver_hit;
	
	private Image[] diver_move_sprites = new Image[9];
	private AnimatedSprite diver_move;
	
	private double move_speed = 10;
	private double move_acceleration = 20;
	
	private Vector2 direction = new Vector2();
	private Vector2 velocity = new Vector2();
	
	private Timer timer;
	
	public Player(double x, double y) {
		setTransformations(x, y);
		setSpritesAndAnimations();
		setCollision();
		setChild(new Projectile(this));
		
		SFX_MANAGER.addAudioPlayer("HIT", new AudioPlayer(Assets.HIT));
	}
	
	public Player(Vector2 position) {
		setTransformations(position.x, position.y);
		setSpritesAndAnimations();
		setCollision();
		
		SFX_MANAGER.addAudioPlayer("HIT", new AudioPlayer(Assets.HIT));
	}
	
	/*
	 * Setters and Getters
	 */
	
	private void setTransformations(double x, double y) {
		size.set(256, 1696);
		rotation = 0;
		position.set(x, y);
	}
	//Setters
	private void setCollision() {
		collision.setCollide(true);
		collision.setOrigin(new Vector2(-(size.x/2) + 80, (size.y/2) - 230));
		collision.setSize( new Vector2(100, 190));
		collision.setCollisions(new String[] {AnglerFish.class.getName()});
	}
	
	private void setSpritesAndAnimations() {
		animation_player = new AnimationPlayer();
		
		diver_hit_sprites[0] = new Image("/Player/Sprites/Diver10.png");
		diver_hit_sprites[1] = new Image("/Player/Sprites/Diver1.png");
		diver_hit_sprites[2] = new Image("/Player/Sprites/Diver10.png");
		diver_hit_sprites[3] = new Image("/Player/Sprites/Diver1.png");
		
		diver_idle = new AnimatedSprite(new Image[] {new Image("/Player/Sprites/Diver1.png")}, 1, position, size);
		for (int i = 0; i < 9; i++) diver_move_sprites[i] = new Image("/Player/Sprites/Diver" + (i+1) + ".png");
		 
		diver_hit = new AnimatedSprite(diver_hit_sprites, 12 , position, size);
		diver_hit.setLoop(false);
		diver_move = new AnimatedSprite(diver_move_sprites, 12, position, size);
		
		animation_player.addAnimation("HIT", diver_hit);
		animation_player.addAnimation("IDLE", diver_idle);
		animation_player.addAnimation("MOVE", diver_move);
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
	
	//Getters
	public Vector2 getPosition() {
		return position;
	}
	
	public Vector2 getVelocity() {
		return velocity;
	}
	
	/*
	 * update() and coroutines only below
	 */
	
	@Override
	public void update(GraphicsContext gc) {
		//State Machine
		switch(state) {							
		case NORMAL:
			normal();
			break;
		case INVULNERABLE:
			break;
		case SPEEDUP:
			break;
		}
		
		render(gc);
	}
	
	private void render(GraphicsContext gc) {
		checkAnimation();
		
		animation_player.render(gc);
		if (!collision.isColliding()) {
			// collision.renderCollision(gc);
			//System.out.println("Not Colliding");
		}else {
			//System.out.println(collision.getOverlaps());
			SFX_MANAGER.stopAudioPlayer("HIT");
			SFX_MANAGER.playAudioPlayer("HIT");
			animation_player.playAnimation("HIT");
			//collision.renderCollision(gc, Color.DARKORANGE, 0.5);
			destroyCollidingObjects();
		}
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
		velocity.moveTowards(velocity, new Vector2(direction.x * move_speed, direction.y * move_speed), move_acceleration * TIME_MANAGER.getDeltaTime());

		position.add(velocity);
		animation_player.setPosition(position);
	}
	
	private void updateCollision() {
		collision.setPosition(position);
		collision_pos = collision.getPosition();
	}
	
	
	private void destroyCollidingObjects() {
		ArrayList<GameObject> toremove_list = new ArrayList<GameObject>();
		
		for (GameObject other: collision.getOverlaps()) {
			toremove_list.add(other);
		}
		
		//Solves ConcurrentModificationError
		for (GameObject other: toremove_list) {
			//collision.removeOverlap(other);
			
			// From von: I comment this out for the mean time, 
			// OBJECTS (enemies and boss) should be destroy when they are shoot, not collide
//			other.destroy();
		}
	}
	
	private void checkAnimation() {
		//System.out.println(animation_player.getAnimation("HIT").isPlaying());
		if (animation_player.getAnimation("HIT").isPlaying()) animation_player.playAnimation("HIT");
		else if (velocity.x == 0 && velocity.y == 0) animation_player.playAnimation("IDLE");
		else animation_player.playAnimation("MOVE");
	}
	
	public void setChild(GameObject _child) {
		child = _child;
	}
}
