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
   public static final SpriteSet SP = SpriteSet.load("resources/images/friendly.txt");
   
   double pTheta, dPTheta;
   public Civilian(double tx, double ty){
      hp = 10000;
      maxDTheta = 0;
      maxVel = 0;
      mass = 100;
      theta = fTheta = Math.random()*Math.PI*2;
      vel = Math.random()*.5;
      x = tx;
      y = ty;
      sprite = SP;
      pTheta = fTheta = Math.random()*Math.PI*2;
      dPTheta = Math.random()*Math.PI/50;
   }
   
   
   public Civilian() {
      x = Math.random() * Engine.getWidth();
      y = Math.random() * Engine.getHeight();
      maxVel = .5;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      sprite = SP;
      ratUp = 3;
      ratDown = 7;
      primeDist = sprite.getSpriteWidth()/2;
      pTheta = fTheta = Math.random()*Math.PI*2;
      dPTheta = (Math.random()*2-1)*Math.PI/50;
   }
   
   public void act(){
      super.act();
      dPTheta += (Math.random()-.5)/300;
      pTheta += dPTheta;
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
}
