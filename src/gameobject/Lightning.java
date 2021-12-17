package gameobject;

import parentclass.PowerUp;

public class Lightning extends PowerUp {

	public Lightning(double x, double y) {
		super(x, y);
		setSpritesAndAnimation(PowerUp.LIGHTNING_PATH);
	}

}
