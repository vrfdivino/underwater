package gameobject;

import parentclass.PowerUp;

public class Pearl extends PowerUp {

	public Pearl(double x, double y) {
		super(x, y);
		setSpritesAndAnimation(PowerUp.PEARL_PATH);
	}
}
