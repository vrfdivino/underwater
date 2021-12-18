package gameobject;

import component.AudioPlayer;
import component.Timer;
import constants.Assets;

/**
 * The pearl powerup object.
 * 
 * @author Dave Jimenez, Von Divino
 */

public class Pearl extends PowerUp {

	/**
	 * Creates a PowerObject object.
	 * 
	 * @param x The starting x position.
	 * @param y The starting y position.
	 * @author Von Divino, Dave Jimenez
	 */
	
	public Pearl(double x, double y) {
		super(x, y);
		setSpritesAndAnimation(PowerUp.PEARL_PATH);
		sfx = new AudioPlayer(Assets.POWERUP_3);
		Timer duration_timer = new Timer(5);
		duration_timer.onTimerTimeout(()->{
			animated_sprite.setVisible(false);
			collision.setCollide(false);
		});
		duration_timer.setLoop(false);
		duration_timer.start();
		TIME_MANAGER.addTimer(duration_timer);
	}
}
