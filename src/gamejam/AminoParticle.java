/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;

/**
 *
 * @author Kevin
 */
public class AminoParticle extends Particle{
   public static final SpriteSet SP = SpriteSet.load("resources/images/amino.txt");
   public static final int FRAMES_PER = 2;
   int frame;
   public AminoParticle(double tx, double ty){
      super(tx, ty);
      sprite = SP;
      vel = 1.5;
      theta = Math.PI/2;
      fTheta = 0;
   }
   
   public AminoParticle(Entity other){
      super(other);
      sprite = SP;
      vel = 1.5;
      theta = -Math.PI/2;
      fTheta = 0;
   }
   
   @Override
   public void render(Graphics2D g){
      if (sprite.numFrames() > 0) {
         frame = (frame + 1) % (sprite.numFrames() * FRAMES_PER);
         sprite.setCurrentFrame(frame / FRAMES_PER);
         sprite.draw(g, (int) x, (int) y);
      }
   }
}
