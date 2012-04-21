/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 *
 * @author Kevin
 */
public final class Engine {
   
   private static int LOGIC_DELAY = 30;
   private static int PAINT_DELAY = 30;
   
   private static Thread mainLoop, renderLoop;
   private static GameMode currentMode;
   private static BufferedImage buffer;
   
   private static boolean running;
   
   private static int vWidth, vHeight;
   
   private Engine(){
      //DO NOTHING
   }
   
   static void start() {
      running = true;
      resize();
      BloodVessel bv;
      currentMode = bv = new BloodVessel();
      for(int i=0; i<100; i++)
         bv.add(new PillBacteria());
      for(int i=0; i<10; i++)
         bv.add(new Civilian());
      for(int i=0; i<10; i++)
          bv.add(new Neutrophil());
      for(int i=0; i<10; i++)
          bv.add(new Monocyte());
      for(int i=0; i<200; i++)
          bv.add(new Virus());
      mainLoop = new Thread(){
         public void run(){
            while(running){
               try{
                  update();
                  renderLoop.sleep(LOGIC_DELAY);
               }catch(Exception e){
                  e.printStackTrace();
               }
            }
         }
      };
      mainLoop.setDaemon(false);
      renderLoop = new Thread(){
         public void run(){
            while(running){
               try{
                  FullScreenView.instance().drawImage(render());
                  renderLoop.sleep(PAINT_DELAY);
               }catch(Exception e){
                  e.printStackTrace();
               }
            }
         }
      };
      renderLoop.setDaemon(true);
      mainLoop.start();
      renderLoop.start();
   }
   
   static void stop(){
      running = false;
   }
   
   static void resize(){
      vWidth = FullScreenView.instance().getScreenWidth()/2;
      vHeight = FullScreenView.instance().getScreenHeight()/2;
      buffer = new BufferedImage(vWidth, vHeight, BufferedImage.TYPE_INT_RGB);
   }
   
   static void pressKey(KeyEvent e) {
      if(currentMode != null)
         currentMode.press(e);
   }

   static void releaseKey(KeyEvent e) {
      if(currentMode != null)
         currentMode.release(e);
   }
   
   static void pressMouse(MouseEvent e){
      
   }
   
   static void releaseMouse(MouseEvent e){
      
   }

   static void setMode(GameMode g){
      currentMode = g;
   }
   
   public static BufferedImage render(){
      if(currentMode != null){
         Graphics2D g = buffer.createGraphics();
         g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
         g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
         g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
         g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
         g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_SPEED);
         g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
         g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
         g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
         currentMode.render(g);
      }
      return buffer;
   }
   
   public static void update(){
      if(currentMode != null)
         currentMode.update();
   }
   
   public static int getWidth(){
      return vWidth;
   }
   
   public static int getHeight(){
      return vHeight;
   }
   
   public static GameMode getCurrentMode(){
      return currentMode;
   }
   
   public static BloodVessel getBloodVessel(){
      if(currentMode instanceof BloodVessel){
         return (BloodVessel)currentMode;
      }
      return null;
   }
   
}
