/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.font.LineMetrics;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Clem
 */
public class ADVMode extends GameMode {
   public static final String IMG = "resources/images/";
   public static final String LOC = "resources/scripts/";

   protected BufferedImage cutinRight, cutinLeft, sayImage, narrateImage;
   protected String narrate, say, name;
   protected ADVScript script;
   protected int shakeIntensity, shakeRemaining;
   protected boolean done;
   protected ArrayList<AudioClip> audioClips;
   protected Font current;

   public ADVMode(String s) {
      this(ADVScript.parse(s));
      if(s.contains("/"))
         super.name = s.substring(s.lastIndexOf("/")+1);
      else
         super.name = s;
   }

   public ADVMode(ADVScript s) {
      script = s;
      cutinRight = null;
      cutinLeft = null;
      done = false;
      narrate = "";
      say = "";
      name = "";
      audioClips = new ArrayList();
      current = new Font("serif", Font.PLAIN, 15);
      advance();
   }

   public final void advance(){
      while(audioClips.size() > 0){
         audioClips.get(0).stop();
         audioClips.remove(0);
      }
      done = !script.hasNext();
      ADVScript.ADVCommand c;
      while(script.hasNext() && !(c = script.next()).isWait()){
         switch(c.getState()){
            case say:
               name = c.getName();
               say = c.getText();
               System.out.println("SAY: \""+name+"\" "+say);
               break;
            case narrate:
               narrate = c.getText();
               System.out.println("NARRATE: "+narrate);
               break;
            case left:
               cutinLeft = FileUtility.loadImage(IMG+c.getName()+".png");
               System.out.println("LEFT: "+c.getName());
               break;
            case right:
               cutinRight = FileUtility.loadImage(IMG+c.getName()+".png");
               System.out.println("RIGHT: "+c.getName());
               break;
            case audio:
               System.out.println("AUDIO: "+c.getName());
               AudioClip tmp = AudioClip.get(c.getName());
               tmp.forcePlay(true, true);
               audioClips.add(tmp);
               break;
            case shake:
               shakeIntensity = c.getOne();
               shakeRemaining = c.getTwo();
               System.out.println("SHAKE: "+shakeIntensity+" "+shakeRemaining);
               break;
         }
      }
      sayImage = null;
      narrateImage = null;
   }
   
   public BufferedImage render(String in, Graphics2D g){
      g.setFont(current);
      String[] words = in.split(" ");
      String tmp = "", tmp2 = "";
      ArrayList<String> lines = new ArrayList();
      Rectangle2D rect = new Rectangle2D.Double(0, 0, 0, 0);
      LineMetrics metrics;
      double height = 0;
      for(int i=0; i<words.length; i++){
         tmp2 = (tmp + " " + words[i]);
         rect = current.getStringBounds(tmp2, g.getFontRenderContext());
         metrics = current.getLineMetrics(tmp2, g.getFontRenderContext());
         if(rect.getWidth() < 600){
            tmp = tmp2;
         }else{
            lines.add(tmp);
            tmp2 = tmp = words[i];
            height += metrics.getHeight();
         }
      }
      lines.add(tmp2);
      height += rect.getHeight();
      BufferedImage out = new BufferedImage(600, (int)(height+1), BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = out.createGraphics();
      g2.setColor(Color.red);
      g2.setFont(current);
      float tHeight = 0;
      for(int i=0; i<lines.size(); i++){
         metrics = current.getLineMetrics(lines.get(i), g2.getFontRenderContext());
         g2.drawString(lines.get(i), 1, metrics.getAscent()+tHeight);
         tHeight += metrics.getHeight();
      }
      return out;
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
      g.setFont(current);
      int dx=0, dy=0;
      if(shakeRemaining > 0){
         dx = (int)(shakeIntensity * (Math.random()*2-1));
         dy = (int)(shakeIntensity * (Math.random()*2-1));
      }
      g.setColor(Color.black);
      g.fillRect(0, 0, 800, 600);
      if(cutinRight != null)
         g.drawImage(cutinRight, 100+dx, 160+dy, 200, 300, null);
      if(cutinLeft != null)
         g.drawImage(cutinLeft, 500+dx, 160+dy, 200, 300, null);
      
      BufferedImage tmp;
      Graphics2D g2;
      if(say.length() > 0 && sayImage == null){
         sayImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
         g2 = sayImage.createGraphics();
         tmp = render(say, g);
         g2.setColor(Color.BLUE);
         LineMetrics metrics = g2.getFont().getLineMetrics(name, g2.getFontRenderContext());
         if(tmp.getHeight() > 150){
            g2.fill3DRect(95, 600-tmp.getHeight()-10, 610, tmp.getHeight()+10, true);
            g2.drawImage(tmp, 100, 600-tmp.getHeight()-10, null);
            g2.setColor(Color.WHITE);
            g2.drawString(name, 100, 600-tmp.getHeight()-12-metrics.getDescent());
         }else{
            g2.fill3DRect(95, 440, 610, 160, true);
            g2.drawImage(tmp, 100, 450, null);
            g2.setColor(Color.WHITE);
            g2.drawString(name, 100, 438-metrics.getDescent());
         }
      }
      if(narrate.length() > 0 && narrateImage == null){
         narrateImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
         g2 = narrateImage.createGraphics();
         tmp = render(narrate, g);
         g2.setColor(Color.BLUE);
         g2.fill3DRect(95, 0, 610, Math.max(160, tmp.getHeight()+10), true);
         g2.drawImage(tmp, 100, 5, null);
      }
      
      if(narrateImage != null)
         g.drawImage(narrateImage, dx, dy, null);
      if(sayImage != null)
         g.drawImage(sayImage, dx, dy, null);
      if(shakeRemaining > 0)
         shakeRemaining--;
   }

   @Override
   public void update() {
      //Do nothing
   }

   @Override
   public GameMode escape() {
      return new BloodVessel(super.name);
   }

   @Override
   public void mousePress(MouseEvent e) {
      advance();
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
