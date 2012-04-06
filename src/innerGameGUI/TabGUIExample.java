package innerGameGUI;

import gameObjects.GameObject;
import gameObjects.Player;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;

public class TabGUIExample extends Game {
	private TabGUI tab1;
	
	@Override
	public void initResources() {
		// TODO Auto-generated method stub
		List<GameObject> gob = new ArrayList<GameObject>();
		for(int i = 1; i < 9; i++)
		{
			Player p = new Player(0, 0, "");
			p.setImage(getImage("resources/ship" + i + ".png"));
			gob.add(p);
		}
		
		tab1 = new TabGUI(this, gob, "ships", 0, 0);
	}

	@Override
	public void render(Graphics2D pen) {
		// TODO Auto-generated method stub
		tab1.render(pen);
	}

	@Override
	public void update(long elapsedTime) {
		// TODO Auto-generated method stub
		tab1.update(elapsedTime);
		
	}
	
	public static void main(String[] arg0)
	{
		GameLoader game = new GameLoader();
		game.setup(new TabGUIExample(), new Dimension(800, 600), false);
		game.start();
	}
	
}
