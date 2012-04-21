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
      return t;
   }
   
   public Intruder nearestIntruder(Entity e){
      Intruder i = null;
      return i;
   }
   
   public Entity nearestEntity(Entity e){
      Entity ne = null;
      return ne;
   }
}
