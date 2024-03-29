package bars;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import com.golden.gamedev.object.font.SystemFont;

import gameObjects.Player;

public class HealthBar{

	int num; 
	Font myFont = new Font("test", Font.BOLD, 15);
	Color c = Color.BLUE; 
	SystemFont myF = new SystemFont(myFont, c); 	
	Player myPlayer; 
	
	public HealthBar(Player myS) {
		myPlayer = myS; 
	}
	
	public void setHealth(int n){
		num = n; 
	}
	
	public void reduceHealth(){
		num--; 
	}
	
	public void render(Graphics2D pen){
		num = myPlayer.getHealth(); 
		myF.drawString(pen, "Health: " +num, 315, 30);
	}
		
}
