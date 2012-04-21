/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

/**
 *
 * @author Kevin
 */
public abstract class GameMode {
   public static final AudioClip CONFIRM = AudioClip.get("Select4.ogg");
   public static final AudioClip CURSOR = AudioClip.get("MoveCursor.ogg");
   public static final AudioClip ERROR = AudioClip.get("Error2.ogg");
   protected String name;
   
   public abstract void press(KeyEvent e);
   
   public abstract void release(KeyEvent e);
   
   public abstract void render(Graphics2D g);
   
   public abstract void update();
   
   public abstract GameMode escape();
}
