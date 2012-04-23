/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;
import java.awt.Graphics2D;
import java.awt.color.*;

/**
 *
 * @author Clem
 */
public class Flagellate extends Intruder{
   public static final SpriteSet SP = SpriteSet.load("resources/images/bacteria.txt");
   public static final int FRAMES_PER = 2;
    
    private int crashTimer;
   private int frame;
    
    public Flagellate()
    {
      bounces = true;
      x = Math.random() * Engine.getGameWidth();
      y = Math.random() * Engine.getGameHeight();
      maxVel = 5.9;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      maxDTheta = Math.PI/20;
      sprite = SP;
      ratUp = 2;
      ratDown = 8;
      primeDist = 0;
      maxHp = hp = 500;
      crashTimer=0;
      drops = 40;
      radius = 4;
    }
    
    public void move(){
       super.move();
       fTheta = theta;
    }
    
    public void act()
    {
        if (crashTimer <= 0 && target == null){
            target=Engine.getBloodVessel().nearestTower(this);
        }else
         crashTimer--;
        super.act();
    }
    
    //The flagellate slams into a target and knocks it back, then finds a new target.
    //the Flagellate needs to be more massive than you'd think it would be based on its size.
    public void onCollision(Entity other)
    {
        if (other instanceof Tower)
        {
            other.damage(35);
            target = null;
            crashTimer=10;
        }
        else
            super.onCollision(other);
    }
    

   public void render(Graphics2D g) {
      sprite.enact("flagellate");
      if (sprite.numFrames() > 0) {
         frame = (frame + 1) % (sprite.numFrames() * FRAMES_PER);
         sprite.setCurrentFrame(frame / FRAMES_PER);
         sprite.drawRot(g, (int) x, (int) y, fTheta);
      } else {
         System.out.println("WHOOPSIE!");
      }
   }

}
