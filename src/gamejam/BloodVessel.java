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
}
