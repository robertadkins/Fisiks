package shape;
import java.awt.geom.Rectangle2D;

import collision.EdgeCollider;


public class Rectangle extends PhysicalObject {

	public Rectangle(double x, double y, double vx, double vy, double ax, double ay,
			double width, double height, double mass, double coeff, EdgeCollider edgeCollider) {
		super(vx, vy, ax, ay, mass, coeff,
				new Rectangle2D.Double(x, y, width * pixelPerM, height * pixelPerM), edgeCollider);
	}
}
