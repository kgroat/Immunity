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

    @Override
    public void press(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void release(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void render(Graphics2D g) {
        BufferedImage bi = FileUtility.loadImage("resources/images/particles.png");
        g.drawImage(bi, 0, 0, null);
        throw new UnsupportedOperationException("Not supported yet.");
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
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void mouseRelease(MouseEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void mouseMove(MouseEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void mouseDrag(MouseEvent e) {
      throw new UnsupportedOperationException("Not supported yet.");
   }
    

}
