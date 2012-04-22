/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

/**
 *
 * @author Kevin
 */
public abstract class Intruder extends Entity {
   protected int drops;
   
   public boolean damage(double ouch)
   {
       boolean result = super.damage(ouch);
       if (disposable)
           Engine.getBloodVessel().aminoAcids+=drops;
       return result;
   }
}
