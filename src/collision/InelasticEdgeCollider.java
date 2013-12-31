package collision;

import shape.PhysicalObject;
import etc.World;

public class InelasticEdgeCollider implements EdgeCollider {

	@Override
	public void collideWithEdge(PhysicalObject object) {

		int screenWidth = World.getWorld().getWidth();
		int screenHeight = World.getWorld().getHeight();

		// hit right edge
		if(object.getFutureX() + object.getWidth() >= screenWidth) {
			object.setNextVX(0);
			object.setFutureX(screenWidth - object.getWidth());
		}
		// hit left edge
		else if(object.getFutureX() < 0) {
			object.setNextVX(0);
			object.setFutureX(0);
		}

		// hit bottom edge
		if(object.futureBody.getMaxY() >= screenHeight) {

			object.setNextVY(0);
			object.setFutureY(screenHeight - object.getHeight() - 1);
			object.grounded = true;
			// floor to zero b/c of double precision
			if(Math.abs(object.getNextVX()) < PhysicalObject.minVelocity) {
				object.setNextVX(0);
			}
		}
		// hit top edge
		else if(object.getFutureY() < 0) {
			object.setNextVY(0);
			object.setFutureY(0);
		}
	}

}
