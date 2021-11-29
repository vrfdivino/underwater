package component;

import java.util.ArrayList;

import datatype.Vector2;
import parentclass.GameObject;

public class Collision {
	private boolean canCollide = true;
	private Vector2 position = new Vector2();
	private Vector2 size = new Vector2();
	private ArrayList<String> collidesWith = new ArrayList<String>();
	private ArrayList<GameObject> overlaps = new ArrayList<GameObject>();
	
	public Collision(boolean canCollide, double x, double y, double width, double height) {
		this.setCollide(canCollide);
		this.setPosition(x,y);
		this.setSize(width, height);
	}
	
	public Collision(boolean canCollide, Vector2 position, Vector2 size) {
		this.setCollide(canCollide);
		this.setPosition(position);
		this.setSize(size);
	}
	
	public void setCollide(boolean canCollide) {
		this.canCollide = canCollide;
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
	
	public boolean canCollide() {
		return canCollide;
	}
	
	public Vector2 getPosition() {
		return position;
	}
	
	public Vector2 getSize() {
		return size;
	}
	
	public void setCollisions(String[] gameObjectClasses) {
		collidesWith.clear();
		for (int i = 0; i < gameObjectClasses.length; i++) {
			this.collidesWith.add(gameObjectClasses[i]);
		}
	}
	
	public ArrayList<String> getCollisions() {
		return collidesWith;
	}
	
	public boolean overlaps(GameObject other) {
		if (other.getCollision().canCollide() && collidesWith.contains(other.getClass().getName())) {
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
	
	public ArrayList<GameObject> getOverlaps() {
		return overlaps;
	}
}
