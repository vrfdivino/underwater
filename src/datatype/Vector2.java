package datatype;

public class Vector2 {
	public static final Vector2 LEFT = new Vector2(-1, 0);
	public static final Vector2 RIGHT = new Vector2(1, 0);
	public static final Vector2 UP = new Vector2(0, -1);
	public static final Vector2 DOWN = new Vector2(0, 1);
	public static final Vector2 ZERO = new Vector2(0, 0);
	
	public double x;
	public double y;
	
	public Vector2() {
		this.set(0, 0);
	}
	
	public Vector2(double x, double y){
		this.set(x, y);
	}
	
	public void set(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public void set(Vector2 vector2) {
		this.x = vector2.x;
		this.y = vector2.y;
	}
	
	public void add(double dx, double dy) {
		this.x += dx;
		this.y += dy;
	}
	
	public void add(Vector2 dvector2) {
		this.x += dvector2.x;
		this.y += dvector2.y;
	}
	
	public Vector2 multiply(double m) {
		x = this.x * m;
		y = this.y * m;
		
		return new Vector2(x, y);
	}
	
	public void setLength(double L) {
		double currentLength = this.getLength();
		
		if (currentLength == 0) {
			this.set(L, 0);						//Set length to L
		}
		else {
			this.multiply(1/currentLength);		//Set length to 1
			this.multiply(L);  					//Set length to L
		}
	}
	
	public double getLength() {
		return Math.sqrt(this.x * this.x + this.y * this.y);
	}
	
	public double getAngle() {
		return Math.toDegrees(Math.atan2(this.x, this.y));
	}
	
	/**
	 * Gets the current length of the Vector2 and sets it at the specified angle in degrees
	 * @param angleDegrees double
	 * @author Dave
	 */
	public void setAngle(double angleDegrees) {
		double L = this.getLength();
		double angleRadians = Math.toRadians(angleDegrees);
		this.x = L * Math.cos(angleRadians);
		this.y = L * Math.sin(angleRadians);
	}
	
	/**
	 * Returns the normalized value of the Vector2. Primarily used for setting directions
	 * @return ( Vector2 ) Normalized Vector2
	 * @author Dave
	 */
	public Vector2 normalize() {
		double length = this.getLength();
		
		if (length == 0) {
			return new Vector2(0, 0);
		}
		return new Vector2(this.x/length, this.y/length);
	}
	
	/**
	 * Moves the vector from the specified current position towards the target position at delta increments. 
	 * Primarily used for getting smooth movements when paired with speed and acceleration.
	 * @param current_pos Vector2
	 * @param target_pos Vector2
	 * @param delta double
	 * @return ( Vector2 ) Next vector2 value towards target position 
	 * @author Dave
	 */
	public Vector2 moveTowards(Vector2 current_pos, Vector2 target_pos, double delta) {
		Vector2 origin_pos = new Vector2(target_pos.x - current_pos.x, target_pos.y - current_pos.y);
		Vector2 final_pos = new Vector2();
		Vector2 direction = origin_pos.normalize();
		
		//Check if next delta increment will overshoot. If overshoot then the final position will be the target position.
		if (direction.x > 0) {
			if (direction.y > 0) {
				if (current_pos.x + (delta * direction.x) <= target_pos.x && current_pos.y + (delta * direction.y) <= target_pos.y) {
					final_pos = new Vector2(current_pos.x + (delta * direction.x), current_pos.y + (delta * direction.y));
				} else {
					final_pos = target_pos;
				}
			}
			else {
				if (current_pos.x + (delta * direction.x) <= target_pos.x && current_pos.y + (delta * direction.y) >= target_pos.y) {
					final_pos = new Vector2(current_pos.x + (delta * direction.x), current_pos.y + (delta * direction.y));
				} else {
					final_pos = target_pos;
				}
			}

		} else {
			if (direction.y > 0) {
				if (current_pos.x + (delta * direction.x) >= target_pos.x && current_pos.y + (delta * direction.y) <= target_pos.y) {
					final_pos = new Vector2(current_pos.x + (delta * direction.x), current_pos.y + (delta * direction.y));
				} else {
					final_pos = target_pos;
				}
			}
			else {
				if (current_pos.x + (delta * direction.x) >= target_pos.x && current_pos.y + (delta * direction.y) >= target_pos.y) {
					final_pos = new Vector2(current_pos.x + (delta * direction.x), current_pos.y + (delta * direction.y));
				} else {
					final_pos = target_pos;
				}
			}
		}

		x = final_pos.x;
		y = final_pos.y;
		return final_pos;
	}
}
