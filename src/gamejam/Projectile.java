/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class Projectile extends Entity {
    
    private Entity shooter;
    
    public Projectile()
    {
        x = Math.random()*Engine.getWidth();
        y = Math.random() * Engine.getHeight();
        maxVel = 6;
        vel = 6;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=9;
        ratDown=10;
        primeDist = 0;
        hp=3.14;
        shooter = null;
    }
    
    public Projectile (double startwidth, double startheight, double starttheta, Entity e)
    {
        this();
        shooter = e;
        x = startwidth;
        y = startheight;
        fTheta = theta = starttheta;
    }
    
    public void onCollision(Entity other)
    {
        if (other == shooter)
            return;
        if (other instanceof Intruder)
        {
            other.damage(25);
            disposable = true;
        }
        else if (other instanceof Tower)
        {
            vel *= .99;
            if(vel < 2){
                disposable = true;
            }
        }
    }
}
