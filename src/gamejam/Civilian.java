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
      this();
      x = tx;
      y = ty;
      infectionsRemaining = 10;
   }
   
   
   public Civilian() {
      bounces = true;
      maxHp = hp = 2000;
      maxDTheta = 0;
      maxVel = 0;
      mass = 100;
      theta = fTheta = Math.random()*Math.PI*2;
      vel = Math.random()*.5;
      x = Math.random() * Engine.getWidth();
      y = Math.random() * Engine.getHeight();
      sprite = SP;
      pTheta = fTheta = Math.random()*Math.PI*2;
      dPTheta = Math.random()*Math.PI/50;
      infectionsRemaining = 10;
      ratUp = 1;
      ratDown = 5;

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
