package movement;

import gameObjects.GameObject;

/**
 * 
 * @author James Pagliuca
 * 
 */

public abstract class Movement {

	protected double mySpeed;

	public abstract void move(GameObject o);

}
