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
public class PillBacteria extends Intruder {
   private static final double aggression = 2.0;

   public PillBacteria(){
      x = Math.random()*Engine.getWidth();
      y = Math.random()*Engine.getHeight();
      maxDFTheta = Math.PI/50;
      maxVel = 1;
      vel = Math.random()*maxVel;
      theta = fTheta = Math.random()*Math.PI*2;
      dFTheta = (Math.random()*2-1)*maxDFTheta;
      col = ColorType.values()[(int)(Math.random()*4)];
      sprite = SpriteSet.load("resources/images/pills.txt");
      sprite.enact(col.name());
   }
   
   public enum ColorType{ green, lime, cyan, violet };
   ColorType col;
   
   public PillBacteria(double tx, double ty, double tTheta, ColorType c){
      col = c;
      x = tx; y = ty;
      theta = tTheta;
   }
   
    @Override
   public void act(){
       Tower nTower = Engine.getBloodVessel().nearestTower(this);
       Intruder nPill = Engine.getBloodVessel().nearestPill(this);
       
       if(nPill!=null && nTower!=null) {
           if(dist(nPill)*aggression < dist(nTower)) {
               target = nPill;
           } else {
               target = nTower;
           }
       } else if(nPill!=null) {
           target = nPill;
       }
       
       super.act();
   }
}
