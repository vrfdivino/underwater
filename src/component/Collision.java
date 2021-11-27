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
		return this.canCollide;
	}
	
	public Vector2 getPosition() {
		return this.position;
	}
	
	public Vector2 getSize() {
		return this.size;
	}
	
	public void setCollisions(String[] gameObjectClasses) {
		this.collidesWith.clear();
		for (int i = 0; i < gameObjectClasses.length; i++) {
			this.collidesWith.add(gameObjectClasses[i]);
		}
	}
	
	public ArrayList<String> getCollisions() {
		return this.collidesWith;
	}
	
	public boolean overlaps(GameObject other) {
		if (other.getCollision().canCollide() && collidesWith.contains(other.getClass().getName())) {
			boolean noOverlap = this.position.x + this.size.x < other.getCollision().position.x ||
					other.getCollision().position.x + other.getCollision().size.x < this.position.x ||
					this.position.y + this.size.y < other.getCollision().position.y ||
					other.getCollision().position.y + other.getCollision().size.y < this.position.y;
			
			if (!noOverlap) {
				if (!this.overlaps.contains(other)) {
					this.overlaps.add(other);
				}
			}	else {
				if (this.overlaps.contains(other)) {
					this.overlaps.remove(other);
				}
			}		
			return !noOverlap;			
		}
		return false;
	}
	
	public ArrayList<GameObject> getOverlaps() {
		return this.overlaps;
	}
}
