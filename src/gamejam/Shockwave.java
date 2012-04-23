/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author kevingroat
 */
public class Shockwave{
   
   double x, y, radius, maxRad, odx, ody;
   int power, curr;
   int total;
   private boolean disposable;
   private static int counter = 0;
   
   public Shockwave(double tx, double ty, int tPower){
      x = tx; y = ty;
      power = tPower;
      curr = 0;
      total = 15;
      radius = power/10.+30;
      maxRad = power*1.05+30;
      
      counter%=3;
      counter++;
   }
   
   public void update(){
      radius += power/(curr+1)/5;
      if(curr >= total)
         destroy();
      curr++;
   }
   
   public void collideAndEffect(Entity other){
      double dist = dist(other);
      if(dist < radius){
         double val = power*(maxRad-dist)/maxRad;
         Helper.add(other, new Helper.Velocity(val/15., Math.atan2(other.y-y, other.x-x)), 1);
         other.damage(50);
      }
   }
   
   public void render(BufferedImage bi){
      //For now:
      RippleEffect.operate(bi, (int)x, (int)y, radius, (total-curr)*(total-curr)*(power+20.)/120/20/15, Math.PI/2, 0);//curr/2.5);
      Graphics g = bi.getGraphics();
      g.setColor(new Color(255, 200, 0, 50*(total-curr)/total));
      g.fillOval((int)(x-radius), (int)(y-radius), (int)(2*radius), (int)(2*radius));
   }
   
   public double dist(Entity other){
      double dx = other.x-x, dy = other.x-y;
      return Math.sqrt(dx*dx+dy*dy);
   }
   
   public boolean isDisposable(){
      return disposable;
   }
   
   public void destroy(){
      disposable = true;
   }
   
}
