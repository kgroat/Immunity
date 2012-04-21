/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.event.KeyEvent;
import static java.awt.event.KeyEvent.*;

/**
 *
 * @author Kevin
 */
public final class InputHelper {
   public static final int CONFIRM =   0x00000001;
   public static final int CANCEL =    0x00000002;
   public static final int UP =        0x00000004;
   public static final int DOWN =      0x00000008;
   public static final int LEFT =      0x00000010;
   public static final int RIGHT =     0x00000020;
   public static final int MENU =      0x00000040;
   public static final int PAUSE =     0x00000080;
   public static final int RUN =       0x00000100;
   public static final int SELECT =    0x00000200;
   
   private static int confirm = VK_Z,
                       cancel = VK_X,
                           up = VK_UP,
                         down = VK_DOWN,
                         left = VK_LEFT,
                        right = VK_RIGHT,
                         menu = VK_SPACE,
                        pause = VK_ESCAPE,
                          run = VK_C,
                       select = VK_P;
   
   public static int transform(KeyEvent e){
      return transform(e.getKeyCode());
   }
   
   public static int transform(int in){
      int out = 0;
      if(in == confirm) out |= CONFIRM;
      if(in == cancel) out |= CANCEL;
      if(in == up) out |= UP;
      if(in == down) out |= DOWN;
      if(in == left) out |= LEFT;
      if(in == right) out |= RIGHT;
      if(in == menu) out |= MENU;
      if(in == pause) out |= PAUSE;
      if(in == run) out |= RUN;
      if(in == select) out |= SELECT;
      return out;
   }
   
   public static void set(int action, int key){
      if((action & CONFIRM) != 0) confirm = key;
      if((action & CANCEL) != 0) cancel = key;
      if((action & UP) != 0) up = key;
      if((action & DOWN) != 0) down = key;
      if((action & LEFT) != 0) left = key;
      if((action & RIGHT) != 0) right = key;
      if((action & MENU) != 0) menu = key;
      if((action & PAUSE) != 0) pause = key;
      if((action & RUN) != 0) run = key;
      if((action & SELECT) != 0) select = key;
   }
}
