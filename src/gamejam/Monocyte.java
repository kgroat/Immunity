/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class Monocyte extends Tower {

    public Monocyte ()
    {
        x=Math.random()*Engine.getWidth();
        y=Math.random() * Engine.getHeight();
        maxVel=0;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=0;
        ratDown=1;
        primeDist = 100;
        hp=400;
    }
    
    public Monocyte (double placewidth, double placeheight)
    {
        x=placewidth;
        y=placeheight;
        maxVel=0;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=0;
        ratDown=1;
        primeDist = 100;
        hp=400;
    }
    
    public void act()
    {
        Tower[] patients = Engine.getBloodVessel().towersNearby(this, 150);
        for (int j=0; j<patients.length; j++)
            patients[j].damage(-5);
    }
}
