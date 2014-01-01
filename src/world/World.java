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

package world;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import physical_object.PhysicalObject;


public class World extends JPanel implements Runnable {

	private static final long serialVersionUID = 6538520320325439153L;

	private Future<?> future;

	private static World instance = null;
	private Color[] colors = {Color.red, Color.yellow, Color.blue, Color.orange, Color.green};

	private int width;
	private int height;

	private CopyOnWriteArrayList<PhysicalObject> physicalObjects;
	private ScheduledThreadPoolExecutor pool;

	private boolean bounded;
	private boolean paused;

	private Graphics2D graphics;
	private Image worldImage;

	protected World(int width, int height, int coreSize, boolean bounded) {
		this.physicalObjects = new CopyOnWriteArrayList<PhysicalObject>();
		this.width = width;
		this.height = height;
		this.pool = new ScheduledThreadPoolExecutor(coreSize);
		this.pool.setMaximumPoolSize(Integer.MAX_VALUE);
		this.bounded = bounded;
		this.worldImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		this.graphics = (Graphics2D)worldImage.getGraphics();
		this.future = null;
		this.paused = true;

		setSize(width, height);
		setLayout(null);

		graphics.setBackground(Color.black);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	}

	public static World makeWorld(int width, int height, int coreSize, boolean bounded) {
		if(instance == null) {
			instance = new World(width, height, coreSize, bounded);
		}
		return instance;
	}

	public static World getWorld() {
		return instance;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public ScheduledThreadPoolExecutor getPool() {
		return pool;
	}

	public void add(PhysicalObject object) {
		physicalObjects.add(object);
	}

	public int numObjects() {
		return physicalObjects.size();
	}

	public boolean isBounded() {
		return bounded;
	}

	public CopyOnWriteArrayList<PhysicalObject> getPhysicalObjects() {
		return physicalObjects;
	}

	public void play() {

		if(!paused) {
			return;
		}
		
		for(PhysicalObject o: physicalObjects) {
			o.setLastTime(System.currentTimeMillis());
			o.future = World.getWorld().getPool().scheduleAtFixedRate(o, 0, 1, TimeUnit.MILLISECONDS);
		}

		if(future == null) {
			future = pool.scheduleAtFixedRate(this, 0, 1, TimeUnit.MILLISECONDS);
		}
		
		paused = false;
	}

	public void pause() {

		if(paused) {
			return;
		}
		
		for(int i = physicalObjects.size() - 1; i >= 0; i--) {
			PhysicalObject physicalObject = physicalObjects.get(i);
			if(physicalObject.future != null) {
				physicalObject.future.cancel(true);
				pool.remove(physicalObject);
			}
		}

		pool.purge();
		paused = true;
	}

	public void paint(Graphics g) {
		// clear old stuff
		graphics.clearRect(0, 0, width, height);

		// draw physical objects with updated positions {
		for(PhysicalObject physicalObject: physicalObjects) {
			graphics.setColor(colors[physicalObject.getID() % colors.length]);
			graphics.fill(physicalObject.body);
			//graphics.setColor(Color.green);
			graphics.draw(physicalObject.body);
			//graphics.drawString("VX: " + physicalObject.getVX() + ", VY: " + physicalObject.getVY(), (int)physicalObject.getX(), (int)(physicalObject.getY()));
		}

		g.drawImage(worldImage, 0, 0, width, height, 0, 0, width, height, this);
	}

	public void destroy() {

		if(future != null && !future.isCancelled()) {
			future.cancel(true);
			pool.remove(this);
		}

		for(int i = physicalObjects.size() - 1; i >= 0; i--) {
			PhysicalObject physicalObject = physicalObjects.get(i);
			if(physicalObject.future != null) {
				physicalObject.future.cancel(true);
				pool.remove(physicalObject);
			}

			physicalObjects.remove(i);
		}

		pool.purge();
		pool.shutdownNow();
		instance = null;
	}

	public void run() {
		repaint();
	}
}
