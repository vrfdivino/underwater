package parentclass;

import component.AnimationPlayer;
import component.Collision;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import manager.AudioManager;
import manager.GameManager;
import manager.InputManager;
import manager.PlayerManager;
import manager.SFXManager;
import manager.TimeManager;
import runnableobject.RunnableObject;

public class GameObject implements RunnableObject {
	protected InputManager INPUT_MANAGER = InputManager.getInstance();
	protected GameManager GAME_MANAGER = GameManager.getInstance();
	protected AudioManager AUDIO_MANAGER = AudioManager.getInstance();
	protected SFXManager SFX_MANAGER = SFXManager.getInstance();
	protected PlayerManager PLAYER_MANAGER = PlayerManager.getInstance();
	protected TimeManager TIME_MANAGER = TimeManager.getInstance();
	
	protected AnimationPlayer animationPlayer;
	protected Collision collision;
	
	protected Vector2 size = new Vector2();
	protected Vector2 position = new Vector2();
	protected double rotation = 0;
	
	protected boolean isDestroyed = false;
	
	public Collision getCollision() {
		return collision;
	}
	
	public void collidesWith(GameObject other) {
		System.out.println(this + " " + collision + " > " + other + " " + other.getCollision());
		collision.overlaps(other);
	}
	
	public void destroy() {
		animationPlayer.setVisible(false);
		isDestroyed = true;
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
	@Override
	public void update(GraphicsContext gc) {}
	
}
