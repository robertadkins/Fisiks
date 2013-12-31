package concrete;
import shape.Rectangle;
import collision.InelasticEdgeCollider;

public class Block extends Rectangle{

	public Block(double x, double y, double vx, double vy,
			double width, double height, double mass) {
		super(x, y, vx, vy, 0, earthG, width, height, mass, .99, new InelasticEdgeCollider());
	}
}
