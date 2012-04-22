/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class FungalSpore extends Intruder {
    
    public FungalSpore()
    {
        bounces = true;
      x = Math.random() * Engine.getGameWidth();
      y = Math.random() * Engine.getGameHeight();
      maxVel = 2;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      sprite = null;
      ratUp = 2;
      ratDown = 7;
      primeDist = sprite.getSpriteWidth()/2;
      maxHp = hp = 1000;
      drops = 50;
    }
    
    public boolean damage(double ouch)
    {
        return super.damage(ouch-5);
    }
    
    public void act()
    {
        if (target==null || target.disposable)
            target=Engine.getBloodVessel().randomTower();
        super.act();
    }
    
    public void onCollision(Entity other)
    {
        if (other instanceof Tower)
            other.damage(15);
    }
}
