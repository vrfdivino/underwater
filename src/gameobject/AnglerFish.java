package gameobject;

import component.AnimatedSprite;
import component.AnimationPlayer;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;
import parentclass.GameObject;

public class AnglerFish extends GameObject{
	
	private Image[] anglerFishMoveSprites = new Image[8];
	private AnimatedSprite anglerFishMove;
	
	private int dir_x = -1;
	
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
	
	private void setTransformations(double x, double y) {
		this.position.set(x, y);
		this.size.set(256, 256);
		this.rotation = 0;
	}
	
	private void setSpritesAndAnimations() {
		animationPlayer = new AnimationPlayer();
		
		for (int i = 0; i < 8; i++)	anglerFishMoveSprites[i] = new Image("/Enemy/Sprites/AnglerFish" + (i + 1) + ".png");
		anglerFishMove = new AnimatedSprite(anglerFishMoveSprites, 12, position, size);

		animationPlayer.addAnimation("MOVE", anglerFishMove);
	}
	
	private void setCollision() {
		collision.setCollide(true);
		collision.setOrigin(new Vector2(-(size.x/2) + 40, -(size.y/2) + 60));
		collision.setSize(new Vector2(140, 140));
	}
	
	@Override
	public void update(GraphicsContext gc) {
		updatePosition();
		updateCollision();
		render(gc);
	}
	
	private void updatePosition() {
		position.add(new Vector2(125 * dir_x * TIME_MANAGER.getDeltaTime(),0));
		
		if (position.x < 0)	{
			dir_x = 1;
			position.x = 0;
			animationPlayer.setHFlip(true);
		}
		
		if (position.x > GameStage.WINDOW_WIDTH) {
			dir_x = -1;
			position.x = GameStage.WINDOW_WIDTH;
			animationPlayer.setHFlip(false);
		}
	}
	
	private void updateCollision() {
		collision.setPosition(position);
		//System.out.println("Angler Collision: (" + collision.getPosition().x + ", " + collision.getPosition().y + ")");
	}
	
	private void render(GraphicsContext gc) {
		animationPlayer.playAnimation("MOVE");
		animationPlayer.setPosition(position);
		animationPlayer.render(gc);
		//collision.renderCollision(gc);
	}
}
