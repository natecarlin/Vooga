package weapons;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.imageio.ImageIO;

import com.golden.gamedev.object.Sprite;
import com.golden.gamedev.object.SpriteGroup;

import gameObjects.GameObject;
import gameObjects.GameObjectData;

/** does not create functional Projectile class.
 * instead creates dummy class, to create usable sprite, call createProjectile function (first call setSpriteGroup).
 * 
 * @author ntc2
 *
 */


public abstract class Projectile extends GameObject implements Serializable{
	
	SpriteGroup myGroup;
	double myXSpeed;
	double myYSpeed;
	
	
	
	Projectile( String imgPath, SpriteGroup g){
		
		myImgPath = imgPath;
		myGroup = g;
		myType = "Projectile";
		try{
        	File projImgSrc = new File(getImgPath());
        	BufferedImage projImage = ImageIO.read(projImgSrc);
        	this.setImage(projImage);
        }
        catch(IOException e){
        	e.printStackTrace();
        }
		
		
	}
	
	

	@Override
	public String getImgPath() {
		return myImgPath;
	}
	

	
	public abstract void createProjectile(double xpos, double ypos, double xspeed, double yspeed);
	
	public abstract void actionOnCollision(GameObject hit);
	
	public void removeProjectile(){
		this.myGroup.remove(this);
	}
	
	
	

}
