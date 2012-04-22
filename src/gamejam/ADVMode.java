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
    
    public ADVMode()
    {
        cutinRight=null;
        cutinLeft=null;
    }
    
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
        g.drawImage(cutinRight, 200, 160, null);
        g.drawImage(cutinLeft, 400, 160, null);
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
