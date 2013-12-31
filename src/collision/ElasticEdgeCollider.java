package collision;

import shape.PhysicalObject;
import etc.World;

public class ElasticEdgeCollider implements EdgeCollider {

	@Override
	public void collideWithEdge(PhysicalObject object) {

		int screenWidth = World.getWorld().getWidth();
		int screenHeight = World.getWorld().getHeight();

		// hit right edge
		if(object.getFutureX() + object.getWidth() >= screenWidth) {
			object.setNextVX(-object.getNextVX() * object.restitution);
			object.setFutureX(screenWidth - object.getWidth());
		}
		// hit left edge
		else if(object.getFutureX() < 0) {
			object.setNextVX(-object.getNextVX() * object.restitution);
			object.setFutureX(0);
		}

		// hit bottom edge
		if(object.futureBody.getMaxY() >= screenHeight) {

			if(object.futureBody.getMaxY() == screenHeight) {
				//setNextVX(getNextVX() * .9);
				return;
			}

			double deltay = screenHeight - object.futureBody.getMaxY();
			double toRoot = 2 * object.getAY() * deltay + object.getNextVY() * object.getNextVY();

			// calculate next VY based off of displacement to edge
			// vf^2 - vi^2 = 2ad
			if(toRoot > 0) {
				object.setNextVY(-Math.sqrt(toRoot) * object.restitution);
			}
			else {
				object.setNextVY(0);
			}

			object.setFutureY(screenHeight - object.getHeight());

			// floor to zero b/c of double precision
			if(Math.abs(object.getNextVX()) < PhysicalObject.minVelocity) {
				object.setNextVX(0);
			}
		}
		// hit top edge
		else if(object.getFutureY() < 0) {
			double deltay = -object.getFutureY();
			double toRoot = 2 * object.getAY() * deltay + object.getNextVY() * object.getNextVY();

			//vf^2 - vi^2 = 2ad
			if(toRoot >= 0) {
				object.setNextVY(Math.sqrt(toRoot)- Math.signum(object.getNextVY()) * .8);
			}
			object.setFutureY(0);
		}
	}
}
