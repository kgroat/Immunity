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
public class PillBacteria extends Intruder{

   public enum ColorType{ green, lime, cyan, violet };
   ColorType col;
   
   public PillBacteria(double tx, double ty, double tTheta, ColorType c){
      col = c;
      x = tx; y = ty;
      theta = tTheta;
   }
   
   @Override
   public void prerender(Graphics2D g){
      //Do nothing
   }
   
   @Override
   public void render(Graphics2D g) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
}
