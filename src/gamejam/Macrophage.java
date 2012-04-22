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
   public static final SpriteSet SP = SpriteSet.load("resources/images/friendly.txt");

    public Macrophage()
    {
        x = Math.random()*Engine.getGameWidth();
        y = Math.random() * Engine.getGameHeight();
        maxVel = 5.8;
        vel = 0;
        fTheta = Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=5;
        ratDown=9;
        primeDist = 100;
        maxHp = hp=300;
        bounces = true;
        infectionsRemaining = 3;
        maxDTheta = Math.PI / 14;
    }
    
    public Macrophage(double startwidth, double startheight)
    {
        this();
        x = startwidth;
        y = startheight;
    }
    
    public void act()
    {
        if (target != null && target.disposable)
            target=null;
        super.act();
    }
    
    public void onCollision(Entity other)
    {
        if (other instanceof Intruder)
            other.damage(75);
    }
}
