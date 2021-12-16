package gameobject;

import java.util.Random;

import component.AnimatedSprite;
import component.AnimationPlayer;
import component.Timer;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import main.GameStage;
import parentclass.GameObject;

public class AnglerFish extends GameObject{
	
	public static int DAMAGE = 50;
	
	private Image[] anglerfish_move_sprites = new Image[8];
	private AnimatedSprite anglerfish_move;
	
	private int dir_x = -1;
	
	private int speed = 125;
	private Timer timer;
	
	public AnglerFish(double x, double y) {
		setTransformations(x, y);
		setSpritesAndAnimations();
		setCollision();
	}
	
	public AnglerFish(Vector2 position) {
		setTransformations(position.x, position.y);
		setSpritesAndAnimations();
		setCollision();
	}
	
	/*
	 * Setters and Getters
	 */
	
	private void setTransformations(double x, double y) {
		position.set(x, y);
		size.set(256, 256);
		rotation = 0;
	}
	
	private void setSpritesAndAnimations() {
		animation_player = new AnimationPlayer();
		for (int i = 0; i < 8; i++)	anglerfish_move_sprites[i] = new Image("/Enemy/Sprites/AnglerFish" + (i + 1) + ".png");
		anglerfish_move = new AnimatedSprite(anglerfish_move_sprites, 12, position, size);
		animation_player.addAnimation("MOVE", anglerfish_move);
		animation_player.setRotation(rotation);
	}
	
	private void setCollision() {
		collision.setCollide(true);
		collision.setOrigin(new Vector2(-(size.x/2) + 40, -(size.y/2) + 60));
		collision.setSize(new Vector2(140, 140));
		
		String[] collisions_objs = new String[2];
		collisions_objs[0] = Player.class.getName();
		collisions_objs[1] = Projectile.class.getName();
		collision.setCollisions(collisions_objs);
	}
	
	public void setSpeed(int newSpeed) {
		this.speed = newSpeed;
	}
	
	/*
	 * update() and Coroutines only below 
	 */
	
	@Override
	public void update(GraphicsContext gc) {
		updatePosition();
		updateCollision();
		render(gc);
	}
	
	private void render(GraphicsContext gc) {
		animation_player.playAnimation("MOVE");
		animation_player.setPosition(position);
		animation_player.render(gc);
		if (!collision.isColliding()) {
//			collision.renderCollision(gc);
		} else {
			// deduct strength of the player 
			PLAYER_MANAGER.setStrength(-AnglerFish.DAMAGE);
//			PLAYER_MANAGER.setHp(-20);
			//collision.renderCollision(gc, Color.DARKORANGE, 0.5);
		}
	}
	
	private void updatePosition() {
		position.add(new Vector2(speed * dir_x * TIME_MANAGER.getDeltaTime(),0));
		
		if (position.x < 0)	{
			dir_x = 1;
			position.x = 0;
			animation_player.setHFlip(true);
			collision.setOrigin(new Vector2(-(size.x/2) + 70, -(size.y/2) + 60));
		}
		
		if (position.x > GameStage.WINDOW_WIDTH) {
			dir_x = -1;
			position.x = GameStage.WINDOW_WIDTH;
			animation_player.setHFlip(false);
			collision.setOrigin(new Vector2(-(size.x/2) + 40, -(size.y/2) + 60));
		}
	}
	
	private void updateCollision() {
		collision.setPosition(position);
	}
}
