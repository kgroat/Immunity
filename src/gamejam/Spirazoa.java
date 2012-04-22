/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class Spirazoa extends Intruder{
    
    private int rateoffire;
    
    public Spirazoa()
    {
      bounces = true;
      x = Math.random() * Engine.getWidth();
      y = Math.random() * Engine.getHeight();
      maxVel = 3;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      sprite = null;
      ratUp = 3;
      ratDown = 7;
      primeDist = sprite.getSpriteWidth()/2;
      maxHp = hp = 450;
      rateoffire = 15;
    }
    
    public void act()
    {
        if (target==null || target.disposable)
        {
            target=Engine.getBloodVessel().randomTower();
        }
        if (rateoffire==0)
        {
            Engine.getBloodVessel().add(new Projectile(x, y, Math.atan2(target.y-y, target.x-x), this));
            rateoffire=15;
        }
        else
            rateoffire--;
        super.act();
    }

}
