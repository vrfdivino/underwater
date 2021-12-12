package component;

import java.util.ArrayList;

import datatype.Vector2;
import gameobject.AnglerFish;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import parentclass.GameObject;

public class Collision {
	private boolean can_collide = true;
	private boolean is_colliding = false;
	private Vector2 position = Vector2.ZERO;
	private Vector2 origin = new Vector2();
	private Vector2 size = new Vector2();
	private ArrayList<String> collides_with = new ArrayList<String>();
	private ArrayList<GameObject> overlaps = new ArrayList<GameObject>();
	
	public Vector2 test_value = new Vector2(100, 0);
	
	public Collision(boolean can_collide, double x, double y, double width, double height) {
		this.setCollide(can_collide);
		this.setOrigin(x,y);
		this.setSize(width, height);
	}
	
	public Collision(boolean can_collide, Vector2 position, Vector2 size) {
		this.setCollide(can_collide);
		this.setOrigin(position);
		this.setSize(size);
	}
	
	public Collision() {}
	
	public void subTest(Vector2 value) {
		test_value = new Vector2(test_value.x - value.x, test_value.y - value.y);
	}
	
	public boolean overlaps(GameObject other) {
			
		if (can_collide && other.getCollision().canCollide() && collides_with.contains(other.getClass().getName())) {
			boolean noOverlap = position.x + size.x < other.getCollision().position.x ||
					other.getCollision().position.x + other.getCollision().size.x < position.x ||
					position.y + size.y < other.getCollision().position.y ||
					other.getCollision().position.y + other.getCollision().size.y < position.y;

			if (!noOverlap) {
				if (!overlaps.contains(other)) {
					overlaps.add(other);
				}
			}	else {
				if (overlaps.contains(other)) {
					overlaps.remove(other);
				}
			}
			is_colliding = !noOverlap;
			return !noOverlap;			
		}

		is_colliding = false;
		return false;
	}
	
	//Setters
	public void setCollisions(String[] gameObjectClasses) {
		collides_with.clear();
		for (int i = 0; i < gameObjectClasses.length; i++) {
			this.collides_with.add(gameObjectClasses[i]);
		}
	}
	
	public void setCollide(boolean can_collide) {
		this.can_collide = can_collide;
	}
	
	public void setPosition(double x, double y) {
		this.position = new Vector2(x + origin.x, y + origin.y);
	}
	
	public void setPosition(Vector2 position) {
		this.position = new Vector2(position.x + origin.x, position.y + origin.y);
	}
	
	public void setOrigin(double x, double y) {
		this.origin.set(x,y);
	}
	
	public void setOrigin(Vector2 origin) {
		this.origin = origin;
	}
	
	public void setSize(double width, double height) {
		this.size.set(width, height);
	}
	
	public void setSize(Vector2 size) {
		this.size.set(size);
	}
	
	public void renderCollision(GraphicsContext gc, Color color, double alpha) {
		gc.setFill(color);
		gc.setGlobalAlpha(alpha);
		gc.fillRect(position.x, position.y, size.x, size.y);
		
		gc.setGlobalAlpha(1.0);
	}
	
	public void renderCollision(GraphicsContext gc) {
		gc.setFill(Color.RED);
		gc.setGlobalAlpha(0.5);
		gc.fillRect(position.x, position.y, size.x, size.y);
		
		gc.setGlobalAlpha(1.0);
		
	}
	
	//Getters
	public ArrayList<String> getCollisions() {
		return collides_with;
	}
	
	public boolean isColliding() {
		return is_colliding;
	}
	
	public boolean canCollide() {
		return can_collide;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Vector2 getOrigin() {
		return origin;
	}
	
	public Vector2 getSize() {
		return size;
	}
	
	public ArrayList<GameObject> getOverlaps() {
		return overlaps;
	}
}
