/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class NaturalKiller extends Tower {
    
    public NaturalKiller()
    {
        x = Math.random()*Engine.getWidth();
        y = Math.random() * Engine.getHeight();
        maxVel = 5.4;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=5;
        ratDown=7;
        primeDist = 100;
        hp=150;
    }
    
    public NaturalKiller(double placewidth, double placeheight)
    {
        this();
        x = placewidth;
        y = placeheight;
    }
    
    @Override
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
        if (target != null && Helper.intersects(this.getBounds(), target.getBounds()))
        {
            Entity [] victims = Engine.getBloodVessel().entitiesNearby(this, 150);
            for (int k=0; k<victims.length; k++)
            {
                victims[k].damage(500);
            }
            disposable = true;
        }
    }
}
