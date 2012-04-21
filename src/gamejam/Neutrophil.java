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
public class Neutrophil extends Tower {
    public static final SpriteSet SP = SpriteSet.load("resources/images/cells.txt");
    
    protected double pTheta, dPTheta;
    
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
        ratDown=9;
        primeDist = 100;
        hp=670;
        bounces = false;
        pTheta = Math.random()*Math.PI*2;
        dPTheta = (Math.random()*2-1)*Math.PI/50;
        maxDTheta = Math.PI/50;
    }
    
    public Neutrophil(double placewidth, double placeheight)
    {
        this();
        x=placewidth;
        y=placeheight;
    }
    
    public void act()
    {
       pTheta += dPTheta;
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
    
   @Override
   public void prerender(Graphics2D g) {
      sprite.enact("pre");
      sprite.drawRot(g, (int)x, (int)y, pTheta);
   }

   @Override
   public void render(Graphics2D g) {
      sprite.enact("post");
      sprite.drawRot(g, (int)x, (int)y, fTheta);
   }
   
   @Override
   public void onCollision(Entity other){
      if(other instanceof Intruder)
         other.damage(10);
   }
}
