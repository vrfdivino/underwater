package component;

import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sprite {
	private Vector2 position = new Vector2();
	private Vector2 size = new Vector2();
	private Vector2 scale = new Vector2(1, 1);
	private double rotation = 0;
	private Image texture;
	
	public Sprite(Image texture, double x, double y, double width, double height) {
		this.setPosition(x, y);
		this.setSize(width, height);
		this.setTexture(texture);
	}
	
	public Sprite(Image texture, Vector2 position, Vector2 size) {
		this.setPosition(position);
		this.setSize(size);
		this.setTexture(texture);
	}
	
	public void setPosition(double x, double y) {
		this.position.set(x, y);
	}
	
	public void setPosition(Vector2 position) {
		this.position.set(position);
	}
	
	public void setSize(double width, double height) {
		this.size.set(width, height);
	}
	
	public void setSize(Vector2 size) {
		this.size.set(size);
	}
	
	public void setScale(double scale_width, double scale_height) {
		this.scale.set(scale_width, scale_height);	
	}
	
	public void setScale(Vector2 scale) {
		this.scale.set(scale);
	}
	
	public void setRotation(double rotation) {
		this.rotation = rotation;
	}
	
	public void setTexture(Image texture) {
		this.texture = texture;
	}
	
	public Vector2 getPosition() {
		return this.position;
	}
	
	public Vector2 getSize() {
		return this.size;
	}
	
	public Vector2 getScale() {
		return this.scale;
	}
	
	public double getRotation() {
		return this.rotation;
	}
	
	public void render(GraphicsContext gc) {
		gc.drawImage(texture, this.position.x, this.position.y, this.size.x * this.scale.x, this.size.y * this.scale.y);
	}
}
