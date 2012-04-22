/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

import java.awt.Graphics2D;

/**
 *
 * @author Clem
 */
//The most basic Tower.  It chases nearby enemies and damages them on contact.
public class Neutrophil extends Tower {
    public static final SpriteSet SP = SpriteSet.load("resources/images/neutrophil.txt");
    
    public Neutrophil ()
    {
        x=Math.random()*Engine.getWidth();
        y = Math.random() * Engine.getHeight();
        maxVel=4.5;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = SP;
        ratUp=1;
        ratDown=29;
        primeDist = 100;
        maxHp = hp=670;
        bounces = false;
        maxDTheta = Math.PI/50;
        infectionsRemaining = 4;
        radius = 31;
    }
    
    public Neutrophil(double placewidth, double placeheight)
    {
        this();
        x=placewidth;
        y=placeheight;
    }
    
    public void act()
    {
        if (target != null && target.disposable)
            target=null;
        if(target == null)
           target = Engine.getBloodVessel().nearestIntruder(this);
        super.act();
    }
   
   @Override
   public void onCollision(Entity other){
      if(other instanceof Intruder)
         other.damage(10);
   }
}
