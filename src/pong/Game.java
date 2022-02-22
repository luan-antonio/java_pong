package pong;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

public class Game extends Canvas implements Runnable, KeyListener{
	
	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 160;
	public static final int HEIGHT = 120;
	public static final int SCALE = 3;
	private Player player;
	private Enemy enemy;
	private Ball ball;
	private Scoreboard scoreboard;
	private boolean gameEnded = false;
	private BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public static void main(String[] args) {
		new Game();
	}
	
	public int getWidth() {
		return WIDTH;
	}
	public int getHeight() {
		return HEIGHT;
	}
	
	private static JFrame initFrame(Game game) {
		JFrame jFrame = new JFrame("Pong");
		jFrame.setResizable(false);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.add(game);
		jFrame.pack();
		jFrame.setVisible(true);
		jFrame.setLocationRelativeTo(null);
		return jFrame;
	}
	
	public void resetGame() {
		player = new Player();
		enemy = new Enemy();
		ball = new Ball(this);
		scoreboard = new Scoreboard();
		gameEnded = false;
	}

	public Game() {
		this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
		JFrame jframe = Game.initFrame(this);
		jframe.addKeyListener(this);
		
		resetGame();
		
		new Thread(this).start();
	}
	
	private BufferStrategy generateBS() {
		if(this.getBufferStrategy() == null) {
			this.createBufferStrategy(3);
		}
		return this.getBufferStrategy();
	}
	
	private void render() {
		String scoreStatus = scoreboard.getScoreStatus();
		BufferStrategy bs = generateBS();
		Graphics g = layer.getGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		switch(scoreStatus) {
			case "Em andamento":
				player.render(g);
				enemy.render(g);
				ball.render(g);
				scoreboard.render(g);
				break;
			case "Você perdeu":
				g.setColor(Color.yellow);
				g.setFont(new Font("Arial", Font.BOLD, 8));
				g.drawString(scoreStatus, WIDTH/3, HEIGHT/2);
				g.drawString("Pressione espaço para jogar de novo", WIDTH/7, HEIGHT/2+30);
				gameEnded = true;
				break;
			case "Você venceu":
				g.setColor(Color.yellow);
				g.setFont(new Font("Arial", Font.BOLD, 8));
				g.drawString(scoreStatus, WIDTH/3, HEIGHT/2);
				g.drawString("Pressione espaço para jogar de novo", WIDTH/7, HEIGHT/2+30);
				gameEnded = true;
				break;
			default: 
				player.render(g);
				enemy.render(g);
				ball.render(g);
				scoreboard.render(g);
				break;
		}
		g = bs.getDrawGraphics();
		g.drawImage(layer, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
		bs.show();
	}
	
	private void update() {
		player.update();
		enemy.update(ball.getX());
		ball.update(player.getBoxCollider(), enemy.getBoxCollider());
		scoreboard.update();
	}
	
	@Override
	public void run() {
		while(true){
			update();
			render();
			try {
				Thread.sleep(1000/60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			player.allowMoveRight();
		} else if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			player.allowMoveLeft();
		} else if(e.getKeyCode() == KeyEvent.VK_SPACE && gameEnded) {
			resetGame();
			return;
		}else {
			player.stopMoving();
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void updateScore(int whoIs) {
		scoreboard.score(whoIs);
	}
}
