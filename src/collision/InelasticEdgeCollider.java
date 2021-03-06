/*
 *The MIT License (MIT)
 * 
 *Copyright (c) 2013 Robert Adkins
 *
 *Permission is hereby granted, free of charge, to any person obtaining a copy
 *of this software and associated documentation files (the "Software"), to deal
 *in the Software without restriction, including without limitation the rights
 *to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *copies of the Software, and to permit persons to whom the Software is
 *furnished to do so, subject to the following conditions:
 *
 *The above copyright notice and this permission notice shall be included in
 *all copies or substantial portions of the Software.
 *
 *THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *THE SOFTWARE.
 */

package collision;

import physical_object.PhysicalObject;
import world.World;

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
