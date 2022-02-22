package pong;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class Ball {
	private double x = (Game.WIDTH/2) - 5, y = (Game.HEIGHT/2) - 5, speed = 1.8, dx, dy;
	private int width = 5, height = 5;
	private static final Color COLOR = Color.gray;
	private Game game;
	
	public Ball(Game game) {
		
		calculateBallAngle();
		this.game = game;
	}
	
	public void calculateBallAngle() {
		int angle = new Random().nextInt(120 - 45) + 46;
		dx = Math.cos(Math.toRadians(angle));
		dy = Math.sin(Math.toRadians(angle));
	}
	
	public void update(Rectangle playerCollider, Rectangle enemyCollider) {
		move();
		collideInEdges();
		collideInEntities(playerCollider, enemyCollider);
	}
	
	public void move() {
		x+=dx*speed;
		y+=dy*speed;
	}
	
	public void resetBallPosition() {
		dx = new Random().nextGaussian();
		dy = new Random().nextGaussian();
		x = (Game.WIDTH/2) - 5;
		y = (Game.HEIGHT/2) - 5;
	}
	
	private void collideInEdges() {
		if(x+dx*speed+(width/2) >= Game.WIDTH || x+dx*speed+(width/2) < 0 ) {
			dx*=-1;
		}
		if(y >= Game.HEIGHT) {
			game.updateScore(0);
			resetBallPosition();
		} else if(y < 0) {
			game.updateScore(1);
			resetBallPosition();
		}
	}
	
	private void collideInEntities(Rectangle playerCollider, Rectangle enemyCollider) {
		Rectangle boxCollider = new Rectangle((int)x, (int)y, width, height);
		if(boxCollider.intersects(playerCollider)) {
			calculateBallAngle();
			dy*=-1;
		}
		if(boxCollider.intersects(enemyCollider)) {
			calculateBallAngle();
			if(dy < 0) {
				dy*=-1;				
			}
		}
	}
	
	public double getX() {
		return x;
	}
	
	public void render(Graphics g) {
		g.setColor(COLOR);
		g.fillRect((int)x, (int)y, width, height);
	}
}
