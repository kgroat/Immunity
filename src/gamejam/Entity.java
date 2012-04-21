/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

/**
 *
 * @author Kevin
 */
public abstract class Entity {
   
   SpriteSet preSprite, sprite;
   Entity target;
   protected double hp, x, y, vel, theta, fTheta, targetVel, dTheta, targetDTheta, dFTheta, targetDFTheta, maxDVel, maxDTheta, maxDFTheta, maxVel, mass;
   
   public void act(){
      
   }
   
   public void move(){
      theta += dTheta;
      fTheta += dFTheta;
      x += vel*Math.cos(theta);
      y += vel*Math.sin(theta);
   }
   
   public void updatePos(){
      theta += dTheta;
      fTheta += dFTheta;
      x += Math.cos(theta)*vel;
      y += Math.sin(theta)*vel;
   }
   
   public int getX(){
      return (int)x;
   }
   
   public int getY(){
      return (int)y;
   }
   
   public int getWidth(){
      return sprite.getSpriteWidth();
   }
   
   public int getHeight(){
      return sprite.getSpriteHeight();
   }
   
   public void prerender(Graphics2D g){
      if(preSprite != null)
         preSprite.drawRot(g, (int)x, (int)y, fTheta);
   }
   
   public void render(Graphics2D g){
      if(sprite != null)
         sprite.drawRot(g, (int)x, (int)y, fTheta);
   }
   
   public double dist(Entity other){
      double tx = x-other.x, ty = y-other.y;
      return Math.sqrt(tx*tx + ty*ty);
   }
   
   public Polygon getBounds(){
      int bx = (int)x, by = (int)y;
      int nx=(int)(Math.cos(fTheta)*getWidth()/2 + Math.sin(fTheta)*getHeight()/2), ny=(int)(Math.cos(fTheta)*getHeight()/2 + Math.sin(fTheta)*getWidth()/2);
      int[] xs = new int[]{-nx+bx, nx+bx, -nx+bx, nx+bx};
      int[] ys = new int[]{-ny+by, -ny+by, ny+by, ny+by};
      return new Polygon(xs, ys, 4);
   }
   
   public Helper.Intersection intersectPoint(Entity other){
      return Helper.intersectPoint(getBounds(), other.getBounds());
   }
   
   public int intersectionCode(int tx, int ty){
      int bx = (int)x, by = (int)y;
      int nx=(int)(Math.cos(fTheta)*getWidth()/2 + Math.sin(fTheta)*getHeight()/2), ny=(int)(Math.cos(fTheta)*getHeight()/2 + Math.sin(fTheta)*getWidth()/2);
      int[] xs = new int[]{-nx+bx, nx+bx, -nx+bx, nx+bx};
      int[] ys = new int[]{-ny+by, -ny+by, ny+by, ny+by};
      int best=0;
      double bestDist = Helper.dist(tx, ty, xs[0], ys[0]), dist;
      for(int i=1; i<4; i++){
         dist = Helper.dist(tx, ty, xs[i], ys[i]);
         if(dist < bestDist){
            bestDist = dist;
            best = i;
         }
      }
      return best;
   }
   
   public void bounceX(){
      double tx = vel*Math.cos(theta);
      double ty = vel*Math.sin(theta);
      theta = Math.atan2(ty, -tx);
   }
   
   public void bounceY(){
      double tx = vel*Math.cos(theta);
      double ty = vel*Math.sin(theta);
      theta = Math.atan2(-ty, tx);
   }
}
