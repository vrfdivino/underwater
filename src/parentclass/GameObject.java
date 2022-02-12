package parentclass;

import component.AnimationPlayer;
import component.BoxCollider2D;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import manager.AudioManager;
import manager.GameManager;
import manager.InputManager;
import manager.PlayerManager;
import manager.SFXManager;
import manager.TimeManager;
import runnableobject.RunnableObject;

/**
 * The class which all the game objects inherits.
 * 
 * @author Dave Jimenez
 */

public class GameObject implements RunnableObject {
	
	/////////////////// PROPERTIES ///////////////////
	
	protected InputManager INPUT_MANAGER = InputManager.getInstance();
	protected GameManager GAME_MANAGER = GameManager.getInstance();
	protected AudioManager AUDIO_MANAGER = AudioManager.getInstance();
	protected SFXManager SFX_MANAGER = SFXManager.getInstance();
	protected PlayerManager PLAYER_MANAGER = PlayerManager.getInstance();
	protected TimeManager TIME_MANAGER = TimeManager.getInstance();
	
	protected AnimationPlayer animation_player;
	protected BoxCollider2D collider = new BoxCollider2D();
	
	protected Vector2 size = new Vector2();
	protected Vector2 position = new Vector2();
	protected double rotation = 0;
	protected boolean is_destroyed = false;
	
	@Override
	public void update(GraphicsContext gc) {}
	public void collidesWith(GameObject other) {collider.overlaps(other);}
	public void destroy() {
		animation_player.setVisible(false);
		collider.setCanCollide(false);
		is_destroyed = true;
	}
	/////////////////// GETTERS ///////////////////
	
	public boolean isDestroyed() {return is_destroyed;}
	public Vector2 getPosition() {return position;}
	public BoxCollider2D getCollider() {return collider;}
	
}
