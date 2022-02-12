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
	}
}
