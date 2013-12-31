package etc;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import shape.PhysicalObject;
import concrete.Atom;


public class World {

	private static World instance = null;
	private Color[] colors = {Color.red, Color.yellow, Color.blue, Color.orange, Color.green};

	private int width;
	private int height;

	private CopyOnWriteArrayList<PhysicalObject> physicalObjects;
	private ScheduledThreadPoolExecutor pool;

	private boolean bounded;

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
		for(PhysicalObject o: physicalObjects) {
			o.setLastTime(System.currentTimeMillis());
			o.future = World.getWorld().getPool().scheduleAtFixedRate(o, 0, 1, TimeUnit.MILLISECONDS);
		}
	}
	
	public void loadObjects(String objectList) {

		Scanner in = null;
		URLConnection con = null;

		try {

			URL url = new URL(objectList);
			con = url.openConnection();
			con.connect();
			in = new Scanner(con.getInputStream());

		} catch (IOException e) {}

		while(in.hasNextLine()) {

			String object = in.nextLine();

			do {

				String trimmed = object.trim();

				if(trimmed.equals("Ball")) {
					physicalObjects.add(new Atom(in.nextInt(), in.nextInt(), in.nextDouble(), in.nextDouble()));
				}

				in.nextLine();
				object = in.nextLine();

			}while(object.length() - object.trim().length() == 1);
		}

		in.close();
	}

	public Image getImage() {
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

		return worldImage;
	}

	public void destroyWorld() {
		instance = null;
	}

	public void destroyObjects() {
		for(int i = physicalObjects.size() - 1; i >= 0; i--) {
			PhysicalObject physicalObject = physicalObjects.get(i);
			if(physicalObject.future != null) {
				physicalObject.future.cancel(true);
				pool.remove(physicalObject);
			}
			physicalObjects.remove(i);
		}
	}
}
