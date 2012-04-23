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
   public static final SpriteSet SP = SpriteSet.load("resources/images/fungus.txt");
   
   public FungalSpore(){
      this(true);
   }
   
    public FungalSpore(boolean left)
    {
       super(left);
        bounces = true;
      maxVel = 2;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      sprite = SP;
      ratUp = 2;
      ratDown = 7;
      primeDist = sprite.getSpriteWidth()/2;
      maxHp = hp = 1000;
      drops = 50;
      radius = 15;
      mass = 30;
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
