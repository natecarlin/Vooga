package gameObjects;

import java.util.ArrayList;

import states.FullHealthState;
import states.HalfHealthState;
import states.State;
import movement.BackForthMovement;
import movement.Movement;

/**
 * 
 * @author James Pagliuca
 * 
 */
public class Enemy extends GameObject {

	private ArrayList<State> possibleStates;
	private State currentState;
	private int currentHealth;

	public Enemy(double x, double y, String imgPath, ArrayList<State> states) {
		myX = x;
		myY = y;
		myImgPath = imgPath;
		myType = "Enemy";
		setLocation(myX, myY);
		possibleStates = states;
		State fullHealthState = new FullHealthState(this, new BackForthMovement(
				getX(), getX() + 200, .2));
		State halfHealthState = new HalfHealthState(this, new BackForthMovement(
				getX(), getX() + 200, 1));
		possibleStates.add(fullHealthState);
		possibleStates.add(halfHealthState);
		currentState = fullHealthState;
		currentHealth = 500;
	}

	@Override
	public String getImgPath() {
		return myImgPath;
	}

	public void move() {
		currentState.move();
		currentHealth--;
	}

	public void update() {
		move();
		checkState();
	}
	
	public void checkState(){
		for (State s : possibleStates){
			if (s.shouldBeCurrentState()){
				currentState = s;
			}
		}
	}

	public int getCurrentHealth() {
		return currentHealth;
	}
	
	public void sustainDamage(int damage){
		currentHealth -= damage;
	}

	@Override
	public GameObject makeGameObject(GameObjectData god) {
		Double x = god.getX();
		Double y = god.getY();
		String imgPath = god.getImgPath();
		ArrayList<State> states = new ArrayList<State>(); 
		return new Enemy(x, y, imgPath, states);
	}

	/**
	 * Enemy() and getFactory() must be implemented by each game object; they
	 * are used for the factory system.
	 */
	private Enemy() {
		myType = "Enemy";
	}

	public static GameObjectFactory getFactory() {
		return new GameObjectFactory(new Enemy());
	}

}
