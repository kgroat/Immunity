/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;

/**
 *
 * @author Kevin
 */
public abstract class Tower extends Entity {
    protected int infectionsRemaining, cost;
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
           this.damage(50);
           numViruses++;
       }
       super.act();
   }
   
   @Override
   public void prerender(Graphics2D g) {
      sprite.enact("pre");
      sprite.drawRot(g, (int)x, (int)y, pTheta);
   }

   @Override
   public void render(Graphics2D g) {
      sprite.enact("post");
      sprite.drawRot(g, (int)x, (int)y, fTheta);
   }
}
