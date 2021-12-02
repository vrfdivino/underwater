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
	
	protected Collision collision;
	protected AnimationPlayer animationPlayer;
	
	protected Vector2 size = new Vector2();
	protected Vector2 position = new Vector2();
	protected double rotation = 0;
	
	protected boolean isDestroyed = false;
	
	public Collision getCollision() {
		return collision;
	}
	
	public void destroy() {
		animationPlayer.setVisible(false);
		collision.setCollide(false);
		isDestroyed = true;
	}
	
	public boolean isDestroyed() {
		return isDestroyed;
	}
	
	@Override
	public void update(GraphicsContext gc) {}
	
}