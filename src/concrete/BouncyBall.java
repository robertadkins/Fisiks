package concrete;

import shape.Circle;
import collision.ElasticEdgeCollider;

public class BouncyBall extends Circle {

	public BouncyBall(int x, int y, double vx, double vy) {
		super(x, y, vx, vy, 0, earthG, .3, 20, .9, new ElasticEdgeCollider());
	}
}
