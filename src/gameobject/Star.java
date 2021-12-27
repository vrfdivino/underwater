package gameobject;

import component.AudioPlayer;
import component.Timer;
import constants.Assets;

/**
 * The star powerup object.
 * 
 * @author Dave Jimenez, Von Divino
 */

public class Star extends PowerUp {
	
	/**
	 * Creates a Star object.
	 * 
	 * @param x The starting x position.
	 * @param y The starting y position.
	 * @author Dave Jimenez
	 */
	
	public Star(double x, double y) {
		super(x, y);
		setSpritesAndAnimation(PowerUp.STAR_PATH);
		sfx = new AudioPlayer(Assets.POWERUP_1);
		Timer duration_timer = new Timer(5);
		duration_timer.setOnTimerTimeout(()->{
			animated_sprite.setVisible(false);
			collider.setCanCollide(false);
		});
		duration_timer.setLoop(false);
		duration_timer.start();
		TIME_MANAGER.addTimer(duration_timer);
	}
}
