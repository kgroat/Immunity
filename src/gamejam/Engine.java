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
   
   private static final BufferedImage LOADING = FileUtility.loadImage("resources/images/loading.png");
   
   private static int LOGIC_DELAY = 30;
   private static int PAINT_DELAY = 30;
   
   private static Thread mainLoop, renderLoop;
   private static GameMode currentMode;
   private static BufferedImage buffer;
   
   private static boolean running, debug;
   
   private static int vWidth, vHeight;

   static void mouseDrag(MouseEvent e) {
      currentMode.mouseDrag(e);
   }

   static void mouseMove(MouseEvent e) {
      currentMode.mouseMove(e);
   }
   
   private Engine(){
      //DO NOTHING
   }
   
   static void start() {
      FullScreenView.instance().setName("Immunity: Tower Defense");
      FullScreenView.instance().setTitle("Immunity: Tower Defense");
      debug = false;
      running = true;
      resize();
      FullScreenView.instance().drawImage(LOADING);
      setMode(new MainMenu());
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
      AudioClip.get("i-get-around.ogg").forcePlay(true, true);
   }
   
   static void stop(){
      running = false;
   }
   
   public static boolean isDebug(){
      return debug;
   }
   
   static void resize(){
      vWidth = FullScreenView.instance().getScreenWidth();
      vHeight = FullScreenView.instance().getScreenHeight();
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
      FullScreenView.instance().setTitle(currentMode.name);
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
      if(currentMode != null && !currentMode.isDone())
         currentMode.update();
      else
         setMode(currentMode.escape());
   }
   
   public static int getGameWidth(){
      return vWidth;
   }
   
   public static int getGameHeight(){
      return vHeight-150;
   }
   
   public static int getGameX(){
      return 0;
   }
   
   public static int getGameY(){
      return 0;
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
   
   public static void mousePress(MouseEvent e){
      currentMode.mousePress(e);
   }
   
   public static void mouseRelease(MouseEvent e){
      currentMode.mouseRelease(e);
   }
   
   public static BufferedImage getImage(){
      return buffer;
   }
}
