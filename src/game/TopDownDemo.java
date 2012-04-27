package game;

import gameObjects.Enemy;
import gameObjects.Player;
import gameObjects.Player;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import weapons.DamagingProjectile;
import weapons.Projectile;
import weapons.ShotPattern;
import weapons.SinglePattern;
import weapons.UnlimitedGun;
import weapons.Weapon;

import levelLoadSave.EnemyLoadObserver;
import levelLoadSave.HorizontalShipLoadObserver;
import levelLoadSave.LevelLoader;
import levelLoadSave.LoadObserver;
import levelLoadSave.PlayerLoadObserver;
import levelLoadSave.SimpleLoadObserver;
import maps.Map;

import bars.HealthBar;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;

import decorator.CompanionDecorator;
import decorator.ConstantlyMoveDecorator;
import decorator.DecoratorManager;
import decorator.HorizontalDecorator;
import decorator.InvisibilityDecorator;
import decorator.PowerUp;
import decorator.PowerUpDecorator;
import decorator.SimplePowerUp;
import decorator.SimpleShip;
import decorator.DecoratedShip;
import decorator.VerticalDecorator;

public class TopDownDemo extends Game {

	private Player myPlayer;
	private Enemy myEnemy;	
	private HealthBar myHealthBar;
	private Weapon myWeapon;
	
	private Player myShip; 
	private Player myCompanion; 

	private DecoratedShip decorations;
	private DecoratedShip decCompanion; 
	private PowerUp decoratedPowerUp;
	
	private PowerUpDecorator myPowerUpDecorator;

	private SpriteGroup myPlayerGroup;
	private SpriteGroup myBarrierGroup;
	private SpriteGroup myEnemyGroup;
	private SpriteGroup myCompanionGroup;
	private SpriteGroup myProjectileGroup;
	private SpriteGroup myEnemyProjectileGroup;
	private Background myBackground;
	private PlayField myPlayfield;
	private PlayerInfo playerInfo;
	private int enemySize;

	private BufferedImage myBackImage;
	private Map myMap;
	private int count = 0;
	private List<LoadObserver> myLoadObservers;

	@Override
	public void initResources() {
		myBarrierGroup = new SpriteGroup("barrier");
		myPlayerGroup = new SpriteGroup("player");
		
		myEnemyGroup = new SpriteGroup("enemy");
		myProjectileGroup = new SpriteGroup("projectile");
		myEnemyProjectileGroup = new SpriteGroup("enemy projectile");
		myCompanionGroup = new SpriteGroup("companion");

		
		// init background using the new Map class
		myBackImage = getImage("resources/BackFinal.png");
		myMap = new Map(myBackImage, getWidth(), getHeight());

		myMap.setSpeed(10);
		myBackground = myMap.getMyBack();

		myPlayerGroup.setBackground(myBackground);

		myBarrierGroup.setBackground(myBackground);
		myEnemyGroup.setBackground(myBackground);
		myCompanionGroup.setBackground(myBackground); 
		myProjectileGroup.setBackground(myBackground);
		myEnemyProjectileGroup.setBackground(myBackground);

		myShip = new Player(200, 2700, "resources/ship.png");
		myShip.setImage(getImage("resources/ship.png"));

		DecoratorManager decman = null;
		try {
			decman = new DecoratorManager();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		ArrayList<String> myDecs = new ArrayList<String>(); 
		myDecs.add("VerticalDecorator"); 
		myDecs.add("HorizontalDecorator"); 
		try {
			decman.addDecorators(myDecs);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		decorations = decman.getDecorators();
		
		InvisibilityDecorator myInv = new InvisibilityDecorator(new SimplePowerUp(), myShip); 
		myPowerUpDecorator = new CompanionDecorator(myInv, myShip);
		
		decCompanion = new ConstantlyMoveDecorator(new SimpleShip()); 

		myShip.setBackground(myBackground);
		myPlayerGroup.add(myShip);
		
		//intit weapons
		Projectile p = new DamagingProjectile("resources/enemy.png",myProjectileGroup,1);
		p.setImage(getImage("resources/enemy.png"));
		ShotPattern s = new SinglePattern(-1);
		myWeapon = new UnlimitedGun(300,p,s);
		myShip.addWeapon(myWeapon);

		// init playfield
		myPlayfield = new PlayField(myBackground);
		myPlayfield.addGroup(myPlayerGroup);
		myPlayfield.addGroup(myBarrierGroup);
		myPlayfield.addGroup(myEnemyGroup);
		myPlayfield.addGroup(myProjectileGroup);
		myPlayfield.addGroup(myCompanionGroup); 
		myPlayfield.addGroup(myEnemyProjectileGroup);

		myPlayfield.addCollisionGroup(myPlayerGroup, myBarrierGroup,
				new PlayerBarrierCollision());
		

		// load level data
		myLoadObservers = new ArrayList<LoadObserver>();
		myLoadObservers
				.add(new HorizontalShipLoadObserver(myPlayerGroup, this));
		myLoadObservers.add(new PlayerLoadObserver(myPlayerGroup, myProjectileGroup, this));
		myLoadObservers.add(new SimpleLoadObserver(myBarrierGroup));
		myLoadObservers.add(new EnemyLoadObserver(myEnemyGroup, myEnemyProjectileGroup));

		LevelLoader l = new LevelLoader(myLoadObservers);
		l.loadLevelData("serializeTest.ser");

		enemySize = myEnemyGroup.getSize();

		// initializing PlayerInfo
		playerInfo = new PlayerInfo();
		
		//HealthBar
		myHealthBar = new HealthBar(myShip); 


	}

	@Override
	public void render(Graphics2D pen) {
		myPlayfield.render(pen);
		myHealthBar.render(pen); 

	}

	@Override
	public void update(long elapsedTime) {
		myMap.moveMap(elapsedTime);
		
		myMap.movePlayer(elapsedTime, myShip);
		playerMovement();

		myPlayfield.update(elapsedTime); 
		myPlayfield.update(elapsedTime);

		decorations.move(this, myShip);
		if(myShip != null){
		myShip.fire(this, elapsedTime);
		}
		
		
		
		myPowerUpDecorator.powerUp(this, myShip, myPlayerGroup);
		
		
		if (!((CompanionDecorator) myPowerUpDecorator).beenCreated())
		{
			myCompanion = ((CompanionDecorator) myPowerUpDecorator).getCompanion();
			myCompanion.setBackground(myBackground); 
			myCompanionGroup.add(((CompanionDecorator) myPowerUpDecorator).getCompanion()); 
			((CompanionDecorator) myPowerUpDecorator).setCreated();  
		}
		
		else{
			decorations.move(this, myCompanion); 
			decCompanion.move(this, myCompanion); 
		}

		
		// this is for testing enemy movement
		count =0;
		for (Sprite elem:myEnemyGroup.getSprites()){
			if (count>=enemySize) break;
			Enemy e = (Enemy) elem;

			e.updateEnemy(elapsedTime);
			count++;
		}
		playerInfo.updatePlayerPosition(myShip.getX(), myShip.getY());

	}

	public void playerMovement() {
		// Used for movements or states that need access to info about player's
		// movement
		playerInfo.setUpwardMovement(keyDown(java.awt.event.KeyEvent.VK_W));
		playerInfo.setDownwardMovement(keyDown(java.awt.event.KeyEvent.VK_S));
		playerInfo.setLeftwardMovement(keyDown(java.awt.event.KeyEvent.VK_A));
		playerInfo.setRightwardMovement(keyDown(java.awt.event.KeyEvent.VK_D));
	}

	public void setPlayer(Player g) {
		myPlayer = g;
	}
	


}
