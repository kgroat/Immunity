/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class LossScreen extends GameMode {
   private static final BufferedImage BG = FileUtility.loadImage("resources/images/loss.png");

   private String from;
   private ArrayList<Button> buttons;
   private BufferedImage bg;
   private int x, y;
   
   public LossScreen(){
      name = "You lose!";
   }
   
   public LossScreen(String s){
      this();
      from = s;
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
   public void mousePress(MouseEvent e) {
//      for(Button b: buttons){
//         if(b.getBounds().contains(e.getPoint())){
//            b.onClick();
//         }
//      }
      Engine.setMode(new ADVMode(from));
   }

   @Override
   public void mouseRelease(MouseEvent e) {
      //Do nothing
   }

   @Override
   public void render(Graphics2D g) {
      if(BG != null)
         g.drawImage(BG, 0, 0, null);
      else
         g.fillRect(0, 0, Engine.getWidth(), Engine.getHeight());
   }

   @Override
   public void update() {
      //Do nothing
   }

   @Override
   public GameMode escape() {
      return this;
   }

   @Override
   public void mouseMove(MouseEvent e) {
      //Do nothing
   }

   @Override
   public void mouseDrag(MouseEvent e) {
      //Do nothing
   }

   @Override
   public boolean isDone() {
      return false;
   }
   
}
