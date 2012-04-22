/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Clem
 */
//This Tower stands still and heals other Towers near it.
public class Monocyte extends Tower {
    public static final SpriteSet SP = SpriteSet.load("resources/images/monocyte.txt");
    Tower[] patients;
    
    public Monocyte ()
    {
        x=Math.random()*Engine.getWidth();
        y=Math.random() * Engine.getHeight();
        maxVel=0;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = SP;
        ratUp=1;
        ratDown=19;
        primeDist = 100;
        maxHp = hp=400;
        maxDTheta = Math.PI/50;
        infectionsRemaining = 4;
        radius = 22;
        bounces = true;
    }
    
    public Monocyte (double placewidth, double placeheight)
    {
        this();
        x=placewidth;
        y=placeheight;
    }
    
    public void act()
    {
        patients = Engine.getBloodVessel().towersNearby(this, 150);
        for (int j=0; j<patients.length; j++){
            patients[j].damage(-.05);
            
        }
        super.act();
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
      if(patients != null && Engine.isDebug()){
         g.setColor(Color.GREEN);
         for(int i=0; i<patients.length; i++){
            g.drawLine((int)x, (int)y, (int)patients[i].x, (int)patients[i].y);
         }
      }
   }
   
}
