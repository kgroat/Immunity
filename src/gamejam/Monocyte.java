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

   public static final int COST = 300;
   public static final SpriteSet SP = SpriteSet.load("resources/images/monocyte.txt");
   Tower[] patients;

   public Monocyte() {
      x = Math.random() * Engine.getGameWidth();
      y = Math.random() * Engine.getGameHeight();
      maxVel = 0;
      vel = 0;
      fTheta = Math.random() * Math.PI * 2;
      theta = 0;
      sprite = SP;
      ratUp = 1;
      ratDown = 19;
      primeDist = 100;
      maxHp = hp = 800;
      maxDTheta = Math.PI / 50;
      infectionsRemaining = 4;
      radius = 22;
      bounces = true;
      cost = COST;
   }

   public Monocyte(double placewidth, double placeheight) {
      this();
      x = placewidth;
      y = placeheight;
   }

   public void act() {
      double thet, dist;
      patients = Engine.getBloodVessel().towersNearby(this, 150);
      for (int i = 0; i < patients.length; i++) {
         patients[i].damage(-.5);
         if(Math.random()<.3){
            thet = Math.atan2(patients[i].y - y, patients[i].x - x);
            dist = dist(patients[i]) * Math.random();
            Engine.getBloodVessel().add(new Particle(x + Math.cos(thet) * dist, y + Math.sin(thet) * dist));
         }

      }
      super.act();
   }

   @Override
   public void prerender(Graphics2D g) {
      sprite.enact("pre");
      sprite.drawRot(g, (int) x, (int) y, pTheta);
   }

   @Override
   public void render(Graphics2D g) {
      sprite.enact("post");
      sprite.drawRot(g, (int) x, (int) y, fTheta);
      if (patients != null) {
         g.setColor(Color.GREEN);
         double thet, dist;
         for (int i = 0; i < patients.length; i++) {
            if (Engine.isDebug()) {
               g.drawLine((int) x, (int) y, (int) patients[i].x, (int) patients[i].y);
            }
         }
      }
   }
}
