package gameobject;

import java.util.ArrayList;

import component.AnimatedSprite;
import component.AnimationPlayer;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;
import parentclass.GameObject;

public class Projectile extends GameObject {
	
	
	public static int HEIGHT = 64;
	public static int WIDTH = 20;
	public static int DIVER_X_OFFSET = 100;
	public static int DIVER_Y_OFFSET = 450 + 320;
	public static int SPEED = 10;
	
	private AnimatedSprite projectile_sprite;
	private GameObject parent;
	private boolean isReleased;
	
	public Projectile(GameObject _parent) {
		parent = _parent;
		isReleased = false;
		setTransformations(/*x, y*/);
		setSpritesAndAnimations();
		setCollision();
	}
	
	private void setTransformations(/*double x, double y*/) {
		size.set(Projectile.HEIGHT, Projectile.WIDTH);
		rotation = 0;
		setPositionWithParent();
	}
	
	private void setSpritesAndAnimations() {
		animation_player = new AnimationPlayer();

		projectile_sprite = new AnimatedSprite(new Image[] {new Image("/Game/Spear.png")}, 1, position, size);
		
		animation_player.addAnimation("IDLE", projectile_sprite);
	}
	
	@Override
	public void update(GraphicsContext gc) {
		
		getInput();
		setShootPosition();
		updateCollision();
		render(gc);
		

	}
	
	private void render(GraphicsContext gc) {

		animation_player.playAnimation("IDLE");
		if(!isReleased) {
			setPositionWithParent();
			animation_player.setPosition(position);	
		} else {
			setShootPosition();
		}
		
		if (!collision.isColliding()) {
			collision.renderCollision(gc);
			System.out.println("not colliding");
		} else {
			System.out.println("colliding");
			destroyCollidingObjects();
		}
		
		animation_player.render(gc);
	}
	
	private void setPositionWithParent() {
		double x = parent.getPosition().x + Projectile.DIVER_X_OFFSET;
		double y = parent.getPosition().y + Projectile.DIVER_Y_OFFSET;
		position.set(x, y);
	}
	
	private void getInput() {
		if(INPUT_MANAGER.pressedInt("SPACE") == 1) {
			isReleased = true;
		}
	}
	
	// TODO: Set destruction here, if not memory leak behind the scenes but still OK
	private void setShootPosition() {
		if(isReleased) {
			if(position.x < GameStage.WINDOW_WIDTH + Projectile.DIVER_X_OFFSET) {
				position.x += Projectile.SPEED;
				animation_player.setPosition(position);
			} else {
				parent.setChild(new Projectile(parent));
			}
		}
		
	}
	
	private void setCollision() {
		collision.setCollide(true);
		collision.setOrigin(new Vector2(-(size.x/2) + 20, -(size.y/2) + 10));
		collision.setSize( new Vector2(64, 20));
		collision.setCollisions(new String[] {AnglerFish.class.getName()});
	}
	
	private void updateCollision() {
		collision.setPosition(position);
	}
	
	private void destroyCollidingObjects() {
		ArrayList<GameObject> toremove_list = new ArrayList<GameObject>();
		
		for (GameObject other: collision.getOverlaps()) {
			toremove_list.add(other);
			System.out.println(other);
		}

		for (GameObject other: toremove_list) {
			collision.removeOverlap(other);
			
			// only destroy if fish
			// just a test, angler fish's hp should be deducted
			if(other instanceof AnglerFish) {
				other.destroy();
			}
		}
	}

}
