package datatype;

public class Vector2 {
	public static final Vector2 LEFT = new Vector2(-1, 0);
	public static final Vector2 RIGHT = new Vector2(1, 0);
	public static final Vector2 UP = new Vector2(0, -1);
	public static final Vector2 DOWN = new Vector2(0, 1);
	
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
	
	public void setAngle(double angleDegrees) {
		double L = this.getLength();
		double angleRadians = Math.toRadians(angleDegrees);
		this.x = L * Math.cos(angleRadians);
		this.y = L * Math.sin(angleRadians);
	}
	
	public Vector2 normalize() {
		double length = this.getLength();
		
		if (length == 0) {
			return new Vector2(0, 0);
		}
		return new Vector2(this.x/length, this.y/length);
	}
}