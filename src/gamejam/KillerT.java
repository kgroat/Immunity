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
//This Tower stays in place unless something moves it and shoots at a random target every half second.
public class KillerT extends Tower {
   public static final int COST = 500;
   public static final SpriteSet SP = SpriteSet.load("resources/images/killerT.txt");
   private int rateoffire;

   public KillerT() {
      x = Math.random() * Engine.getGameWidth();
      y = Math.random() * Engine.getGameHeight();
      maxVel = 0;
      vel = 0;
      fTheta = Math.random() * Math.PI * 2;
      theta = 0;
      sprite = SP;
      ratUp = 0;
      ratDown = 1;
      primeDist = 100;
      maxHp = hp = 900;
      rateoffire = 15;
      infectionsRemaining = 2;
      maxDTheta = Math.PI / 45;
      radius = 21;
      cost = COST;
      mass = 120;
   }
   
   @Override
   public void act() {
      //remember to implement turning towards your target later!
      if ((target == null) || (target.disposable)) //target=Engine.getBloodVessel().randomIntruder();
      {
         target = Engine.getBloodVessel().nearestIntruder(this);
      }
      if (rateoffire == 0) {
         Engine.getBloodVessel().add(new Projectile(x, y, fTheta, this));
         rateoffire = 15;
      } else {
         rateoffire--;
      }
      super.act();
   }

   public void render(Graphics2D g) {
      super.render(g);
      if (Engine.isDebug()) {
         g.setColor(Color.red);
         if (target != null) {
            g.drawLine((int) x, (int) y, (int) target.x, (int) target.y);
         }
      }
   }

   @Override
   public void move() {
      if (target != null) {
         double tTheta = ((fTheta + Math.PI) % (Math.PI * 2) - Math.PI);
         double targetangle = Math.atan2(target.y - y, target.x - x);
         targetangle -= tTheta;
         double signum = Math.signum(targetangle);
         targetangle = Math.abs(targetangle);
         if (targetangle > Math.PI) {
            signum *= -1;
         }
         targetangle = Math.min(Math.abs(targetangle), maxDTheta);
         fTheta += signum * targetangle;
      }
      super.move();
   }
}
