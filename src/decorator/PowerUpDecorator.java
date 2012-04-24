package decorator;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.Sprite;

public abstract class PowerUpDecorator implements PowerUp{
	
	protected SpaceShip decoratedSpaceShip;
	
	public PowerUpDecorator (SpaceShip decoratedSpaceShip){
		this.decoratedSpaceShip = decoratedSpaceShip;
	}
	
	public PowerUpDecorator(SpaceShip decoratedSpaceShip, Sprite t){
		this.decoratedSpaceShip = decoratedSpaceShip;
	}
	

	@Override
	public void powerUp(Game g, Sprite t) {
		
	}

	@Override
	public String getDescription() {
		return "PowerUp Decorator";
	}


}
