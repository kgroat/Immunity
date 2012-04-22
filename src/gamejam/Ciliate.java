/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class Ciliate extends Intruder {
    
    public Ciliate ()
    {
      bounces = true;
      x = Math.random() * Engine.getGameWidth();
      y = Math.random() * Engine.getGameHeight();
      maxVel = 4;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      sprite = null;
      ratUp = 6;
      ratDown = 8;
      primeDist = sprite.getSpriteWidth()/2;
      maxHp = hp = 600;
      maxDTheta = Math.PI / 10;
      drops = 35;
    }
    
    public void act()
    {
        if (target==null || target.disposable)
            target=Engine.getBloodVessel().nearestCivilian(this);
        super.act();
    }
    
    public void onCollision(Entity other)
    {
        if (other instanceof Tower)
            other.damage(55);
    }
}
