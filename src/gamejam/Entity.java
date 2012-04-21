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
   public static final double VFRICTION = 1.00;
   public static final double TFRICTION = 1.03;
   public static final int TURN_TRIES = 5;
   
   protected SpriteSet preSprite, sprite;
   protected Entity target;
   protected double hp, maxHp, x, y, vel, targetVel, theta, fTheta, maxDTheta, maxVel, mass;
   protected boolean disposable;
   protected double primeDist, ratUp, ratDown;
   protected boolean bounces;
   
   public void act(){
      if(target != null){
         double th;
         targetVel = Math.min(dist(target)*maxVel/primeDist, maxVel);
         for(int i=-TURN_TRIES; i<TURN_TRIES; i++){
            th = theta + maxDTheta*i/TURN_TRIES;
            if(isCloser(th)){
               theta = th;
            }
         }
      }
   }
   
   private boolean isCloser(double t){
      double dx = Math.cos(theta)*targetVel, dy = Math.sin(theta)*targetVel;
      double tdx = Math.cos(t)*targetVel, tdy = Math.sin(t)*targetVel;
      return target.dist(x+dx, y+dy) > 
             target.dist(x+tdx, y+tdy);
   }
   
   public void move(){
      vel = (ratDown*vel + ratUp*targetVel)/(ratUp+ratDown);
      x += vel*Math.cos(theta);
      y += vel*Math.sin(theta);
   }
   
   public int getX(){
      return (int)x;
   }
   
   public int getY(){
      return (int)y;
   }
   
   public int getWidth(){
      if(sprite == null)
         return 0;
      return sprite.getSpriteWidth();
   }
   
   public int getHeight(){
      if(sprite == null)
         return 0;
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
   
   public double dist(double tx, double ty){
      return Math.sqrt(tx*tx + ty*ty);
   }
   
   public Polygon getBounds(){
      int bx = (int)x, by = (int)y;
      int nx=(int)(Math.cos(fTheta)*getWidth()/2 + Math.sin(fTheta)*getHeight()/2), ny=(int)(Math.cos(fTheta)*getHeight()/2 + Math.sin(fTheta)*getWidth()/2);
      int[] xs = new int[]{-nx+bx, nx+bx,-nx+bx, nx+bx};
      int[] ys = new int[]{-ny+by, -ny+by, ny+by, ny+by};
//      int[] xs = new int[]{-nx+bx, nx+bx, nx+bx, -nx+bx};
//      int[] ys = new int[]{-ny+by, -ny+by, ny+by, ny+by};
//      int[] xs = new int[]{-nx+bx, -nx+bx, nx+bx, nx+bx};
//      int[] ys = new int[]{-ny+by, ny+by, ny+by, -ny+by};
      return new Polygon(xs, ys, 4);
   }
   
   public Helper.Intersection intersectPoint(Entity other){
      return Helper.intersectPoint(getBounds(), other.getBounds());
   }
   
   public int intersectionCode(int tx, int ty){
      Polygon p = getBounds();
      int[] xs = p.xpoints;
      int[] ys = p.ypoints;
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
   
   public boolean damage(double ouch)
   {
       System.out.println(hp+" / "+(hp-=ouch));
       hp = Math.min(hp, maxHp);
       disposable=hp<=0;
       return disposable;
   }
   
   public void onCollision(Entity other){
       
   }
}
