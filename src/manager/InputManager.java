package manager;

import java.util.ArrayList;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;

/**
 * Singleton Class.
 * Handles managing keyboard user inputs.
 * @author Dave
 *
 */
public class InputManager {
	
	private static InputManager instance;
	
	private ArrayList<String> pressed = new ArrayList<String>();
	private ArrayList<String> justPressed = new ArrayList<String>();
	private ArrayList<String> justReleased = new ArrayList<String>();
	
	private InputManager() {
		
	}
	
	public static InputManager getInstance() {
		if (instance == null) {
			instance = new InputManager();
		}
		
		return instance;
	}
	
	public void inputUpdate(Scene scene) {
		
		scene.setOnKeyPressed(
			(KeyEvent e) -> {
				String code = e.getCode().toString();
				if (!pressed.contains(code)) {
					pressed.add(code);
					justPressed.add(code);
				}
			}
		);
		
		scene.setOnKeyReleased(
			(KeyEvent e) -> {
				String code = e.getCode().toString();
				pressed.remove(code);
				justReleased.add(code);				
			}
		);
	}
	
	public boolean pressed(String keycode) {
		return pressed.contains(keycode);
	}
	
	public boolean justPressed(String keycode) {
		return justPressed.contains(keycode);
	}
	
	public boolean justReleased(String keycode) {
		return justReleased.contains(keycode);
	}
	
	public int pressedInt(String keycode) {
		return pressed(keycode) ? 1 : 0;
	}
	
	public int justPressedInt(String keycode) {
		return justPressed(keycode) ? 1 : 0;
	}
	
	public int justReleasedInt(String keycode) {
		return justReleased(keycode) ? 1 : 0;
	}
	
	public void clearPressed() {
		pressed.clear();
	}
	
	public void clearJustPressed() {
		justPressed.clear();
	}
	
	public void clearJustReleased() {
		justReleased.clear();
	}
}
