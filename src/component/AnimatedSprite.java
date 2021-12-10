package component;

import java.util.ArrayList;

import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;

/**
 * Handles a single animation
 * @author Dave
 *
 */
public class AnimatedSprite {
	private double fps = 12;
	private int current_frame = 0;
	private int frames = 0;
	private double delta_frame = 0;
	private double frames_lapsed = 0;
	
	private boolean is_playing = false;
	private boolean is_visible = true;
	
	private double alpha = 1.0;
	
	private Vector2 position = new Vector2();
	private Vector2 size = new Vector2();
	private Vector2 scale = new Vector2(1, 1);
	private double rotation = 0;
	private boolean is_hflip = false;
	private boolean is_vflip = false;
	private ArrayList<Image> textures = new ArrayList<Image>();
	
	/**
	 * Creates a new AnimatedSprite
	 * @param textures An array of Image objects
	 * @param fps Determines the speed of the animation
	 * @param x
	 * @param y
	 * @param width 
	 * @param height
	 * @author Dave
	 */
	public AnimatedSprite(Image[] textures, int fps, double x, double y, double width, double height) {
		this.position.set(x, y);
		this.size.set(width, height);
		
		this.frames = textures.length;
		this.fps = fps;
		this.delta_frame = this.fps/GameStage.JAVA_FPS;
		for (int i = 0; i < textures.length; i++) {
			this.textures.add(textures[i]);
		}
	}
	
	/**
	 * Creates a new AnimatedSprite
	 * @param textures An array of Image objects
	 * @param fps Determines the speed of the animation
	 * @param position
	 * @param size
	 * @author Dave
	 */
	public AnimatedSprite(Image[] textures, int fps, Vector2 position, Vector2 size) {
		this.position.set(position);
		this.size.set(size);

		this.frames = textures.length;
		this.fps = fps;
		this.delta_frame = fps/GameStage.JAVA_FPS;
		for (int i = 0; i < textures.length; i++) {
			this.textures.add(textures[i]);
		}	
	}
	
	/**
	 * Stops the AnimatedSprite and resets current frame to the first frame
	 * @author Dave
	 */
	public void stop() {
		is_playing = false;
		frames_lapsed = 0;
		current_frame = 0;
	}
	
	/**
	 * Starts the AnimatedSprite at the specified frame. Defaults at the first frame.
	 * @author Dave
	 */
	public void start() {
		if (!is_playing) {
			is_playing = true;
			frames_lapsed = 0;
			current_frame = 0;
		}
	}
	
	/**
	 * Starts the AnimatedSprite at the specified frame. Defaults at the first frame.
	 * @param frame
	 * @author Dave
	 */
	public void start(int frame) {
		if (!is_playing) {
			is_playing = true;
			frames_lapsed = frame;
			current_frame = frame;
		}		
	}
	/**
	 * Renders the AnimatedSprite into the Canvas
	 * @param gc GraphicsContext
	 * @author Dave
	 */
	public void render(GraphicsContext gc) {
		playFrames();
		
		double x_offset;
		double y_offset;
		double x_scale_factor;
		double y_scale_factor;
		
		if (isHflip()) {
			x_offset = this.size.x;
			x_scale_factor = -1;
		}
		else {
			x_offset = 0;
			x_scale_factor = 1;
		}
		
		if (isVflip()) {
			y_offset = this.size.y;
			y_scale_factor = -1;
		}
		else {
			y_offset = 0;
			y_scale_factor = 1;
		}
		
		gc.setGlobalAlpha(alpha);
		
		if (is_visible) {
			gc.drawImage(textures.get(current_frame), 
					position.x + x_offset - size.x/2, position.y + y_offset - size.y/2, 
					size.x * scale.x * x_scale_factor, size.y * scale.y * y_scale_factor);
		}
		
		gc.setGlobalAlpha(1.0);
	}
	
	//Increments the frames
	private void playFrames() {
		if (is_playing) {
			frames_lapsed += delta_frame;
			current_frame = (int) Math.floor(frames_lapsed);
			if (current_frame >= frames) {
				current_frame = 0;
				frames_lapsed = 0;
			}
		}
	}
	
	//Setters
	public void setPosition(double x, double y) {
		this.position.set(x, y);
	}
	
	public void setPosition(Vector2 position) {
		this.position.set(position.x, position.y);
	}
	
	public void setSize(double width, double height) {
		this.size.set(width, height);
	}
	
	public void setSize(Vector2 size) {
		this.size.set(size.x, size.y);
	}
	
	public void setScale(double scale_width, double scale_height) {
		this.scale.set(scale_width, scale_height);	
	}
	
	public void setScale(Vector2 scale) {
		this.scale.set(scale.x, scale.y);
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	public void setTextures(Image[] textures) {
		this.frames = textures.length;
		for (int i = 0; i < textures.length; i++) {
			this.textures.add(textures[i]);
		}	
	}
	
	public void setFPS(int fps) {
		this.fps = fps;
		this.delta_frame = fps/GameStage.JAVA_FPS;
	}
	
	public void setVisible(boolean is_visible) {
		this.is_visible = is_visible;
	}
	
	public void setHFlip(boolean is_hflip) {
		this.is_hflip = is_hflip;
	}
	
	public void setVFlip(boolean is_vflip) {
		this.is_vflip = is_vflip;
	}
	
	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}
	
	//Getters
	public Vector2 getPosition() {
		return position;
	}
	
	public Vector2 getSize() {
		return size;
	}
	
	public Vector2 getScale() {
		return scale;
	}
	
	public double getRotation() {
		return rotation;
	}
	
	public int getFrames() {
		return frames;
	}
	
	public int getCurrentFrame() {
		return current_frame;
	}
	
	public boolean isPlaying() {
		return is_playing;
	}
	
	public boolean isVisible() {
		return is_visible;
	}
	
	public boolean isHflip() {
		return is_hflip;
	}
	
	public boolean isVflip() {
		return is_vflip;
	}
	
	public double getAlpha() {
		return alpha;
	}
}
