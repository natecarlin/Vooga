package innerGameGUI;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.golden.gamedev.Game;
import com.golden.gamedev.GameLoader;

public class GUIExample extends Game{
	KPanel myPanel;
	@Override
	public void initResources() {
		myPanel = new KPanel(this);
		myPanel.add(new KTextBox(this, "Start Game"));
		myPanel.add(new KTextBox(this, "Load/Save"));
		KButton button = new KButton(this, "Confirm");
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent ev) {
				JOptionPane.showMessageDialog(new JFrame(), "Surprise suprise!!");
			}
		});
		myPanel.add(button);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D pen) {
		// TODO Auto-generated method stub
		myPanel.render(pen);
	}

	@Override
	public void update(long elapsedTime) {
		// TODO Auto-generated method stub
		myPanel.update(elapsedTime);
	}
	
	public static void main(String[] arg0)
	{
		GameLoader game = new GameLoader();
		game.setup(new GUIExample(), new Dimension(800, 600), false);
		game.start();
	}
}
