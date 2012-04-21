/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import javax.imageio.ImageIO;

/**
 * When using this class, make sure to specify the path based on the location
 * of the FileUtility in the jar package.
 * @author Kevin
 */
public final class FileUtility {
   
   /**
    * Gets a URL pointing to the resource at the specified path.
    * @param path The path to the resource, with respect to the FileUtility class.
    * @return The URL to the specified resource.
    */
   public static synchronized URL loadURL(String path){
      return FileUtility.class.getResource(path);
   }
   
   /**
    * Gets an InputStream containing the information stored by a resource at
    * the specified path.
    * @param path The path to the resource, with respect to the FileUtility class.
    * @return The InputStream associated with the resource.
    */
   public static synchronized InputStream loadStream(String path){
      return FileUtility.class.getResourceAsStream(path);
   }
   
   /**
    * Gets a Scanner using an InputStream to read a specific resource 
    * line-by-line.
    * @param path The path to the resource, with respect to the FileUtility class.
    * @return The Scanner for reading the resource.
    */
   public static synchronized Scanner loadScanner(String path){
      return new Scanner(FileUtility.class.getResourceAsStream(path));
   }
   
   /**
    * Loads a BufferedImage from an image resource at the specified path.
    * @param path The path to the resource, with respect to the FileUtility class.
    * @return The given image as a BufferedImage, or null if the file doesn't
    *         exist or it is not of a known image format.
    */
   public static synchronized BufferedImage loadImage(String path){
      try {
         return ImageIO.read(FileUtility.class.getResource(path));
      } catch (Exception ex) {
         System.err.println("Warning: resource \""+path+"\" does not exist or "
                 + "it is not in a known image format.");
         return null;
      }
   }
   
   public static synchronized void saveImage(BufferedImage in, String filename){
      try{
         if(filename.endsWith(".jpg")){
            ImageIO.write(in, "JPG", new File(filename));
         }else if(filename.endsWith(".png")){
            ImageIO.write(in, "PNG", new File(filename));
         }else if(filename.endsWith(".bmp")){
            ImageIO.write(in, "BMP", new File(filename));
         }else if(filename.endsWith(".gif")){
            ImageIO.write(in, "GIF", new File(filename));
         }else{
            ImageIO.write(in, "PNG", new File(filename+".png"));
         }
      }catch(IOException e){
         e.printStackTrace();
      }
   }
   
}
