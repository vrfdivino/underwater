package gameobject;

import component.AnimatedSprite;
import component.AnimationPlayer;
import component.Timer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import parentclass.GameObject;

public class SpikeBall extends GameObject {
	
	public static double WIDTH = 80d;
	public static double HEIGHT = 80d;
	public static double ROT = 0.0d;
	public static String PATH = "/Game/SpikeBall.png";
	
	private GameObject ref;
	private AnimatedSprite spike_ball_sprite;
	private boolean is_released = false;
	public double speed = 5;
	
	public SpikeBall(GameObject ref) {
		setRef(ref);
		setTransformations();
		setSpritesAndAnimation();
	}
	

	
	private void setTransformations() {
		size.set(SpikeBall.WIDTH, SpikeBall.HEIGHT);
		rotation = SpikeBall.ROT;
		position.set(getXOffset(), getYOffset());
	}
	
	private void setSpritesAndAnimation() {
		animation_player = new AnimationPlayer();
		spike_ball_sprite = new AnimatedSprite(new Image[] {new Image(SpikeBall.PATH)},1,position,size);
		animation_player.addAnimation("IDLE", spike_ball_sprite);
	}
	
	
	@Override
	public void update(GraphicsContext gc) {
		render(gc);
	}
	

	private void render(GraphicsContext gc) {
		animation_player.playAnimation("IDLE");
		if(!is_released) {
			updatePosition();
		} else {
			updateReleasePosition();
			destroyObject();
		}
		animation_player.render(gc);
	}
	
	private void updatePosition() {
		position.set(getXOffset(), getYOffset());
		animation_player.setPosition(position);
	}
	
	
	private void updateReleasePosition() {
		double x = position.x - speed;
		double y = position.y;
		position.set(x,y);
		animation_player.setPosition(position);
	}
	
	private void destroyObject() {
		if(position.x == 0) {
			destroy();
		}
	}
	
	private void setCollision() {
		
	}
	
	private double getXOffset() {
		return ref.getPosition().x;
	}
	
	private double getYOffset() {
		return ref.getPosition().y;
	}
	
	public GameObject getRef() {
		return ref;
	}
	
	public void setRef(GameObject ref) {
		this.ref = ref;
	}
	
	public void setRelease(boolean release) {
		is_released = release;
	}
	

}
