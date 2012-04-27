package game;

import gameObjects.Enemy;
import gameObjects.Player;
import innerGameGUI.StartGUI;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import levelLoadSave.EnemyLoadObserver;
import levelLoadSave.HorizontalShipLoadObserver;
import levelLoadSave.LevelLoader;
import levelLoadSave.LoadObserver;
import levelLoadSave.PlayerLoadObserver;
import levelLoadSave.SimpleLoadObserver;
import maps.Map;
import weapons.DamagingProjectile;
import weapons.Projectile;
import weapons.ShotPattern;
import weapons.SinglePattern;
import weapons.UnlimitedGun;
import weapons.Weapon;
import bars.HealthBar;

import com.golden.gamedev.Game;
import com.golden.gamedev.object.Background;
import com.golden.gamedev.object.PlayField;
import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;

import decorator.CompanionDecorator;
import decorator.ConstantlyMoveDecorator;
import decorator.DecoratedShip;
import decorator.InvisibilityDecorator;
import decorator.PowerUp;
import decorator.PowerUpDecorator;
import decorator.SimplePowerUp;
import decorator.SimpleShip;

public class TopDownDemo extends Game {

	private Enemy myEnemy;
	private HealthBar myHealthBar;
	private Weapon myWeapon;

	private Player myShip;
	private Player myCompanion;

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
	private Player myPlayer;

	private BufferedImage myBackImage;
	private Map myMap;
	private int count = 0;
	private List<LoadObserver> myLoadObservers;

	private StartGUI start;
	private boolean initialScreen;

	@Override
	public void initResources() {
		initialScreen = true;
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
		System.out.println("1");

		start = new StartGUI(this);

		InvisibilityDecorator myInv = new InvisibilityDecorator(
				new SimplePowerUp(), myShip);
		myPowerUpDecorator = new CompanionDecorator(myInv, myShip);

		decCompanion = new ConstantlyMoveDecorator(new SimpleShip());

		// intit weapons
		Projectile p = new DamagingProjectile("resources/fire.png",
				myProjectileGroup, 1);
		p.setImage(getImage("resources/fire.png"));
		ShotPattern s = new SinglePattern(-1);
		myWeapon = new UnlimitedGun(300, p, s);
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
		// loadLevelData();

		// initializing PlayerInfo
		playerInfo = new PlayerInfo();

		// HealthBar
		myHealthBar = new HealthBar(myShip);

	}

	private void loadLevelData(String path) {
		myLoadObservers = new ArrayList<LoadObserver>();
		myLoadObservers
				.add(new HorizontalShipLoadObserver(myPlayerGroup, this));
		myLoadObservers.add(new PlayerLoadObserver(myPlayerGroup,
				myProjectileGroup, this));
		myLoadObservers.add(new SimpleLoadObserver(myBarrierGroup));

		myLoadObservers.add(new EnemyLoadObserver(myEnemyGroup,
				myEnemyProjectileGroup));

		LevelLoader l = new LevelLoader(myLoadObservers);
		enemySize = myEnemyGroup.getSize();

		// initializing PlayerInfo
		playerInfo = new PlayerInfo();

		// HealthBar
		myHealthBar = new HealthBar(myShip);

	}

	@Override
	public void render(Graphics2D pen) {
		if (initialScreen) {
			start.render(pen);
			return;
		}
		myPlayfield.render(pen);
		myHealthBar.render(pen);

	}

	@Override
	public void update(long elapsedTime) {

		if (initialScreen) {
			start.update(elapsedTime);
			String path = start.getLoadPath();
			// System.out.println(path);
			if (path != null && path.length() > 0) {
				initialScreen = false;
				loadLevelData(path);
			}
			return;
		}
		myMap.moveMap(elapsedTime);
		myMap.movePlayer(elapsedTime, myShip);
		playerMovement();
		myPlayer.move();
		myPlayfield.update(elapsedTime);

		if (myPlayer != null) {
			myPlayer.fire(this, elapsedTime);
		}

		// this is for testing enemy movement
		count = 0;
		for (Sprite elem : myEnemyGroup.getSprites()) {
			if (count >= enemySize)
				break;
			Enemy e = (Enemy) elem;

			e.updateEnemy(elapsedTime);
			count++;
		}
		playerInfo.updatePlayerPosition(myPlayer.getX(), myPlayer.getY());

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
