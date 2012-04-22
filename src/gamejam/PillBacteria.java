/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;
import java.awt.Point;

/**
 *
 * @author Kevin
 */
public class PillBacteria extends Intruder {
   public static final SpriteSet SP = SpriteSet.load("resources/images/bacteria.txt");
   protected PillBacteria parent;
   protected Entity rTarget;
   public static final double DIST_CUTOFF = 100;

   private static final double aggression = 2.0;

   public PillBacteria() {
      bounces = true;
      x = Math.random() * Engine.getGameWidth();
      y = Math.random() * Engine.getGameHeight();
      maxVel = 3;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      col = ColorType.values()[(int) (Math.random() * ColorType.values().length)];
      sprite = SP;
      ratUp = 3;
      ratDown = 7;
      primeDist = sprite.getSpriteWidth()/2;
      maxHp = hp = 500;
      drops = 30;
      radius = 12;
      maxDTheta = Math.PI/15;
   }

   public enum ColorType {

      lime, peagreen, green, aqua, cyan, violet
   };
   ColorType col;

   public PillBacteria(double tx, double ty, double tTheta, ColorType c) {
      this();
      col = c;
      x = tx;
      y = ty;
      theta = tTheta;
   }

   public Point head(){
      return new Point((int)(x+Math.cos(theta)*sprite.getSpriteWidth()/2), (int)(y+Math.sin(theta)*sprite.getSpriteWidth()/2));
   }
   
   public Point tail(){
      return new Point((int)(x-Math.cos(theta)*sprite.getSpriteWidth()/2), (int)(y-Math.sin(theta)*sprite.getSpriteWidth()/2));
   }
   
   public void face(Point p){
      theta = fTheta = Math.atan2(p.y-y, p.x-x);
   }
   
   @Override
   public void render(Graphics2D g){
      sprite.enact(col.name());
      super.render(g);
   }
   
   @Override
   public void act() {
      if(parent != null && parent != this){
         if(parent.disposable || dist(parent)>DIST_CUTOFF){
            parent = null;
         }
         rTarget = null;
      }
      if (parent == null){
         if(rTarget == null)
            rTarget = Engine.getBloodVessel().nearestTower(this);
         PillBacteria nPill = Engine.getBloodVessel().nearestPill(this);
      
         if(nPill != null && dist(nPill)<DIST_CUTOFF){
            parent = nPill;
            target = nPill;
         }else if (nPill != null && rTarget != null) {
            if (dist(nPill) * aggression < dist(rTarget)) {
               target = nPill;
            } else {
               target = rTarget;
            }
         } else if (nPill != null) {
            target = nPill;
         }
      }else if(parent != this){
         target = parent;
         rTarget = null;
      }else{
         target = rTarget;
      }
      System.out.println("TARGET: "+target + " / "+rTarget+" / "+parent);
      super.act();
   }
   
   public boolean isProblem(PillBacteria p){
      PillBacteria hd = p;
      while(hd.parent != null)
         if((hd = hd.parent) == this)
            return true;
      return false;
   }
   
   public void move(){
      if(parent == null){
         super.move();
         fTheta = theta;
      }else{
         //dFTheta = fTheta;
         face(parent.tail());
         dFTheta -= fTheta;
         x += Math.cos(theta)*vel;
         y += Math.sin(theta)*vel;
      }
   }
   
   public void onCollision(Entity e){
      if(e instanceof Tower){
         e.damage(75);
      }
   }
}
