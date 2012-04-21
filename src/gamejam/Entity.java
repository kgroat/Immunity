/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;
import java.awt.Polygon;

/**
 *
 * @author Kevin
 */
public abstract class Entity {
   
   SpriteSet preSprite, sprite;
   protected double hp, x, y, vel, theta, fTheta, targetVel, dTheta, targetDTheta, dFTheta, targetDFTheta, maxDVel, maxDTheta, maxDFTheta, maxVel, mass;
   
   public abstract void act();
   
   public void move(){
      theta += dTheta;
      fTheta += dFTheta;
      x += vel*Math.cos(theta);
      y += vel*Math.sin(theta);
   }
   
   public void updatePos(){
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
      preSprite.drawRot(g, (int)x, (int)y, theta);
   }
   
   public void render(Graphics2D g){
      sprite.drawRot(g, (int)x, (int)y, theta);
   }
   
   public Polygon getBounds(){
      int nx=(int)(Math.cos(fTheta)*getWidth()/2 + Math.sin(fTheta)*getHeight()/2), ny=(int)(Math.cos(fTheta)*getHeight()/2 + Math.sin(fTheta)*getWidth()/2);
      int[] xs = new int[]{nx, -nx, nx, -nx};
      int[] ys = new int[]{ny, ny, -ny, -ny};
      return new Polygon(xs, ys, 4);
   }
   
   public boolean intersects(Entity other){
      return Helper.intersects(getBounds(), other.getBounds());
   }
}
