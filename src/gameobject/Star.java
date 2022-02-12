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
	}
}
