/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
//This Tower stays in place unless something moves it and shoots at a random target every half second.
public class KillerT extends Tower {

    private int rateoffire;
    
    public KillerT()
    {
        x = Math.random()*Engine.getWidth();
        y = Math.random() * Engine.getHeight();
        maxVel = 0;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=0;
        ratDown=1;
        primeDist = 100;
        maxHp = hp=500;
        rateoffire=15;
        infectionsRemaining = 2;
    }
    
    public KillerT(double placewidth, double placeheight)
    {
        this();
        x = placewidth;
        y = placeheight;
    }
    
    @Override
    public void act()
    {
        //remember to implement turning towards your target later!
        if ((target==null) || (target.disposable))
            target=Engine.getBloodVessel().randomIntruder();
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
