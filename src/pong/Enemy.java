package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Enemy {
	private double x = 100;
	private static final int WIDTH = 40, HEIGHT = 5;
	private static final Color COLOR = Color.red;
	
	public Rectangle getBoxCollider() {
		return new Rectangle((int)x, 0, WIDTH, HEIGHT);
	}
	
	public void update(double ballXCoordinate) {
		move(ballXCoordinate);
		collideInEdges();
	}
	
	public void move(double ballXCoordinate) {
		x+= (ballXCoordinate - x - 6) * 0.07;
	}
	
	private void collideInEdges() {
		if(x+40 >= Game.WIDTH) {
			x = Game.WIDTH-40;
		} else if (x <= 0 ) {
			x = 0;
		}
	}
	
	public void render(Graphics g) {
		g.setColor(COLOR);
		g.fillRect((int)x, 0, WIDTH, HEIGHT);
	}
}
