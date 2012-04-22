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
public class Projectile extends Entity {

   public static final SpriteSet SP = SpriteSet.load("resources/images/projectiles.txt");
   private Entity shooter;
   private boolean bounced;

   public Projectile() {
      x = Math.random() * Engine.getGameWidth();
      y = Math.random() * Engine.getGameHeight();
      maxVel = 6;
      vel = 6;
      fTheta = Math.random() * Math.PI * 2;
      dFTheta = (Math.random()-.5)*Math.PI/5;
      dFTheta += Math.signum(dFTheta)*Math.PI/60;
      theta = 0;
      sprite = SP;
      ratUp = 0;
      ratDown = 1;
      primeDist = 0;
      hp = 3.14;
      shooter = null;
      bounces = false;
      radius=5;
      bounced = true;
   }

   public Projectile(double startwidth, double startheight, double starttheta, Entity e) {
      this();
      shooter = e;
      x = startwidth;
      y = startheight;
      fTheta = theta = starttheta;
   }

   @Override
   public void onCollision(Entity other) {
      final double DAMAGE = 50;
      if (other == shooter) {
         return;
      }
      if (shooter instanceof Tower) {
         if (other instanceof Intruder) {
            other.damage(DAMAGE);
            disposable = true;
         } else if (other instanceof Tower) {
            vel *= .99;
            if (vel < 2) {
               disposable = true;
            }
         }
      } else {
         if (other instanceof Tower) {
            other.damage(DAMAGE);
            disposable = true;
         } else if (other instanceof Intruder) {
            vel *= .99;
            if (vel < 2) {
               disposable = true;
            }
         }
      }
   }

   public void bounceX(){
      if(bounced)
         disposable = true;
      else{
         super.bounceX();
         bounced = true;
      }
   }
   
   public void bounceY(){
      if(bounced)
         disposable = true;
      else{
         super.bounceY();
         bounced = true;
      }
   }
   
   @Override
   public void render(Graphics2D g) {
      if (shooter instanceof Tower) {
         sprite.enact("tower");
      } else {
         sprite.enact("intruder");
      }
      sprite.drawRot(g, (int)x, (int)y, fTheta);
   }
}
