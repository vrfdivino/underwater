package manager;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * Handles managing keyboard user inputs.
 * 
 * @author Dave Jimenez
 */

public class InputManager {
	
	/////////////////// PROPERTIES ///////////////////
	
	private static InputManager instance;
	private ArrayList<String> pressed = new ArrayList<String>();
	private ArrayList<String> just_pressed = new ArrayList<String>();
	private ArrayList<String> just_released = new ArrayList<String>();
	
	private InputManager() {}
	
	public static InputManager getInstance() {
		if (instance == null) {
			instance = new InputManager();
		}
		return instance;
	}
	
	/**
	 * Update the input.
	 * 
	 * @param scene The game scene.
	 * @author Dave Jimenez
	 */
	
	public void inputUpdate(Scene scene) {
		scene.setOnKeyPressed(
			(KeyEvent e) -> {
				String code = e.getCode().toString();
				if (!pressed.contains(code)) {
					pressed.add(code);
					just_pressed.add(code);
				}
			}
		);
		scene.setOnKeyReleased(
			(KeyEvent e) -> {
				String code = e.getCode().toString();
				pressed.remove(code);
				just_released.add(code);				
			}
		);
	}
	
	/////////////////// GETTERS & SETTERS ///////////////////
	
	public boolean pressed(String keycode) {return pressed.contains(keycode);}
	public boolean justPressed(String keycode) {return just_pressed.contains(keycode);}
	public boolean justReleased(String keycode) {return just_released.contains(keycode);}
	public int pressedInt(String keycode) {return pressed(keycode) ? 1 : 0;}
	public int justPressedInt(String keycode) {return justPressed(keycode) ? 1 : 0;}
	public int justReleasedInt(String keycode) {return justReleased(keycode) ? 1 : 0;}
	public void clearPressed() {pressed.clear();}
	public void clearJustPressed() {just_pressed.clear();}
	public void clearJustReleased() {just_released.clear();}
}
