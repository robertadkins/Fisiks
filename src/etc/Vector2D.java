package etc;
import java.awt.geom.Point2D;


public class Vector2D {

	public double x;
	public double y;
	
	public Vector2D() {
		x = 0;
		y = 0;
	}
	
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D(Point2D one, Point2D two) {
		this.x = two.getX() - one.getX();
		this.y = two.getY() - one.getY();
	}
	
	public Vector2D(Vector2D other) {
		this.x = other.x;
		this.y = other.y;
	}
	
	public Vector2D add(Vector2D other) {
		return new Vector2D(this.x + other.x, this.y + other.y);
	}
	
	public Vector2D subtract(Vector2D other) {
		return add(other.multiply(-1));
	}
	
	public double dotProduct(Vector2D other) {
		return this.x * other.x + this.y * other.y;
	}
	
	public Vector2D multiply(double scalor) {
		return new Vector2D(this.x * scalor, this.y * scalor);
	}
	
	public Vector2D divide(double scalor) {
		return multiply(1.0 / scalor);
	}
	
	public double slope() {
		return y / x;
	}
	
	public double magnitude() {
		return Math.sqrt(x * x + y * y);
	}
	
	public Vector2D getPerpendicular() {
		return new Vector2D(y, x * -1);
	}
	
	public Vector2D scaleTo(double magnitude) {
		return getUnit().multiply(magnitude);
	}
	
	public Vector2D getUnit() {
		return divide(magnitude());
	}
	
	public Vector2D reflectAcross(Vector2D axis) {
		return new Vector2D();
	}
}
