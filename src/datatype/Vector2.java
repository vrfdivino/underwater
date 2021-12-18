package datatype;

/**
 * A 2 dimensional vector data type.
 * 
 * @author Dave Jimenez
 */

public class Vector2 {
	
	public static final Vector2 LEFT = new Vector2(-1, 0);
	public static final Vector2 RIGHT = new Vector2(1, 0);
	public static final Vector2 UP = new Vector2(0, -1);
	public static final Vector2 DOWN = new Vector2(0, 1);
	public static final Vector2 ZERO = new Vector2(0, 0);
	public double x;
	public double y;
	
	/**
	 * Create new Vector2 object.
	 * 
	 * @author Dave Jimenez
	 */
	
	public Vector2() {
		set(0, 0);
	}
	
	/**
	 * Create new Vector2 object.
	 * 
	 * @author Dave Jimenez
	 */
	
	public Vector2(double x, double y){
		set(x, y);
	}
	
	/**
	 * Set the x and y position.
	 * 
	 * @param x The x coordinate.
	 * @param y The y coordinate.
	 * @author Dave Jimenez
	 */
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Set the x and y position.
	 * 
	 * @param vector The vector position
	 * @author Dave Jimenez
	 */
	
	public void set(Vector2 vector2) {
		this.x = vector2.x;
		this.y = vector2.y;
	}
	
	/**
	 * Increments the vector position.
	 * 
	 * @param dx Change in x.
	 * @param dy Change in y.
	 * @author Dave Jimenez
	 */
	
	public void add(double dx, double dy) {
		x += dx;
		y += dy;
	}
	
	/**
	 * Increments the vector position.
	 * 
	 * @param dvector2 Change in vector position.
	 * @author Dave Jimenez
	 */
	
	public void add(Vector2 dvector2) {
		x += dvector2.x;
		y += dvector2.y;
	}
	
	/**
	 * Increments the vector position by a factor.
	 * 
	 * @param m The factor to increment.
	 * @return Vector2
	 * 	 * @author Dave Jimenez
	 */
	
	public Vector2 multiply(double m) {
		x = x * m;
		y = y * m;
		return new Vector2(x, y);
	}
	
	/**
	 * Set the length of the vector object.
	 * 
	 * @param L The length to set.
	 * @author Dave Jimenez
	 */
	
	public void setLength(double L) {
		double _currentLength = this.getLength();
		
		if (_currentLength == 0) {
			set(L, 0);						//Set length to L
		}
		else {
			multiply(1/_currentLength);		//Set length to 1
			multiply(L);  					//Set length to L
		}
	}
	
	/**
	 * Get the length of the vector object.
	 * 
	 * @author Dave Jimenez
	 */
	
	public double getLength() {
		return Math.sqrt(x * x + y * y);
	}
	
	/**
	 * Get the angle of the vector object.
	 * 
	 * @author Dave Jimenez
	 */
	
	public double getAngle() {
		return Math.toDegrees(Math.atan2(x, y));
	}
	
	/**
	 * Gets the current length of the Vector2 and sets it at the specified angle in degrees.
	 * 
	 * @param angleDegrees double
	 * @author Dave Jimenez
	 */
	
	public void setAngle(double angleDegrees) {
		double _L = getLength();
		double _angleRadians = Math.toRadians(angleDegrees);
		this.x = _L * Math.cos(_angleRadians);
		this.y = _L * Math.sin(_angleRadians);
	}
	
	/**
	 * Returns the normalized value of the Vector2. 
	 * Primarily used for setting directions.
	 * 
	 * @return Vector2 Normalized vector
	 * @author Dave Jimenez
	 */
	
	public Vector2 normalize() {
		double _length = getLength();
		if (_length == 0) return new Vector2(0, 0);
		return new Vector2(x/_length, y/_length);
	}
	
	/**
	 * Moves the vector from the specified current position towards the target position at delta increments. 
	 * Primarily used for getting smooth movements when paired with speed and acceleration.
	 * 
	 * @param current_pos Vector2
	 * @param target_pos Vector2
	 * @param delta double
	 * @return ( Vector2 ) Next Vector2 value towards target position 
	 * @author Dave Jimenez
	 */
	
	public Vector2 moveTowards(Vector2 current_pos, Vector2 target_pos, double delta) {
		Vector2 _origin_pos = new Vector2(target_pos.x - current_pos.x, target_pos.y - current_pos.y);
		Vector2 _final_pos = new Vector2();
		Vector2 _direction = _origin_pos.normalize();
		//Check if next delta increment will overshoot. If overshoot then the final position will be the target position.
		if (_direction.x > 0) {
			if (_direction.y > 0) {
				if (current_pos.x + (delta * _direction.x) <= target_pos.x && current_pos.y + (delta * _direction.y) <= target_pos.y) {
					_final_pos = new Vector2(current_pos.x + (delta * _direction.x), current_pos.y + (delta * _direction.y));
				} else {
					_final_pos = target_pos;
				}
			}
			else {
				if (current_pos.x + (delta * _direction.x) <= target_pos.x && current_pos.y + (delta * _direction.y) >= target_pos.y) {
					_final_pos = new Vector2(current_pos.x + (delta * _direction.x), current_pos.y + (delta * _direction.y));
				} else {
					_final_pos = target_pos;
				}
			}

		} else {
			if (_direction.y > 0) {
				if (current_pos.x + (delta * _direction.x) >= target_pos.x && current_pos.y + (delta * _direction.y) <= target_pos.y) {
					_final_pos = new Vector2(current_pos.x + (delta * _direction.x), current_pos.y + (delta * _direction.y));
				} else {
					_final_pos = target_pos;
				}
			}
			else {
				if (current_pos.x + (delta * _direction.x) >= target_pos.x && current_pos.y + (delta * _direction.y) >= target_pos.y) {
					_final_pos = new Vector2(current_pos.x + (delta * _direction.x), current_pos.y + (delta * _direction.y));
				} else {
					_final_pos = target_pos;
				}
			}
		}
		x = _final_pos.x;
		y = _final_pos.y;
		return _final_pos;
	}
}
