package gamejam;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.*;
import java.util.*;

/**
 * This is a helper class for dynamic animated sprites in Java.<p />
 *
 * A SpriteSet has a number of animated sprites, which are either singles or loops.<br />
 * After a single is enacted (and completed), the sprite returns to the previous loop.<br />
 * Two files are used to create SpriteSets: an image file and an initialization text file.<p />
 *
 * See the JavaDoc for SpriteSet.load() for further information on defining sprite sheets.
 *
 * @author Jacob
 */
public class SpriteSet {

   private int spriteWidth, spriteHeight;
   private ArrayList<Sprite> sprites;
   private Sprite currentSingle;
   private Sprite currentLoop;
   private int animationDelay;
   private int currentDelay;
   private int currentFrame;

   /**
    * Instantiates a new SpriteSet from a file path (relative to FileUtility.java)
    *
    * The file at location is of the following format:                                                                            <p />
    * the first non-empty line of the file is the file path of the sprite sheet (image file) to be read (relative to FileUtility.java)<br />
    * the second non-empty line of the file is the resolution a single sprite in the sprite sheet, like this: WIDTH x HEIGHT     <br />
    * every subsequent line represents a new sprite. The line for a sprite is defined as follows:                  <p />&nbsp&nbsp&nbsp
    *    LOOP or SINGLE (indicating the type of the new sprite), followed by a colon (:)                          <br />&nbsp&nbsp&nbsp
    *    the name of the new sprite, used to enact that action from outside the class (not case-sensitive)        <br />&nbsp&nbsp&nbsp
    *    the number of frames in the new sprite, surrounded by parentheses                                        <br />&nbsp&nbsp&nbsp
    *    a semicolon (;) at the end of the line indicates the end of the row in the sprite sheet   <br />&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp
    *       (if there is no semicolon, the frames of the next sprite will also be taken from the current row)                     <p />
    *
    *   Here is an example file, for reference (5 Sprites, extracted from a sprite sheet with 4 rows and 8 columns):
    *
    *  <p />&nbsp&nbsp           superllama.png
    *  <br />&nbsp&nbsp          64 x 64
    *  <br />&nbsp&nbsp          LOOP:   stand (8) ;
    *  <br />&nbsp&nbsp          LOOP:   run   (8) ;
    *  <br />&nbsp&nbsp          LOOP:   fly   (8) ;
    *  <br />&nbsp&nbsp          SINGLE: punch (4)
    *  <br />&nbsp&nbsp          SINGLE: dodge (4)
    *  <br />&nbsp&nbsp&nbsp
    *
    * <p />  Note: all extra whitespace before and after tokens is ignored
    * <br /> Note: the first LOOP defined will be the default sprite for the SpriteSet.
    * <br /> Note: any string other than "LOOP" before the colon (:) of a sprite definition line will define a SINGLE
    *
    * @param location  The path of the text file to be loaded (relative to FileUtility.java)
    * @return          The newly instantiated SpriteSet
    */
   public static SpriteSet load(String location) {
      SpriteSet newSet = new SpriteSet();
      newSet.parse(location);
      newSet.setDefault();
      return newSet;
   }

   /**
    * Updates currentDelay, currentFrame, and the current Sprite
    *
    * Automatically switches to loop when single animation is completed.<br />
    * Automatically restarts loop when loop animation in completed.
    */
   public void update() {
      if (currentDelay == 0) {
         currentDelay = animationDelay;
         currentFrame++;

         if (currentSingle != null) {
            if (currentFrame >= currentSingle.frame.length) {
               currentSingle = null;
               currentFrame = 0;
            }
         } else {
            if (currentFrame >= currentLoop.frame.length) {
               currentFrame = 0;
            }
         }

      } else {
         currentDelay--;
      }
   }

   /**
    * Switches either currentLoop or currentSingle to a new sprite.
    *
    * If the new sprite is a single, the SpriteSet will revert to the
    * previous loop (currentLoop) after the new single animation is completed.
    *
    * @param in  The name of the sprite, defined in the loader text file (ex. stand). This is not case-sensitive
    */
   public void enact(String in) {
      Sprite s = getSprite(in);
      if (s != null && s.loop) {
         currentLoop = s;
      } else {
         currentSingle = s;
      }
      
      currentFrame %= (currentSingle==null)?currentLoop.frame.length:currentSingle.frame.length;
   }

   /**
    * Returns true if the SpriteSet is currently drawing a single; false if a loop
    *
    * @return False if currentSingle is null, otherwise true
    */
   public boolean acting() {
      return currentSingle != null;
   }

   /**
    * Draws the SpriteSet's current sprite frame to a given Graphics object
    *
    * @param g  The Graphics object on which to draw the image
    * @param x  The given horizontal offset on g at which to draw the image
    * @param y  The given vertical offset on g at which to draw the image
    */
   public void draw(Graphics g, int x, int y) {
      Sprite toDraw = currentSingle;
      if (toDraw == null) {
         toDraw = currentLoop;
      }

      g.drawImage(toDraw.frame[currentFrame], x, y, null);
   }

   public void draw(Graphics g, int x, int y, int width, int height) {
      Sprite toDraw = currentSingle;
      if (toDraw == null) {
         toDraw = currentLoop;
      }

      g.drawImage(toDraw.frame[currentFrame], x, y, width, height, null);
   }

   public void flipVertDraw(Graphics g, int x, int y){
      Sprite toDraw = currentSingle;
      if (toDraw == null) {
         toDraw = currentLoop;
      }

      g.drawImage(toDraw.frame[currentFrame], x+spriteWidth, y, -spriteWidth, spriteHeight, null);
   }
   
   public void flipHorizDraw(Graphics g, int x, int y){
      Sprite toDraw = currentSingle;
      if (toDraw == null) {
         toDraw = currentLoop;
      }

      g.drawImage(toDraw.frame[currentFrame], x, y+spriteHeight, spriteWidth, -spriteHeight, null);
   }
   
   public void drawRot(Graphics g, int x, int y, double theta){
      Sprite toDraw = currentSingle;
      if (toDraw == null) {
         toDraw = currentLoop;
      }
      
      AffineTransform xform = new AffineTransform();
      xform.rotate(theta, x, y);
      Graphics2D g2 = (Graphics2D)g;
      g2.setTransform(xform);
      g2.drawImage(toDraw.frame[currentFrame], x-spriteWidth/2, y-spriteHeight/2, null);
      g2.setTransform(new AffineTransform());
   }
   
   public int getSpriteHeight() {
      return spriteHeight;
   }

   public int getSpriteWidth() {
      return spriteWidth;
   }
   
   public int numFrames(){
      if(currentSingle != null)
         return currentSingle.frame.length;
      if(currentLoop != null)
         return currentLoop.frame.length;
      return 0;
         
   }

   /**
    * Prints every frame of every Sprite in the SpriteSet, as a sprite sheet
    *
    * Sprites are displayed as columns, with the top image of every column being the first frame of the animation.<p />
    * This is intended as a method to test your text file and make sure you're properly importing all your sprites.
    *
    * @param g  The Graphics object on which to draw the image
    */
   public void testPrint(Graphics g) {
      for (int i = 0; i < sprites.size(); i++) {
         BufferedImage[] list = sprites.get(i).frame;
         for (int j = 0; j < list.length; j++) {
            g.drawImage(list[j], 100 + i * spriteWidth, 100 + j * spriteHeight, null);
         }
      }
   }

   /**
    * Creates a deep copy of this SpriteSet, maintaining the same set of Sprites and ArrayList.
    * @return The new copy of this SpriteSet.
    */
   public SpriteSet clone(){
      return new SpriteSet(this);
   }

   public int getCurrentFrame() {
      return currentFrame;
   }

   public void setCurrentFrame(int currentFrame) {
      this.currentFrame = currentFrame;
   }
   
   private SpriteSet() {
      sprites = new ArrayList<Sprite>();
      spriteWidth = 0;
      spriteHeight = 0;
      animationDelay = 2;
      currentDelay = 0;
      currentFrame = 0;
   }

   private SpriteSet(SpriteSet original){
      sprites = original.sprites;
      spriteWidth = original.spriteWidth;
      spriteHeight = original.spriteHeight;
      animationDelay = original.animationDelay;
      currentDelay = original.currentDelay;
      currentFrame = original.currentFrame;
      currentLoop = original.currentLoop;
   }
   
   private void parse(String location) {
      Scanner scanner = FileUtility.loadScanner(location);

      int row = 0;
      int col = 0;
      String line;
      BufferedImage spritesheet = null;
      while (scanner.hasNext()) {
         line = scanner.nextLine();
         line = line.trim();
         if (!line.equals("") && line != null) {
            if (spritesheet == null) {
               spritesheet = FileUtility.loadImage(line);
            } else if (spriteWidth == 0 || spriteHeight == 0) {
               spriteWidth = Integer.parseInt(line.substring(0, line.indexOf('x')).trim());
               spriteHeight = Integer.parseInt(line.substring(line.indexOf('x') + 1).trim());
            } else {
               boolean loop = line.substring(0, 4).equals("LOOP");
               line = line.substring(line.indexOf(':') + 1);
               String name = line.substring(0, line.indexOf('(')).trim();
               line = line.substring(line.indexOf('(') + 1);
               int frameCount = Integer.parseInt(line.substring(0, line.indexOf(')')).trim());

               BufferedImage[] frames = new BufferedImage[frameCount];
               for (int i = 0; i < frameCount; i++) {
                  frames[i] = spritesheet.getSubimage(col++ * spriteWidth,
                          row * spriteHeight, spriteWidth, spriteHeight);
               }

               sprites.add(new Sprite(name, frames, loop));

               if (line.contains(";")) {
                  row++;
                  col = 0;
               }
            }
         }
      }
   }

   private Sprite getSprite(String in) {
      for (Sprite s : sprites) {
         if (s.actionName.equalsIgnoreCase(in)) {
            return s;
         }
      }
      return null;
   }

   private void setDefault() {
      currentSingle = null;
      currentLoop = sprites.get(0);
   }

   private static class Sprite {

      private String actionName;
      private BufferedImage[] frame;
      private boolean loop;

      private Sprite(String s, BufferedImage[] f, boolean l) {
         actionName = s;
         frame = f;
         loop = l;
      }
   }
}
