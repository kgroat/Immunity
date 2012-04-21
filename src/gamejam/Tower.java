/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

/**
 *
 * @author Kevin
 */
public abstract class Tower extends Entity {
    protected int infectionsRemaining;
    protected int numViruses = 0;

    public boolean isInfected() {
        return infectionsRemaining <= 0;
   }
   
   public void infect(){
       infectionsRemaining--;
   }
   
   @Override
   public void act() {
       if(isInfected()) {
           //System.out.println("Infected! with " + hp + " hp left");
           this.damage((50));
           numViruses++;
       }
       super.act();
   }
}
