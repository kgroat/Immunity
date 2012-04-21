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

    public Macrophage()
    {
        x = Math.random()*Engine.getWidth();
        y = Math.random() * Engine.getHeight();
        maxVel = 5.8;
        vel = 0;
        fTheta = Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=5;
        ratDown=9;
        primeDist = 100;
        hp=300;
        bounces = true;
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
        if (target==null)
        {
            if ((target = Engine.getBloodVessel().nearestIntruder(this))!=null)
                super.act();
            else
            {
                targetVel = 0;
            }
        }
    }
    
    public void onCollision(Entity other)
    {
        if (other instanceof Intruder)
            other.damage(75);
    }
}
