/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
//Macrophages chase the nearest enemy down and ram into them repeatedly until one of them dies.
public class Macrophage extends Tower{
   public static final int COST = 150;
   public static final SpriteSet SP = SpriteSet.load("resources/images/macrophage.txt");

    public Macrophage()
    {
        x = Math.random()*Engine.getGameWidth();
        y = Math.random() * Engine.getGameHeight();
        maxVel = 5.8;
        vel = 0;
        fTheta = Math.random() * Math.PI * 2;
        theta = 0;
        sprite = SP;
        ratUp=5;
        ratDown=9;
        primeDist = 0;
        maxHp = hp=400;
        bounces = true;
        infectionsRemaining = 3;
        maxDTheta = Math.PI / 14;
        radius = 23;
        cost = COST;
        mass = 30;
    }
    
    public void act()
    {
        if (target != null && target.disposable)
            target=null;
        if(target == null)
           target = Engine.getBloodVessel().nearestIntruder(this);
        super.act();
    }
    
    public void onCollision(Entity other)
    {
        if (other instanceof Intruder)
            other.damage(75);
    }
}
