package shape;
import java.awt.geom.Ellipse2D;

import collision.EdgeCollider;

public class Circle extends PhysicalObject {

	public Circle(int x, int y, double vx, double vy, double ax, double ay, double d, double mass, double coeff, EdgeCollider edgeCollider) {
		super(vx, vy, ax, ay, mass, coeff, new Ellipse2D.Double(x, y, d * pixelPerM, d * pixelPerM), edgeCollider);
	}

	public double getRadius() {
		return getWidth() / 2.0;
	}
}
