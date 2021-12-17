package gameobject;

import component.AnimatedSprite;
import component.AnimationPlayer;
import component.Collision;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;
import parentclass.GameObject;

public class Spike extends GameObject{
	
	private AnimatedSprite spike_sprite;
	
	
	private int DAMAGE = 50;
	private double move_speed = 10;
	private Vector2 direction = new Vector2();
	
	public Spike(double x, double y, Vector2 direction) {
		setTransformations(new Vector2(x, y), direction);
		setSpritesAndAnimations();
		setCollisions();
	}
	
	public Spike(Vector2 position, Vector2 direction) {
		setTransformations(position, direction);
		setSpritesAndAnimations();
		setCollisions();
	}
	
	private void setTransformations(Vector2 position, Vector2 direction) {
		this.position.set(position);
		this.size.set(80, 80);
		this.rotation = 0;
		this.direction = direction;
	}
	
	private void setSpritesAndAnimations() {
		animation_player = new AnimationPlayer();
		spike_sprite = new AnimatedSprite(new Image[] {new Image("/Game/SpikeBall.png")}, 1, position, size);
	}
	
	private void setCollisions() {
		collision.setCollide(true);
		collision.setOrigin(new Vector2(-(size.x/2), -(size.y/2)));
		collision.setSize( new Vector2(80, 80));
		String[] collisions_objs = new String[1];
		collisions_objs[0] = Player.class.getName();
		collision.setCollisions(collisions_objs);
	}
	
	@Override
	public void update(GraphicsContext gc) {
		updatePosition();
		updateCollision();
		render(gc);
	}
	
	public void render(GraphicsContext gc) {
		spike_sprite.render(gc);
		collision.renderCollision(gc);
	}
	
	private void updatePosition() {
		position.add(new Vector2(direction.x * move_speed, direction.y * move_speed));
		spike_sprite.setPosition(position);
		
		if (position.x > GameStage.WINDOW_WIDTH + 50 || position.x < -50) {
			destroy();
		}
	}
	
	private void updateCollision() {
		collision.setPosition(position);
		
		if (collision.isColliding()) {
			destroy();
		}

	}
}
