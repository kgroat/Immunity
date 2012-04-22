/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.font.FontRenderContext;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;

/**
 *
 * @author Kevin
 */
public abstract class Button{
   
   public static final Font THOR = createFont("resources/fonts/Thor.ttf");
   public static final Font MEAN = createFont("resources/fonts/MEANSANS.TTF");
   public static final Font SOMETHING = createFont("resources/fonts/Something.ttf");
   private static Font createFont(String s){
      try{
         return Font.createFont(Font.TRUETYPE_FONT, FileUtility.loadStream(s));
      }catch(Exception e){
         e.printStackTrace();
         return null;
      }
   }
   
   public static final Font HEADING = new Font("", Font.BOLD, 15);//SOMETHING.deriveFont(Font.BOLD, 15);
   public static final Font MENU_FONT = new Font("", Font.BOLD, 20);//SOMETHING.deriveFont(Font.BOLD, 20);
   public static final Font DEFAULT_FONT = new Font("", Font.PLAIN, 12);//SOMETHING.deriveFont(Font.PLAIN, 12);
   public static final Color DEFAULT_COLOR = new Color(50, 50, 120);
   public static final int LEFT_JUSTIFIED = 0;
   public static final int CENTER = 1;
   public static final int RIGHT_JUSTIFIED = 2;
   protected int x, y;
   protected Font font;
   protected String text;
   protected LineMetrics metrics;
   protected Color color;
   protected FontRenderContext frc;
   protected int width, height;
   protected boolean enabled;
   protected Color disabledColor;
   
   public Button(String s, Font f, int tx, int ty){
      this(s, f, tx, ty, LEFT_JUSTIFIED);
   }
   
   public Button(String s, Font f, int tx, int ty, int loc){
      text = s;
      if(f != null)
         font = f;
      else
         font = DEFAULT_FONT;
      x = tx;
      y = ty;
      createLineMetrics();
      switch(loc){
         case CENTER:
            x = tx - width/2;
            break;
         case RIGHT_JUSTIFIED:
            x = tx - width;
            break;
      }
      color = DEFAULT_COLOR;
   }
   
   public void setEnabled(boolean b){
      enabled = b;
   }
   
   public void render(Graphics2D g){
      g.setFont(font);
      g.setColor(color);
      g.drawString(text, x, y+metrics.getAscent());
   }
   
   public Rectangle getBounds(){
      return new Rectangle(x, y, width, height);
   }
   
   protected void createLineMetrics(){
      Graphics2D g = (Graphics2D)FullScreenView.instance().getGraphics();
      g.setFont(font);
      frc = g.getFontRenderContext();
      metrics = font.getLineMetrics(text, frc);
      Rectangle2D r = font.getStringBounds(text, frc);
      width = (int)r.getWidth();
      height = (int)r.getHeight();
   }
   
   public abstract void onClick();
}
