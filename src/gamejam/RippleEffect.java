/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.image.BufferedImage;

/**
 *
 * @author kevingroat
 */
public class RippleEffect {

   public static BufferedImage operate(BufferedImage src, int x, int y, double radius, double amplitude, double phase, double mult) {
      
      //<editor-fold defaultstate="collapsed" desc="setup">
      int width = src.getWidth();
      int height = src.getHeight();
      int size = (int) (radius * 2 + 1);
      int[] inPixels = getRGB( src, 0, 0, width, height, null ), out = new int[2], outPixels = new int[inPixels.length];
      System.arraycopy(inPixels, 0, outPixels, 0, inPixels.length);
      double distance, amount;
      int plc1, plc2;
      //</editor-fold>
      
      //<editor-fold defaultstate="collapsed" desc="calculate extrema">
      //beginning and end of i
      int istart = x-(int)radius;
      istart = Math.max(0, Math.min(istart, width))-x;
      int iend = x+(int)radius;
      iend = Math.max(0, Math.min(iend, width))-x;
      
      //beginning and end of j
      int jstart = y-(int)radius;
      jstart = Math.max(0, Math.min(jstart, height))-y;
      int jend = y+(int)radius;
      jend = Math.max(0, Math.min(jend, height))-y;
      //</editor-fold>
      
      
      for (int i = istart; i < iend; i++) {
         for (int j = jstart; j < jend; j++) {
            if (lenTest(i, j, radius)) {
               
               //<editor-fold defaultstate="collapsed" desc="transformInverse">
               distance = Math.sqrt(i * i + j * j);
               amount = amplitude * Math.sin(distance / radius * Math.PI*mult + phase);
               amount *= distance / radius;
               out[0] = (int) Math.max(0, Math.min(((x+i) - i * amount + .5), width-1));
               out[1] = (int) Math.max(0, Math.min(((y+j) - j * amount + .5), height-1));
               //</editor-fold>
               plc1 = x+i + (y+j) * width;
               plc2 = out[0] + out[1] * width;
               outPixels[plc1] = avgCol(inPixels[plc1], inPixels[plc2], distance);
            }
         }
      }
      setRGB(src, 0, 0, width, height, outPixels);
      return src;
   }

   private static double dist(double x1, double y1, double x2, double y2) {
      double dx = x1 - x2, dy = y1 - y2;
      return Math.sqrt(dx * dx + dy * dy);
   }

   private static boolean lenTest(double x, double y, double len) {
      return x * x + y * y < len * len;
   }

   private static int[] getRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
      int type = image.getType();
      if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
         return (int[]) image.getRaster().getDataElements(x, y, width, height, pixels);
      }
      return image.getRGB(x, y, width, height, pixels, 0, width);
   }

   private static void setRGB(BufferedImage image, int x, int y, int width, int height, int[] pixels) {
      int type = image.getType();
      if (type == BufferedImage.TYPE_INT_ARGB || type == BufferedImage.TYPE_INT_RGB) {
         image.getRaster().setDataElements(x, y, width, height, pixels);
      } else {
         image.setRGB(x, y, width, height, pixels, 0, width);
      }
   }
   
   private static int avgCol(int one, int two, double dist){
      return two;
   }
}
