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
	
	/**
	 * Creates a new Collision instance
	 * @param can_collide ( boolean )
	 * @param x ( double ) x-coordinate of origin
	 * @param y ( double ) y-coordinate of origin
	 * @param width ( double )
	 * @param height ( double )
	 * @author Dave
	 */
	public Collision(boolean can_collide, double x, double y, double width, double height) {
		this.setCollide(can_collide);
		this.setOrigin(x, y);
		this.setSize(width, height);
	}
	
	/**
	 * Creates a new Collision instance
	 * @param can_collide ( boolean )
	 * @param origin ( Vector2 )
	 * @param size ( Vector2 )
	 * @author Dave
	 */
	public Collision(boolean can_collide, Vector2 origin, Vector2 size) {
		this.setCollide(can_collide);
		this.setOrigin(origin);
		this.setSize(size);
	}
	
	/**
	 * Creates an empty Collision instance
	 */
	public Collision() {}
	
	//Setters
	/**
	 * Sets collision masks. Defines which GameObjects to collide with.
	 * @param gameObjectClasses ( String[] ) Use ClassName.getName() as input
	 * @author Dave
	 */
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
	
	/**
	 * Renders the collision box. For debugging purposes.
	 * @param gc ( GraphicsContext )
	 * @param color ( Color )
	 * @param alpha ( double )
	 * @author Dave
	 */
	public void renderCollision(GraphicsContext gc, Color color, double alpha) {
		gc.setFill(color);
		gc.setGlobalAlpha(alpha);
		gc.fillRect(position.x, position.y, size.x, size.y);
		
		gc.setGlobalAlpha(1.0);
	}
	/**
	 * Renders the collision box. For debugging purposes.
	 * @param gc ( GraphicsContext )
	 * @author Dave
	 */
	public void renderCollision(GraphicsContext gc) {
		gc.setFill(Color.RED);
		gc.setGlobalAlpha(0.5);
		gc.fillRect(position.x, position.y, size.x, size.y);
		
		gc.setGlobalAlpha(1.0);
		
	}
	
	//Getters
	/**
	 * Checks if collision overlaps with another collision object
	 * @param other ( GameObject )
	 * @return ( boolean )
	 * @author Dave
	 */
	public boolean overlaps(GameObject other) {
		
		if (can_collide && other.getCollision().canCollide() && collides_with.contains(other.getClass().getName())) {
			//Checks if there is no Overlap with the other collision object
			boolean noOverlap = position.x + size.x < other.getCollision().position.x ||
					other.getCollision().position.x + other.getCollision().size.x < position.x ||
					position.y + size.y < other.getCollision().position.y ||
					other.getCollision().position.y + other.getCollision().size.y < position.y;
			
			if (!noOverlap) {
				//Add Collision object to list of overlapping Collision objects
				if (!overlaps.contains(other)) {
					overlaps.add(other);
				}
			}	else {
				//Remove Collision object from list of overlapping Collision objects
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
	
	/**
	 * Returns the overlapping Collision objects.
	 * @return ( ArrayList ) ArrayList of GameObject
	 */
	public ArrayList<GameObject> getOverlaps() {
		return overlaps;
	}
	
	/**
	 * Returns the Collision Masks.
	 * @return ( ArrayList ) ArrayList of String
	 */
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

}
