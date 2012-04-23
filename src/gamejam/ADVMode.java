/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Clem
 */
public class ADVMode extends GameMode {

   protected BufferedImage cutinRight, cutinLeft;
   protected String narrate, say, name;
   protected ADVScript script;
   protected boolean done;

   public ADVMode(String s) {
      this(ADVScript.parse(s));
   }

   public ADVMode(ADVScript s) {
      script = s;
      cutinRight = null;
      cutinLeft = null;
      done = false;
      narrate = "";
      say = "";
      name = "";
      advance();
   }

   public final void advance(){
      while(script.hasNext()){
         
      }
   }
   
   @Override
   public void press(KeyEvent e) {
      //Do nothing
   }

   @Override
   public void release(KeyEvent e) {
      //Do nothing
   }

   @Override
   public void render(Graphics2D g) {
      if(cutinRight != null)
         g.drawImage(cutinRight, 200, 160, null);
      if(cutinLeft != null)
         g.drawImage(cutinLeft, 400, 160, null);
      if(say.length() > 0){
         //Render say text
      }
      if(narrate.length() > 0){
         
      }
   }

   @Override
   public void update() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public GameMode escape() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void mousePress(MouseEvent e) {
      if(script.hasNext())
         advance();
      else
         done = true;
   }

   @Override
   public void mouseRelease(MouseEvent e) {
      //Do nothing
   }

   @Override
   public void mouseMove(MouseEvent e) {
      //Do nothing
   }

   @Override
   public void mouseDrag(MouseEvent e) {
      //Do nothing
   }
   
   public boolean isDone(){
      return done;
   }
}
