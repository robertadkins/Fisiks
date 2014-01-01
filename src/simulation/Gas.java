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
 */package simulation;


import physical_object.PhysicalObject;
import world.World;
import concrete_object.Atom;
import concrete_object.Block;

public class Gas extends Simulation {

	public Gas() {
		super("Gas Diffusion");
	}

	@Override
	public void init() {
		
		World world = World.getWorld();
		
		world.add(new Block(world.getWidth() / 2, world.getHeight() - world.getHeight() * .8, 0, 0, 1 * PhysicalObject.mPerPixel, world.getHeight() * .8 * PhysicalObject.mPerPixel, 1000000));

		for(int x = 100; x < world.getWidth() / 2; x += 30) {
			for(int y = 10; y < world.getHeight() - 10; y += 30) {
				world.add(new Atom(x, y, Math.random() * 5 - 2, Math.random() * 5 - 2));
			}
		}
	}
}
