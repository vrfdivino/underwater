package component;

import java.util.ArrayList;
import datatype.Vector2;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import parentclass.GameObject;

/**
 * Manages collisions state between game objects.
 * 
 * @author Dave Jimenez
 */

public class Collision {
	
	/////////////////// PROPERTIES ///////////////////
	
	private boolean can_collide = true;
	private Vector2 position = Vector2.ZERO;
	private Vector2 origin = new Vector2();
	private Vector2 size = new Vector2();
	public Vector2 test_value = new Vector2(100, 0);
	private ArrayList<String> collides_with = new ArrayList<String>();
	private ArrayList<GameObject> overlaps = new ArrayList<GameObject>();
	
	/**
	 * Creates an empty Collision instance.
	 * 
	 * 	@author Dave Jimenez
	 */
	
	public Collision() {}
	
	/**
	 * Creates a new Collision instance.
	 * 
	 * @param can_collide If the collision object can collide.
	 * @param x The x position of the collision object.
	 * @param y The y position of the collision object.
	 * @param width The width of the collision object.
	 * @param height The width of the collision object.
	 * @author Dave Jimenez
	 */
	
	public Collision(boolean can_collide, double x, double y, double width, double height) {
		setCollide(can_collide);
		setOrigin(x, y);
		setSize(width, height);
	}
	
	/**
	 * Creates a new Collision instance.
	 * 
	 * @param can_collide If the collision object can collide.
	 * @param origin The vector position.
	 * @param size The vector size.
	 * @author Dave Jimenez
	 */
	
	public Collision(boolean can_collide, Vector2 origin, Vector2 size) {
		setCollide(can_collide);
		setOrigin(origin);
		setSize(size);
	}
	
	/**
	 * Renders the collision box. For debugging purposes.
	 * 
	 * @param gc The graphics context of the canvas.
	 * @param color The color to render.
	 * @param alpha The opacity.
	 * @author Dave Jimenez
	 */
	
	public void renderCollision(GraphicsContext gc, Color color, double alpha) {
		gc.setFill(color);
		gc.setGlobalAlpha(alpha);
		gc.fillRect(position.x, position.y, size.x, size.y);
		gc.setGlobalAlpha(1.0);
	}
	
	/**
	 * Renders the collision box. For debugging purposes.
	 * 
	 * @param gc The graphics context of the canvas.
	 * @param color The color to render.
	 * @param alpha The opacity.
	 * @author Dave Jimenez
	 */
	public void renderCollision(GraphicsContext gc) {
		gc.setFill(Color.RED);
		gc.setGlobalAlpha(0.5);
		gc.fillRect(position.x, position.y, size.x, size.y);
		gc.setGlobalAlpha(1.0);
	}
	
	/**
	 * Checks if collision overlaps with another collision object.
	 * 
	 * @param other The other game object
	 * @return boolean
	 * @author Dave Jimenez
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
				if (!overlaps.contains(other)) overlaps.add(other);
			} else {
				//Remove Collision object from list of overlapping Collision objects
				if (overlaps.contains(other)) overlaps.remove(other);
			}
			return !noOverlap;			
		}
		return false;
	}
	
	/**
	 * Remove the object which overlaps.
	 * 
	 * @param other
	 * @author Dave Jimenez
	 */
	
	public void removeOverlap(GameObject other) {
		if (overlaps.contains(other)) overlaps.remove(other);
	}
	
	/////////////////// GETTERS ///////////////////

	public boolean canCollide() {return can_collide;}
	public Vector2 getPosition() {return position;}
	public Vector2 getOrigin() {return origin;}
	public Vector2 getSize() {return size;}
	public ArrayList<GameObject> getOverlaps() {return overlaps;}
	public ArrayList<String> getCollisions() {return collides_with;}
	public boolean isColliding() {
		if (!overlaps.isEmpty()) return true;
		return false;
	}
	
	/////////////////// SETTERS ///////////////////
	
	public void setCollide(boolean can_collide) {this.can_collide = can_collide;}
	public void setPosition(double x, double y) {this.position = new Vector2(x + origin.x, y + origin.y);}
	public void setPosition(Vector2 position) {this.position = new Vector2(position.x + origin.x, position.y + origin.y);}
	public void setOrigin(double x, double y) {this.origin.set(x,y);}
	public void setOrigin(Vector2 origin) {this.origin = origin;}
	public void setSize(double width, double height) {this.size.set(width, height);}
	public void setSize(Vector2 size) {this.size.set(size);}
	public void setCollisions(String[] gameObjectClasses) {
		collides_with.clear();
		for (int i = 0; i < gameObjectClasses.length; i++) 
			this.collides_with.add(gameObjectClasses[i]);
	}
}
