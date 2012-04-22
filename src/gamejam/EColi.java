/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class EColi extends Intruder {
    
    private int cooldown;
    
    public EColi()
    {
      bounces = false;
      x = Math.random() * Engine.getGameWidth();
      y = Math.random() * Engine.getGameHeight();
      maxVel = 4;
      vel = Math.random() * maxVel;
      maxDTheta = Math.PI/15;
      theta = fTheta = Math.random() * Math.PI * 2;
      sprite = null;
      ratUp = 1;
      ratDown = 9;
      primeDist = 32;
      maxHp = hp = 490;
      drops = 35;
      cooldown = 30;
    }
    
    public void act()
    {
        if (target==null || target.disposable)
        {
            target = Engine.getBloodVessel().nearestCivilian(this);
        }
        super.act();
    }
    
    public void onCollision(Entity other)
    {
        if (other instanceof Tower)
        {
            other.damage(10);
        }
        if (other instanceof Civilian)
        {if (cooldown == 0)
            {
                Engine.getBloodVessel().aminoAcids-=1;
                cooldown=31;
            }
            cooldown--;
        }   
    }
}
