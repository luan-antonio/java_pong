package pong;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Scoreboard {
	private int playerPoints = 0, enemyPoints = 0;
	private Color COLOR = Color.yellow;

	public void update() {
		
	}
	
	public void score(int whoIs){
		if(whoIs == 0 ) {
			enemyPoints++;
		} else {
			playerPoints++;
		}
	}
	
	public String getScoreStatus() {
		if(enemyPoints > playerPoints && enemyPoints >= 5) {
			return "Voc� perdeu";
		} else if(playerPoints > enemyPoints && playerPoints >= 5) {
			return "Voc� venceu";
		}
		return "Em andamento";
	}
	
	public void render(Graphics g) {
		g.setColor(COLOR);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		String scoreboardString = playerPoints+" - "+enemyPoints;
		int width = g.getFontMetrics().stringWidth(scoreboardString);
		int height = g.getFontMetrics().getHeight();
		g.drawString(scoreboardString, Game.WIDTH - width, Game.HEIGHT - height);
	}
}
