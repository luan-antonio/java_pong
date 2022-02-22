package pong;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Graphics;

public class Player {
	private int x = 100;
	private boolean right = false, left = false;
	private static final int WIDTH = 40, HEIGHT = 5;
	private static final Color COLOR = Color.blue;
	
	public Rectangle getBoxCollider() {
		return new Rectangle(x, Game.HEIGHT-5, WIDTH, HEIGHT);
	}
	
	public void update() {
		move();
		collideInEdges();
	}
	
	private void move() {
		if(right) {
			x++;
		} else if(left) {
			x--;
		}
	}
	
	private void collideInEdges() {
		if(x+40 >= Game.WIDTH) {
			x = Game.WIDTH-40;
			stopMoving();
		} else if (x <= 0 ) {
			x = 0;
			stopMoving();
		}
	}
	
	public void render(Graphics g) {
		g.setColor(COLOR);
		g.fillRect(x, Game.HEIGHT-5, WIDTH, HEIGHT);
	}

	public void allowMoveRight() {
		right = true;
		left = false;
		
	}

	public void allowMoveLeft() {
		left = true;
		right = false;
	}
	
	public void stopMoving() {
		right = false;
		left = false;
	}
}
