/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class Macrophage extends Tower {
    public Macrophage ()
    {
        x=Math.random()*Engine.getWidth();
        y = Math.random() * Engine.getHeight();
        maxVel=.75;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=1;
        ratDown=9;
        primeDist = 100;
        hp=670;
    }
    
    public Macrophage(double placewidth, double placeheight)
    {
        x=placewidth;
        y=placeheight;
        maxVel=.75;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=1;
        ratDown=9;
        primeDist = 100;
        hp=670;
    }
    
    public void act()
    {
        if (target.disposable)
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
        if (target != null && Helper.intersects(this.getBounds(), target.getBounds()))
        {
            target.damage(10);
        }
    }
}
