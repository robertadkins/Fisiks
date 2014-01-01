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

package simulation;

import world.World;
import concrete_object.Block;

public class Blocks extends Simulation {
	
	public Blocks() {
		super("Building Blocks");
	}

	@Override
	public void init() {

		World world = World.getWorld();
				
		for(int x = 10; x < World.getWorld().getWidth() - 10; x += 300) {
			for(int y = 10; y < World.getWorld().getHeight() - 10; y += 70) {
				
				Block block = new Block(x + Math.random() * 21 - 10, y, 0, 0, Math.random() * .4 + .2, Math.random() * .5 + .1, 10);
				
				world.add(block);
				world.addMouseListener(block);
				world.addMouseMotionListener(block);
			}
		}

	}
}
