/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Rurouni
 */
public class MainMenu extends GameMode {

   private static final BufferedImage BACK = FileUtility.loadImage("resources/images/title_back.png"), FRONT = FileUtility.loadImage("resources/images/title_front.png");
   private int selected;
   private BufferedImage bg;

   public MainMenu() {
      bg = new BufferedImage(Engine.getWidth(), Engine.getHeight(), BufferedImage.TYPE_INT_ARGB);
      name = "Immunity: Tower Defense";
   }

   @Override
   public void press(KeyEvent e) {
   }

   @Override
   public void release(KeyEvent e) {
   }

   @Override
   public void mousePress(MouseEvent e) {
      AudioClip a = AudioClip.get("Select3.ogg");
      if(a!=null)a.forcePlay(true, true);
      //Engine.setMode(new ADVMode("resources/scripts/infinitemode.txt"));
      Engine.setMode(new ADVMode("resources/scripts/intro.txt"));
   }

   @Override
   public void mouseRelease(MouseEvent e) {
   }

   @Override
   public void render(Graphics2D g) {
      g.drawImage(BACK, 0, 0, null);
      g.drawImage(FRONT, 0, 0, null);
   }

   @Override
   public void update() {
   }

   @Override
   public GameMode escape() {
      return this;
   }

   @Override
   public void mouseMove(MouseEvent e) {
   }

   @Override
   public void mouseDrag(MouseEvent e) {
      //Do nothing
   }

   public boolean isDone() {
      return false;
   }
}
