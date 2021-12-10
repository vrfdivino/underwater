package component;

import java.util.ArrayList;

import datatype.Vector2;
import parentclass.GameObject;

public class Collision {
	private boolean can_collide = true;
	private Vector2 position = new Vector2();
	private Vector2 size = new Vector2();
	private ArrayList<String> collides_with = new ArrayList<String>();
	private ArrayList<GameObject> overlaps = new ArrayList<GameObject>();
	
	public Collision(boolean can_collide, double x, double y, double width, double height) {
		this.setCollide(can_collide);
		this.setPosition(x,y);
		this.setSize(width, height);
	}
	
	public Collision(boolean can_collide, Vector2 position, Vector2 size) {
		this.setCollide(can_collide);
		this.setPosition(position);
		this.setSize(size);
	}

	public boolean overlaps(GameObject other) {
		if (other.getCollision().canCollide() && collides_with.contains(other.getClass().getName())) {
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
			return !noOverlap;			
		}
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
	
	//Getters
	public ArrayList<String> getCollisions() {
		return collides_with;
	}
	
	public boolean canCollide() {
		return can_collide;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Vector2 getSize() {
		return size;
	}
	
	public ArrayList<GameObject> getOverlaps() {
		return overlaps;
	}
}
