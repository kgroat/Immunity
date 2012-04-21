/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;

/**
 *
 * @author Kevin
 */
public class Civilian extends Tower {
   
   public Civilian(double tx, double ty){
      hp = 10000;
      maxDVel = 0;
      maxDTheta = 0;
      maxDFTheta = 0;
      maxVel = 0;
      mass = 100;
      theta = fTheta = Math.random()*Math.PI*2;
      vel = Math.random()*maxVel;
      x = tx;
      y = ty;
   }

   @Override
   public void prerender(Graphics2D g) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void render(Graphics2D g) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
