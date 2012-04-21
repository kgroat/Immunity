/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class BloodVessel {
   ArrayList<Entity> entities;
   ArrayList<Tower> towers;
   ArrayList<Civilian> civilians;
   ArrayList<Intruder> intruders;
   
   public void update(){
      for(Entity e: entities){
         e.act();
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
}
