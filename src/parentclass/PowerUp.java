package parentclass;

import component.AnimatedSprite;
import component.AnimationPlayer;
import datatype.Vector2;
import gameobject.AnglerFish;
import gameobject.Lightning;
import gameobject.Pearl;
import gameobject.Player;
import gameobject.SmallFish;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class PowerUp extends GameObject {
	
	public static double WIDTH = 92d;
	public static double HEIGHT = 120d;
	public static String PEARL_PATH = "/Game/Pearl.png";
	public static String LIGHTNING_PATH = "/Game/Lightning.png";
	
	private AnimatedSprite animated_sprite;
	
	public PowerUp(double x, double y) {
		setTransformations(x, y);
		setCollision();
	}
	

	
	private void setTransformations(double x, double y) {
		size.set(PowerUp.WIDTH, PowerUp.HEIGHT);
		position.set(x,y);
	}
	
	public void setSpritesAndAnimation(String path) {
		animation_player = new AnimationPlayer();
		animated_sprite = new AnimatedSprite(new Image[] {new Image(path)},1,position,size);
		animation_player.addAnimation("IDLE", animated_sprite);
	}
	private void setCollision() {
		collision.setCollide(true);
		collision.setOrigin(new Vector2(-(size.x/2) + 80, (size.y/2) - 230));
		collision.setSize( new Vector2(100, 190));
		String[] collisions_objs = new String[1];
		collisions_objs[0] = Player.class.getName();
		collision.setCollisions(collisions_objs);
	}
	
	
	@Override
	public void update(GraphicsContext gc) {
		render(gc);
		collision.renderCollision(gc);
		updateCollision();
	}
	

	private void render(GraphicsContext gc) {
		animation_player.playAnimation("IDLE");
		animation_player.render(gc);
	}
	
	private void updateCollision() {
		collision.setPosition(position);
	}
}
