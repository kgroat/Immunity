/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

import java.awt.Graphics2D;

/**
 *
 * @author Clem
 */
public class Ciliate extends Intruder {
   public static final SpriteSet SP = SpriteSet.load("resources/images/bacteria.txt");
   public static final int FRAMES_PER = 2;
    int frame;
    public Ciliate(){
       this(true);
    }
    public Ciliate (boolean left)
    {
       super(left);
      bounces = true;
      maxVel = 2.5;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      sprite = SP;
      ratUp = 1;
      ratDown = 8;
      primeDist = sprite.getSpriteWidth()/2;
      maxHp = hp = 300;
      maxDTheta = Math.PI / 10;
      drops = 35;
      radius = 11;
      mass = 70;
    }
    
    public void act()
    {
        target=Engine.getBloodVessel().nearestCivilian(this);
        super.act();
    }
    
   @Override
   public void render(Graphics2D g){
      sprite.enact("ciliate");
      if (sprite.numFrames() > 0) {
         frame = (frame + 1) % (sprite.numFrames() * FRAMES_PER);
         sprite.setCurrentFrame(frame / FRAMES_PER);
         sprite.drawRot(g, (int) x, (int) y, fTheta);
      }
   }
    
    public void onCollision(Entity other)
    {
        if (other instanceof Tower)
            other.damage(55);
    }
}
