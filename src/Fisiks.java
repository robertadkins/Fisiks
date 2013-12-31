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

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import physical_object.PhysicalObject;
import concrete_object.Atom;
import concrete_object.Block;
import etc.World;

public class Fisiks extends JApplet implements Runnable {

	private static final long serialVersionUID = -7854467439567829357L;

	final static int SCREEN_WIDTH = 800;
	final static int SCREEN_HEIGHT = 400;

	private World world;

	private final Container pane = getContentPane();
	private Future<?> future;

	private boolean isPlaying;

	private CardLayout cards;

	// Main menu components
	private JPanel mainMenu;
	private JLabel welcomeLbl;
	private JButton playBtn;
	private JButton ctrlBtn;
	private JButton instBtn;

	// Control submenu components
	private JPanel controlsBG;
	private JButton ctrlbackBtn;

	// Instructions submenu components
	private JPanel instructionsBG;
	private JButton instbackBtn;

	static JPanel simPanel;

	// initialize applet settings
	public void init() {

		setFocusable(true);
		requestFocusInWindow();
		setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
		cards = new CardLayout();
		setLayout(cards);
		future = null;

		world = World.makeWorld(SCREEN_WIDTH, SCREEN_HEIGHT, 40, true);

		isPlaying = false;

		initGUI();
	}

	// start main timer
	public void start() {
		future = world.getPool().scheduleAtFixedRate(this, 0, 1, TimeUnit.MILLISECONDS);
	}

	// stop and remove all threads from pool
	public void stop() {

		if(future != null && !future.isCancelled()) {
			future.cancel(true);
			world.getPool().remove(this);
		}

		world.destroyObjects();
		world.getPool().purge();
		world.getPool().shutdownNow();
		world.destroyWorld();
	}

	// close pool and double buffer graphics
	public void destroy() {

	}

	// load Physical Objects
	private void initObjects() {
		//PhysicalObjectManager.loadObjects("https://dl.dropboxusercontent.com/u/6778782/Fisiks/Test.txt");

		//for(int x = 10; x < SCREEN_WIDTH - 10; x += 200) {
			for(int y = 10; y < SCREEN_HEIGHT - 10; y += 70) {
				Block block = new Block(600, y, 0, 0, Math.random() * .4 + .2, Math.random() * .5 + .1, 10);
				world.add(block);
				this.addMouseListener(block);
				this.addMouseMotionListener(block);
			}
		//}

		//world.add(new BouncyBall(20, 100, 10, 0));

		/*world.add(new Block(399, 50, 0, 0, 1 * PhysicalObject.mPerPixel, 350 * PhysicalObject.mPerPixel, 1000000));

		for(int x = 100; x < SCREEN_WIDTH / 2; x += 30) {
			for(int y = 10; y < SCREEN_HEIGHT - 10; y += 30) {
				world.add(new Atom(x, y, Math.random() * 5 - 2, Math.random() * 5 - 2));
			}
		}*/
	}

	// initialize GUI components
	private void initGUI() {

		mainMenu = new JPanel();
		controlsBG = new JPanel();
		instructionsBG = new JPanel();

		mainMenu.setDoubleBuffered(true);
		controlsBG.setDoubleBuffered(true);
		instructionsBG.setDoubleBuffered(true);

		mainMenu.setBackground(Color.black);
		mainMenu.setLayout(null);

		welcomeLbl = new JLabel("Fisiks Simulator");
		welcomeLbl.setFont(new Font("Comic Sans MS", Font.BOLD, 27));
		welcomeLbl.setForeground(Color.white);
		welcomeLbl.setBounds(275, 50, 1000, 50);

		playBtn = new JButton("Play");
		playBtn.setBackground(Color.white);
		playBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 27));

		ctrlBtn = new JButton("Controls");
		ctrlBtn.setBackground(Color.white);
		ctrlBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 27));

		instBtn = new JButton("Instructions");
		instBtn.setBackground(Color.white);
		instBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 27));

		ctrlbackBtn = new JButton("Back");
		ctrlbackBtn.setBackground(Color.white);
		ctrlbackBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 27));

		instbackBtn = new JButton("Back");
		instbackBtn.setBackground(Color.white);
		instbackBtn.setFont(new Font("Comic Sans MS", Font.PLAIN, 27));

		playBtn.setBounds(350, 170, 100, 50);
		ctrlBtn.setBounds(470, 170, 200, 50);
		instBtn.setBounds(130, 170, 200, 50);
		ctrlbackBtn.setBounds(570, 300, 100, 50);
		instbackBtn.setBounds(570, 300, 100, 50);

		MenuListener ml = new MenuListener();

		playBtn.addActionListener(ml);
		ctrlBtn.addActionListener(ml);
		instBtn.addActionListener(ml);
		ctrlbackBtn.addActionListener(ml);
		instbackBtn.addActionListener(ml);

		mainMenu.add(welcomeLbl);
		mainMenu.add(playBtn);
		mainMenu.add(ctrlBtn);
		mainMenu.add(instBtn);

		controlsBG.setLayout(null);
		controlsBG.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		controlsBG.add(ctrlbackBtn);

		instructionsBG.setLayout(null);
		instructionsBG.setBounds(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
		instructionsBG.add(instbackBtn);

		simPanel = new JPanel();

		add(mainMenu, "Main Menu");
		add(controlsBG, "Controls");
		add(simPanel, "Game");
		add(instructionsBG, "Instructions");

		cards.show(pane, "Main Menu");
	}

	// do appropriate task
	public void run() {

		if(isPlaying) {
			((Graphics2D)simPanel.getGraphics()).drawImage(world.getImage(), 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, 0, 0, SCREEN_WIDTH, SCREEN_HEIGHT, pane);
		}
		else {
			cards.show(pane, "Main Menu");
		}
	}

	// handle GUI events
	private class MenuListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			if(e.getActionCommand().equals(playBtn.getActionCommand())) {

				initObjects();
				world.play();

				isPlaying = true;
				cards.show(pane, "Game");
			}
			else if(e.getActionCommand().equals(ctrlBtn.getActionCommand())) {
				cards.show(pane, "Controls");
			}
			else if(e.getActionCommand().equals(instBtn.getActionCommand())) {
				cards.show(pane, "Instructions");
			}
			else if(e.getActionCommand().equals(instbackBtn.getActionCommand()) || e.getActionCommand().equals(ctrlbackBtn.getActionCommand())) {
				cards.show(pane, "Main Menu");
			}
		}	
	}
}
