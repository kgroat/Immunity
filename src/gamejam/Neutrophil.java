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
    
    public Neutrophil ()
    {
        x=Math.random()*Engine.getWidth();
        y = Math.random() * Engine.getHeight();
        maxVel=.75;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = SP;
        ratUp=1;
        ratDown=9;
        primeDist = 100;
        hp=670;
    }
    
    public Neutrophil(double placewidth, double placeheight)
    {
        x=placewidth;
        y=placeheight;
        maxVel=.75;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = SP;
        ratUp=1;
        ratDown=9;
        primeDist = 100;
        hp=670;
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
        if (target != null && Helper.intersects(this.getBounds(), target.getBounds()))
        {
            target.damage(10);
        }
    }
    
   @Override
   public void prerender(Graphics2D g) {
      sprite.enact("pre");
      sprite.drawRot(g, (int)x, (int)y, fTheta);
   }

   @Override
   public void render(Graphics2D g) {
      sprite.enact("post");
      sprite.drawRot(g, (int)x, (int)y, fTheta);
   }
}
