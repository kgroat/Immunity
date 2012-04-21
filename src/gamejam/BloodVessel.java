/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class BloodVessel extends GameMode {
   public static final Color BG = new Color(201, 58, 58);
   ArrayList<Entity> entities;
   ArrayList<Tower> towers;
   ArrayList<Civilian> civilians;
   ArrayList<Intruder> intruders;
   
   public BloodVessel(){
      entities = new ArrayList();
      towers = new ArrayList();
      civilians = new ArrayList();
      intruders = new ArrayList();
   }
   
   @Override
   public void update(){
      Helper.Intersection pt;
      Entity e, other;
      PillBacteria p1, p2;
      int code1, code2;
      double tVel;
      for(int i=0; i<entities.size(); i++){
         e = entities.get(i);
         e.act();
         for(int j=i+1; j<entities.size(); j++){
            other = entities.get(j);
            pt = e.intersectPoint(other);
            if(pt != null && pt.code >= 0){
               e.onCollision(other);
               other.onCollision(e);
               if(e.bounces && other.bounces){
                  if(pt.code < 4){
                     code1 = pt.code;
                     code2 = other.intersectionCode(pt.x, pt.y);
                  }else{
                     code2 = pt.code-4;
                     code1 = e.intersectionCode(pt.x, pt.y);
                  }
                  Helper.Velocity vel = Helper.sum(e.vel, e.theta, other.vel, other.theta);
   //               double thet = e.dFTheta - other.dFTheta;
   //               if(code1 % 2 == 0){
   //                  e.dFTheta -= thet/25;
   //               }else{
   //                  e.dFTheta += thet/25;
   //               }
   //               if(code2 % 2 == 0){
   //                  other.dFTheta -= thet/25;
   //               }else{
   //                  other.dFTheta += thet/25;
   //               }
                  tVel = Helper.subtract(e, vel, .5).vel;
                  tVel += Helper.subtract(other, vel, .5).vel;
                  e.vel = vel.vel/2;
                  e.theta = vel.theta;
                  other.vel = vel.vel/2;
                  other.theta = vel.theta;
                  vel = Helper.collisionVel(e, other, tVel);
                  Helper.add(e, vel, .5);
                  Helper.add(other, vel, -.5);
               }
            }
         }
         double newX = e.x + e.vel * Math.cos(e.theta);
         double newY = e.y + e.vel * Math.sin(e.theta);
         if(newX < 0 || newX > Engine.getWidth()){
            e.bounceX();
         }
         if(newY < 0 || newY > Engine.getHeight()){
            e.bounceY();
         }
      }
      for(int i=0; i<entities.size(); i++){
         e = entities.get(i);
         e.move();
      }
      for(int i=0; i<entities.size(); i++){
         e = entities.get(i);
         if(e.disposable){
            entities.remove(i);
            towers.remove(e);
            intruders.remove(e);
            civilians.remove(e);
         }
      }
   }
   
   public Tower nearestTower(Entity e){
      Tower t = null;
      double best = Double.MAX_VALUE;
      for(Tower tt: towers){
         if(e.dist(tt)<best){
            best = e.dist(tt);
            t = tt;
         }
      }
      return t;
   }
   
      public Tower nearestUninfectedTower(Entity e){
      Tower t = null;
      double best = Double.MAX_VALUE;
      for(Tower tt: towers){
         if(e.dist(tt)<best && !tt.isInfected()){
            best = e.dist(tt);
            t = tt;
         }
      }
      return t;
   }

   
   public Tower[] towersNearby(Entity e, double radius)
   {
       ArrayList<Tower> out = new ArrayList();
       for (int j=0; j<towers.size(); j++)
       {
           if ((e.dist(towers.get(j))<=radius) && (towers.get(j) != e))
               out.add(towers.get(j));
       }
       return out.toArray(new Tower[out.size()]);
   }
   
   public Intruder randomIntruder()
   {
       int returner = (int)(Math.random()*intruders.size());
       return intruders.get(returner);
   }
   
   public Intruder nearestIntruder(Entity e){
      Intruder i = null;
      double best = Double.MAX_VALUE;
      for(Intruder ti: intruders){
         if(e.dist(ti)<best){
            best = e.dist(ti);
            i = ti;
         }
      }
      return i;
   }
   
   public Intruder[] intrudersNearby(Entity e, double radius)
   {
       ArrayList<Intruder> targets = new ArrayList();
       for (int j=0; j<intruders.size(); j++)
       {
           if ((e.dist(intruders.get(j))<=radius) && (intruders.get(j)!=e))
               targets.add(intruders.get(j));
       }
       return targets.toArray(new Intruder[targets.size()]);
   }
   
   public Entity nearestEntity(Entity e){
      Entity ne = null;
      double best = Double.MAX_VALUE;
      for(Entity te: entities){
         if(e.dist(te)<best){
            best = e.dist(te);
            ne = te;
         }
      }
      return ne;
   }
   
   public Entity[] entitiesNearby(Entity e, double radius)
   {
       ArrayList<Entity> targets = new ArrayList();
       for (int j=0; j<entities.size(); j++)
       {
           if ((e.dist(entities.get(j))<=radius) && (entities.get(j)!=e))
               targets.add(entities.get(j));
       }
       return targets.toArray(new Entity[targets.size()]);
   }
   
   public PillBacteria nearestPill(PillBacteria p){
      PillBacteria out = null, tmp;
      double dist = Double.MAX_VALUE;
      for(Intruder i: intruders){
         if(i instanceof PillBacteria){
            tmp = (PillBacteria)i;
            if(tmp.col == p.col && tmp.dist(p)<dist){
               dist = tmp.dist(p);
               out = tmp;
            }
         }
      }
      return out;
   }

   @Override
   public void press(KeyEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void release(KeyEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void render(Graphics2D g) {
      g.setColor(BG);
      g.fillRect(0, 0, Engine.getWidth(), Engine.getHeight());
      for(int i=0; i<towers.size(); i++){
         towers.get(i).prerender(g);
      }
      for(int i=0; i<intruders.size(); i++){
         intruders.get(i).prerender(g);
         intruders.get(i).render(g);
      }
      for(int i=0; i<towers.size(); i++){
         towers.get(i).render(g);
      }
   }
   
   public void add(Entity e){
      if(e instanceof Tower){
         towers.add((Tower)e);
         if(e instanceof Civilian){
            civilians.add((Civilian)e);
         }
      }
      if(e instanceof Intruder){
         intruders.add((Intruder)e);
      }
      entities.add(e);
   }

   @Override
   public GameMode escape() {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
