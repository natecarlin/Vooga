package gameObjects;

import java.util.ArrayList;

import gameObjects.Player;

import levelLoadSave.ForSave;
import weapons.Weapon;

import com.golden.gamedev.Game;

@ForSave
public class HorizontalShip extends Player {

	protected int myHozSpeed;

	protected ArrayList<Weapon> myWeapons;

	public HorizontalShip(double x, double y, String imgPath) {
		super(x, y, imgPath);
		/*
		 * Sets the default speed and health users can later use the set methods
		 * to alter speed and health
		 */

		myHozSpeed = 3;

		// default ship will be set with generic AmmoGun
		myWeapons = new ArrayList<Weapon>();
		myType = "HorizontalShip";

	}

	public void move(Game g, int width, int height) {

		if (this.getX() > 0 && g.keyDown(java.awt.event.KeyEvent.VK_J)) {
			this.moveX(-myHozSpeed);
		}

		if (this.getX() < width - this.getWidth()
				&& g.keyDown(java.awt.event.KeyEvent.VK_L)) {
			this.moveX(myHozSpeed);
		}
	}

	public void setHozSpeed(int i) {
		myHozSpeed = i;
	}

	@Override
	public GameObject makeGameObject(GameObjectData god) {
		Double x = god.getX();
		Double y = god.getY();
		String imgPath = god.getImgPath();
		return new HorizontalShip(x, y, imgPath);

	}

	/**
	 * HorizontalShip() and getFactory() must be implemented by each game
	 * object; they are used for the factory system.
	 */
	protected HorizontalShip() {
		super();
		myType = "HorizontalShip";
		System.out.println(myType);
	}

	public static GameObjectFactory getFactory() {
		return new GameObjectFactory(new HorizontalShip());
	}

}