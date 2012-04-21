/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Polygon;

/**
 *
 * @author Kevin
 */
public class Helper {
   public static boolean intersects(Polygon one, Polygon two){
      boolean cont = true;
      int[] x, y;
      x = one.xpoints;
      y = one.ypoints;
      for(int i=0; i<one.npoints && cont; i++){
         if(two.contains(x[i], y[i])){
            cont = false;
         }
      }
      if(cont){
         x = two.xpoints;
         y = two.ypoints;
         for(int i=0; i<two.npoints && cont; i++){
            if(one.contains(x[i], y[i])){
               cont = false;
            }
         }
      }
      return !cont;
   }
   protected static class Velocity{
      public double vel, theta;
      private Velocity(double tVel, double tTheta){
         vel = tVel;
         theta = tTheta;
      }
   }
   public static Velocity sum(double vel1, double theta1, double vel2, double theta2){
      double x, y;
      x = Math.cos(theta1)*vel1 + Math.cos(theta2)*vel2;
      y = Math.sin(theta1)*vel1 + Math.sin(theta2)*vel2;
      return new Velocity(Math.sqrt(x*x + y*y), Math.atan2(y, x));
      
   }
}
