/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;
import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class SausageLinks extends Intruder{
   ArrayList<PillBacteria> pills;
   PillBacteria.ColorType col;

   public SausageLinks(PillBacteria one, PillBacteria two){
      pills = new ArrayList();
      pills.add(one);
      pills.add(two);
      col = one.col;
   }
   
   @Override
   public void act(){
      pills.get(0).act();
   }
   
   @Override
   public void prerender(Graphics2D g) {
      for(PillBacteria p: pills){
         p.prerender(g);
      }
   }

   @Override
   public void render(Graphics2D g) {
      for(PillBacteria p: pills){
         p.render(g);
      }
   }
}
