package gameobject;

import component.AudioPlayer;
import component.Timer;
import constants.Assets;

/**
 * The lightning powerup object.
 * 
 * @author Dave Jimenez, Von Divino
 */

public class Lightning extends PowerUp {

	public Lightning(double x, double y) {
		super(x, y);
		setSpritesAndAnimation(PowerUp.LIGHTNING_PATH);
		sfx = new AudioPlayer(Assets.POWERUP_2);
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
