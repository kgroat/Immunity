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
public class Spirillum extends Intruder {

   public static final SpriteSet SP = SpriteSet.load("resources/images/bacteria.txt");
   public static final int FRAMES_PER = 3;
   private int rateoffire;
   private int frame;

   public Spirillum(){
      this(true);
   }
   
   public Spirillum(boolean left) {
      super(left);
      bounces = true;
      maxVel = 3;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      maxDTheta = Math.PI/15;
      sprite = SP;
      ratUp = 3;
      ratDown = 7;
      primeDist = 400;
      minDist = 100;
      maxHp = hp = 450;
      rateoffire = 15;
      drops = 35;
   }

   public void act() {
      if (target == null || target.disposable) {
         target = Engine.getBloodVessel().randomTower();
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
      sprite.enact("spirillum");
      if (sprite.numFrames() > 0) {
         frame = (frame + 1) % (sprite.numFrames() * FRAMES_PER);
         sprite.setCurrentFrame(frame / FRAMES_PER);
         sprite.drawRot(g, (int) x, (int) y, fTheta);
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
