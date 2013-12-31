package shape;

import collision.EdgeCollider;


public class Square extends Rectangle {

	public Square(double x, double y, double vx, double vy, double ax,
			double ay, double size, double mass, double coeff, EdgeCollider edgeCollider) {
		super(x, y, vx, vy, ax, ay, size, size, mass, coeff, edgeCollider);
	}
}
