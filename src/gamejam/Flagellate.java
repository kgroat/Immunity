/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;
import java.awt.color.*;

/**
 *
 * @author Clem
 */
public class Flagellate extends Intruder{
    
    boolean hascrashed;
    
    public Flagellate()
    {
        bounces = true;
      x = Math.random() * Engine.getWidth();
      y = Math.random() * Engine.getHeight();
      maxVel = 5.9;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      sprite = null;
      ratUp = 6;
      ratDown = 8;
      primeDist = sprite.getSpriteWidth()/2;
      maxHp = hp = 500;
      hascrashed=true;
    }
    
    public void act()
    {
        if (hascrashed)
            target=Engine.getBloodVessel().nearestTower(this);
        super.act();
    }
    
    //The flagellate slams into a target and knocks it back, then finds a new target.
    //the Flagellate needs to be more massive than you'd think it would be based on its size.
    public void onCollision(Entity other)
    {
        if (other instanceof Tower)
        {
            other.damage(35);
            hascrashed=true;
        }
        else
            super.onCollision(other);
    }

}
