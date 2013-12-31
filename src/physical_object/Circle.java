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

package physical_object;
import java.awt.geom.Ellipse2D;

import collision.EdgeCollider;

public abstract class Circle extends PhysicalObject {

	public Circle(int x, int y, double vx, double vy, double ax, double ay, double d, double mass, double coeff, EdgeCollider edgeCollider) {
		super(vx, vy, ax, ay, mass, coeff, new Ellipse2D.Double(x, y, d * pixelPerM, d * pixelPerM), edgeCollider);
	}

	public double getRadius() {
		return getWidth() / 2.0;
	}
}
