/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;

/**
 *
 * @author dlederle
 */
public class Virus extends Intruder {

   public static final SpriteSet SP = SpriteSet.load("resources/images/particles.txt");
   private String name;

   public Virus() {
      this(true);
   }

   public Virus(boolean left) {
      super(left);
      maxVel = .5;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      sprite = SP;
      ratUp = 0;
      ratDown = 7;
      primeDist = sprite.getSpriteWidth() / 2;
      maxHp = hp = 50;
      name = "v" + (int) (Math.random() * 4 + 1);
      drops = 5;
      radius = 4;
      mass = 10;
   }

   public Virus(double tx, double ty) {
      this();
      x = tx;
      y = ty;
      vel *= 5;
   }

   public Virus(double tx, double ty, double tTheta) {
      this();
      x = tx;
      y = ty;
      theta = tTheta;
   }

   @Override
   public void act() {
//        target = Engine.getBloodVessel().nearestUninfectedTower(this);
//        super.act();
   }

   @Override
   public void onCollision(Entity e) {
      if (e instanceof Tower) {
         ((Tower) e).infect();
         disposable = true;
      }
   }

   @Override
   public void render(Graphics2D g) {
      sprite.enact(name);
      super.render(g);
   }
}
