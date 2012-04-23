/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Point;
import java.awt.Polygon;

/**
 *
 * @author Kevin
 */
public class Helper {
   
   public static double dist(int x1, int y1, int x2, int y2){
      int tx = x1-x2, ty = y1-y2;
      return Math.sqrt(tx*tx + ty*ty);
   }
   
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
   
   public static Intersection intersectPoint(Polygon one, Polygon two){
      int[] x, y;
      x = one.xpoints;
      y = one.ypoints;
      for(int i=0; i<one.npoints; i++){
         if(two.contains(x[i], y[i])){
            return new Intersection(x[i], y[i], i);
         }
      }
         x = two.xpoints;
         y = two.ypoints;
         for(int i=0; i<two.npoints; i++){
            if(one.contains(x[i], y[i])){
               return new Intersection(x[i], y[i], 4+i);
            }
         }
      return null;
   }
   
   protected static class Intersection{
      public int x, y, code;
      public Intersection(int tx, int ty, int tCode){
         x = tx;
         y = ty;
         code = tCode;
      }
   }
   
   protected static class Velocity{
      public double vel, theta;
      public Velocity(double tVel, double tTheta){
         vel = tVel;
         theta = tTheta;
      }
   }
   
   public static Velocity sum(Entity one, Entity two){
      return sum(one.vel, one.theta, two.vel, two.theta);
   }
   
   public static Velocity sum(double vel1, double theta1, double vel2, double theta2){
      double x, y;
      x = Math.cos(theta1)*vel1 + Math.cos(theta2)*vel2;
      y = Math.sin(theta1)*vel1 + Math.sin(theta2)*vel2;
      return new Velocity(Math.sqrt(x*x + y*y), Math.atan2(y, x));
   }
   
   public static Velocity subtract(Entity e, Velocity v, double mult){
      return sum(e.vel, e.theta, -mult*v.vel, v.theta);
   }
   
   public static void add(Entity e, Velocity v, double mult){
      Velocity vel = sum(e.vel, e.theta, v.vel*mult, v.theta);
      e.vel = vel.vel;
      e.theta = vel.theta;
   }
   
   public static Velocity collisionVel(Entity one, Entity two, double vel){
      double x = one.x - two.x, y = one.y - two.y;
      double theta = Math.atan2(y, x);
      return new Velocity(vel, theta);
   }
   
   public static void bounce(Entity one, Entity two){
      double b = .25, rb = 1 - b;
      double mx1 = Math.cos(one.theta)*one.vel*one.mass;
      double my1 = Math.sin(one.theta)*one.vel*one.mass;
      double mx2 = Math.cos(two.theta)*two.vel*two.mass;
      double my2 = Math.sin(two.theta)*two.vel*two.mass;
      double dmx = mx1-mx2;
      double dmy = my1-my2;
      double mom = Math.sqrt(dmx*dmx + dmy*dmy);
      double mdr = Math.atan2(dmy, dmx);
      double v1 = mx2 / one.mass;
      double v2 = mx2 / two.mass;
      Velocity dif = sum(one.vel, one.theta, -v1, mdr);
      dif = sum(dif.vel*b, dif.theta, v1*rb, mdr);
      one.vel = dif.vel;
      one.theta = dif.theta;
      dif = sum(two.vel, two.theta, -v2, mdr);
      dif = sum(dif.vel*b, dif.theta, v2*rb, mdr);
      one.vel = dif.vel;
      one.theta = dif.theta;
   }
}
