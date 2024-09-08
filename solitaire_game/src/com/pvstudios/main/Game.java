package com.pvstudios.main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;

import java.awt.image.BufferStrategy;


import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static int WIDTH = 860, HEIGHT = 640;
	public static boolean DEBUG = false;
	
	private Thread thread;
	private boolean running = false;
	private final int FPS = 60;
    private final long TARGET_TIME = 1000 / FPS;
    
    public static Table table;
    public static JFrame frame;
    public static SpriteSheet spritesheet;

	public Game() {
		
		Main menu = new Main();
		
		this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame = new JFrame("My Window");
		frame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
		frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null); 
		frame.setJMenuBar(menu);
		frame.add(this);
		frame.setVisible(true);
		spritesheet = new SpriteSheet(0,0,71,95,6,13,0,3, "/assets/cards.png");
		table = new Table();
		this.addMouseListener(table);
		this.addMouseMotionListener(table);
	}

	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}

	public void update() {
		table.update();
	}

	public void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {			
			this.createBufferStrategy(3);
			return;
		}
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
	    g.clearRect(0, 0, getWidth(), getHeight());
	    
	    table.render(g);
		
		bs.show();
		g.dispose();
	}
	
	public void start() {
        thread = new Thread(this);
        thread.start();
    }

	@Override
	public void run() {
		running = true;
		long start, elapsed, wait;

		while (running) {
			start = System.nanoTime();
			
			update();
			render();

			elapsed = System.nanoTime() - start;
			wait = TARGET_TIME - elapsed / 1000000;
			if (wait < 0) {
				wait = 5;
			}

			try {
				Thread.sleep(wait);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
