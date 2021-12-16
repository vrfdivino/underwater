package component;

import java.util.ArrayList;

import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import manager.TimeManager;

/**
 * Handles a single animation
 * @author Dave
 *
 */
public class AnimatedSprite {
	private TimeManager TIME_MANAGER = TimeManager.getInstance();
	
	private double fps = 12;
	private int current_frame = 0;
	private int frames = 0;
	private double delta = 0;
	
	private boolean is_playing = false;
	private boolean is_visible = true;
	private boolean is_loop = true;
	private boolean can_loop = true;
	private boolean is_finished = false;
	
	private double alpha = 1.0;
	
	private Vector2 position = new Vector2();
	private Vector2 size = new Vector2();
	private Vector2 scale = new Vector2(1, 1);
	private double rotation = 0;
	private boolean is_hflip = false;
	private boolean is_vflip = false;
	private ArrayList<Image> textures = new ArrayList<Image>();
	
	/**
	 * Creates a new AnimatedSprite.
	 * @param textures ( Image[] ) An array of Image objects
	 * @param fps ( int ) Determines the speed of the animation
	 * @param x ( double )
	 * @param y ( double )
	 * @param width ( double )
	 * @param height ( double )
	 * @author Dave
	 */
	public AnimatedSprite(Image[] textures, int fps, double x, double y, double width, double height) {
		this.position.set(x, y);
		this.size.set(width, height);
		
		this.frames = textures.length;
		this.fps = fps;
		for (int i = 0; i < textures.length; i++) {
			this.textures.add(textures[i]);
		}
	}
	
	/**
	 * Creates a new AnimatedSprite.
	 * @param textures ( Image[] ) An array of Image objects
	 * @param fps ( int ) Determines the speed of the animation
	 * @param position ( Vector2 )
	 * @param size ( Vector2 )
	 * @author Dave
	 */
	public AnimatedSprite(Image[] textures, int fps, Vector2 position, Vector2 size) {
		this.position.set(position);
		this.size.set(size);

		this.frames = textures.length;
		this.fps = fps;
		for (int i = 0; i < textures.length; i++) {
			this.textures.add(textures[i]);
		}	
	}
	
	/**
	 * Stops the AnimatedSprite and resets current frame to the first frame.
	 * @author Dave
	 */
	public void stop() {
		is_playing = false;
		can_loop = true;
		delta = 0;
		current_frame = 0;
	}
	
	/**
	 * Starts the AnimatedSprite at the specified frame. Defaults at the first frame.
	 * @author Dave
	 */
	public void start() {
		if (!is_playing) {
			is_playing = true;
			can_loop = true;
			delta = 0;
			current_frame = 0;
		}
	}
	
	/**
	 * Starts the AnimatedSprite at the specified frame. Defaults at the first frame.
	 * @param frame ( int )
	 * @author Dave
	 */
	public void start(int frame) {
		if (!is_playing) {
			is_playing = true;
			delta = frame;
			current_frame = frame;
		}		
	}
	/**
	 * Renders the AnimatedSprite into the Canvas.
	 * @param gc ( GraphicsContext )
	 * @author Dave
	 */
	public void render(GraphicsContext gc) {
		if (can_loop) {
			double _x_offset;
			double _y_offset;
			double _x_scale_factor;
			double _y_scale_factor;
			
			//Flips the Image accordingly
			if (isHflip()) {
				_x_offset = this.size.x;
				_x_scale_factor = -1;
			}
			else {
				_x_offset = 0;
				_x_scale_factor = 1;
			}
			
			if (isVflip()) {
				_y_offset = this.size.y;
				_y_scale_factor = -1;
			}
			else {
				_y_offset = 0;
				_y_scale_factor = 1;
			}
			
			gc.setGlobalAlpha(alpha);
			gc.save();
			gc.rotate(rotation);
			//Draws the Image if Visible
			if (is_visible) {
				gc.drawImage(textures.get(current_frame), 
						position.x + _x_offset - size.x/2, position.y + _y_offset - size.y/2, 
						size.x * scale.x * _x_scale_factor, size.y * scale.y * _y_scale_factor);
			}
			
			gc.restore();
			gc.setGlobalAlpha(1.0);
		}
		playFrames();
	}
	
	//Increments the frames
	private void playFrames() {
		if (is_playing) {
			delta += fps * TIME_MANAGER.getDeltaTime();
			current_frame = (int) Math.floor(delta);
			if (current_frame >= frames) {
				current_frame = 0;
				delta = 0;
				
				if (!is_loop) {
					can_loop = false;
					is_finished = true;
					is_playing = false;
				}
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
	
	/**
	 * Specifies the set of Images to play in the animation.
	 * @param textures ( Image[] )
	 * @author Dave
	 */
	public void setTextures(Image[] textures) {
		this.frames = textures.length;
		for (int i = 0; i < textures.length; i++) {
			this.textures.add(textures[i]);
		}	
	}
	
	public void setFPS(int fps) {
		this.fps = fps;
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
	
	public void setLoop(boolean is_loop) {
		this.is_loop = is_loop;
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
	
	public boolean canLoop() {
		return can_loop;
	}
	
	public boolean isFinished() {
		return is_finished;
	}
}
