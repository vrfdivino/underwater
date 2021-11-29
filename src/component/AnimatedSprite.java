package component;

import java.util.ArrayList;

import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import main.GameStage;

public class AnimatedSprite {
	private double fps = 12;
	private int current_frame = 0;
	private int frames = 0;
	private double deltaFrame = 0;
	private double frame_lapsed = 0;
	
	private boolean isPlaying = false;
	private boolean isVisible = true;
	
	private Vector2 position = new Vector2();
	private Vector2 size = new Vector2();
	private Vector2 scale = new Vector2(1, 1);
	private double rotation = 0;
	private boolean isHFlip = false;
	private boolean isVFlip = false;
	private ArrayList<Image> textures = new ArrayList<Image>();
	
	public AnimatedSprite(Image[] textures, int fps, double x, double y, double width, double height) {
		this.position.set(x, y);
		this.size.set(width, height);
		
		this.frames = textures.length;
		this.fps = fps;
		this.deltaFrame = this.fps/GameStage.JAVA_FPS;
		for (int i = 0; i < textures.length; i++) {
			this.textures.add(textures[i]);
		}
	}
	
	public AnimatedSprite(Image[] textures, int fps, Vector2 position, Vector2 size) {
		this.position.set(position);
		this.size.set(size);

		this.frames = textures.length;
		this.fps = fps;
		this.deltaFrame = this.fps/GameStage.JAVA_FPS;
		for (int i = 0; i < textures.length; i++) {
			this.textures.add(textures[i]);
		}	
	}
	
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
	
	public int getCurrentFrame() {
		return current_frame;
	}
	
	public void setFPS(int fps) {
		this.fps = fps;
		this.deltaFrame = fps/GameStage.JAVA_FPS;
	}
	
	public void stop() {
		isPlaying = false;
		deltaFrame = 0;
		frame_lapsed = 0;
		current_frame = 0;
	}
	
	public void start() {
		if (!isPlaying) {
			isPlaying = true;
			frame_lapsed = 0;
			current_frame = 0;
		}
	}
	
	public void start(int frame) {
		if (!isPlaying) {
			isPlaying = true;
			frame_lapsed = frame;
			current_frame = frame;
		}		
	}
	
	public boolean isPlaying() {
		return isPlaying;
	}
	
	public boolean isVisible() {
		return isVisible;
	}
	
	public void setVisible(boolean isVisible) {
		this.isVisible = isVisible;
	}
	
	public void setHFlip(boolean isHFlip) {
		this.isHFlip = isHFlip;
	}
	
	public boolean isHFlip() {
		return isHFlip;
	}
	
	public void setVFlip(boolean isVFlip) {
		this.isVFlip = isVFlip;
	}
	
	public boolean isVFlip() {
		return isVFlip;
	}
	
	public void render(GraphicsContext gc) {
		playFrames();
		
		double x_offset;
		double y_offset;
		double x_scale_factor;
		double y_scale_factor;
		
		if (isHFlip()) {
			x_offset = this.size.x;
			x_scale_factor = -1;
		}
		else {
			x_offset = 0;
			x_scale_factor = 1;
		}
		
		if (isVFlip()) {
			y_offset = this.size.y;
			y_scale_factor = -1;
		}
		else {
			y_offset = 0;
			y_scale_factor = 1;
		}
		
		if (isVisible) {
			gc.drawImage(textures.get(current_frame), 
					position.x + x_offset - size.x/2, position.y + y_offset - size.y/2, 
					size.x * scale.x * x_scale_factor, size.y * scale.y * y_scale_factor);
		}		
	}
	
	private void playFrames() {
		if (isPlaying) {
			frame_lapsed += deltaFrame;
			current_frame = (int) Math.floor(frame_lapsed);
			if (current_frame >= frames) {
				current_frame = 0;
				frame_lapsed = 0;
			}
		}
	}
}
