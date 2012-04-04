package levelEditor;

import gameObjects.GameObjectData;

import com.golden.gamedev.object.Sprite;



public class EnemySprite extends Sprites {
	 int enemyCount=1;
	 private final String myType= "Enemy";
	 private final String imagePath = "resources/enemy.png";

	public EnemySprite(String pngPath, int x, int y) {
		super(pngPath,x,y);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void askQuestions() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public int newID() {
		enemyCount++;
		return enemyCount;
	}
	@Override
	public GameObjectData makeGameObject(Sprite spr) {
		GameObjectData god = new GameObjectData(myType);
		god.setX(spr.getX());
		god.setY(spr.getY());
		god.setImgPath(imagePath);
		return god;
	}
	public static class Factory extends Sprites.Factory{
        
		private final String imagePath = "resources/enemy.png";
		private final int startX = 230;
		private final int startY = 629;
		
		@Override
		public boolean isType(int id) {
			return id<101;
		}

		@Override
		public Sprites makeSprite() {
			// TODO Auto-generated method stub
			return new EnemySprite(imagePath, startX, startY);
		}




		
	}
	
	

}
