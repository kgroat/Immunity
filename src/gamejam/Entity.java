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
   //physics base variables
   public static final double VFRICTION = 1.00;
   public static final double TFRICTION = 1.03;
   public static final int TURN_TRIES = 5;
   
   //entity base variables
   protected SpriteSet preSprite, sprite;
   protected Entity target;
   protected double hp, maxHp, x, y, vel, targetVel, theta, fTheta, dFTheta, maxDTheta, maxVel, mass, pTheta, dPTheta;
   protected boolean disposable;
   protected double primeDist, ratUp, ratDown;
   protected boolean bounces;
   protected double radius;
   
   public Entity(){
      fTheta = Math.random()*Math.PI*2;
      pTheta = Math.random()*Math.PI*2;
      dFTheta = (Math.random()*2-1)*Math.PI/50;
      dPTheta = (Math.random()*2-1)*Math.PI/50;
   }
   
   //act sets an entity's target and moves it
   public void act(){
      if(target != null){
         double th, baseTh = theta;
         targetVel = Math.min(dist(target)*maxVel/primeDist, maxVel);
         System.out.println("VEL: "+targetVel);
         for(int i=-TURN_TRIES; i<TURN_TRIES; i++){
            th = baseTh + maxDTheta*i/TURN_TRIES;
            if(isCloser(th)){
               theta = th;
            }
         }
         System.out.println("THETA: "+theta);
      }
   }
   
   //changes the direction of an object calling this function by checking to
   //see whether a new direction will get it closer to the heading that it wants;
   //double t is the current heading
   private boolean isCloser(double t){
      double dx = Math.cos(theta)*targetVel, dy = Math.sin(theta)*targetVel;
      double tdx = Math.cos(t)*targetVel, tdy = Math.sin(t)*targetVel;
      return target.dist(x+dx, y+dy) > 
             target.dist(x+tdx, y+tdy);
   }
   
   //move the object calling this method by modifying vel, x-direction, and y-direction
   public void move(){
      pTheta += dPTheta;
      fTheta += dFTheta;
      vel = (ratDown*vel + ratUp*targetVel)/(ratUp+ratDown);
      while(pTheta < 0) pTheta += Math.PI*2;
      while(fTheta < 0) fTheta += Math.PI*2;
      while(theta < 0) theta += Math.PI*2;
      pTheta %= Math.PI*2;
      fTheta %= Math.PI*2;
      theta %= Math.PI*2;
      x += vel*Math.cos(theta);
      y += vel*Math.sin(theta);
   }
   
   //casts the x-location pixel as an int
   public int getX(){
      return (int)x;
   }
   
   //casts the y-location pixel as an int
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
         preSprite.drawRot(g, (int)x, (int)y, pTheta);
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
      tx -= x; ty -= y;
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
   
   //calculate the x-direction on collision
   public void bounceX(){
      double tx = vel*Math.cos(theta);
      double ty = vel*Math.sin(theta);
      theta = Math.atan2(ty, -tx);
   }
   
   //calculate the y-direction on collision
   public void bounceY(){
      double tx = vel*Math.cos(theta);
      double ty = vel*Math.sin(theta);
      theta = Math.atan2(-ty, tx);
   }
   
   //calculate damage and decrease health accordingly
   public boolean damage(double ouch)
   {
       hp-=ouch;
       hp = Math.min(hp, maxHp);
       disposable=hp<=0;
       return disposable;
   }
   
   public final boolean collides(Entity other){
      return other.dist(this)<radius+other.radius;
   }
   
   public void onCollision(Entity other){
       //placeholder function for entities to use;
       //no actual function written here
   }
}
