package movement;

import gameObjects.GameObject;

/**
 * 
 * @author James Pagliuca
 * 
 */

public class BackForthMovement extends Movement {

	private double myLeftBound;
	private double myRightBound;

	public BackForthMovement(double l, double r) {
		myLeftBound = l;
		myRightBound = r;
		mySpeed = .2;
	}

	@Override
	public void move(GameObject o) {
		// System.out.println(o.getX());
		o.setVerticalSpeed(0);
		if (o.getHorizontalSpeed() == 0) {
			o.setHorizontalSpeed(mySpeed);
		}
		if (o.getX() >= myRightBound) {
			o.setHorizontalSpeed(-mySpeed);
		} else if (o.getX() <= myLeftBound) {
			o.setHorizontalSpeed(mySpeed);
		}
	}

	public static class BFMovementFactory extends MovementFactory {

		public BFMovementFactory() {
			myName = "BF";
		}

		@Override
		public Movement makeMyMovement(String[] parameters) {
			double leftBound = Double.parseDouble(parameters[1]);
			double rightBound = Double.parseDouble(parameters[2]);
			return new BackForthMovement(leftBound, rightBound);
		}

	}

}
