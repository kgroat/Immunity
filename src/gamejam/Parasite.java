/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Clem
 */
public class Parasite extends Intruder {
   public static final SpriteSet SP = SpriteSet.load("resources/images/parasite.txt");
   public static final int FRAMES_PER = 1;;
    
    private int wanderlust, frame;
    private boolean walking;
    
    public Parasite(){
       this(true);
    }
    
    public Parasite (boolean left)
    {
       super(left);
      bounces = false;
      maxVel = 7;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      sprite = SP;
      ratUp = 3;
      ratDown = 7;
      primeDist = 0;
      maxHp = hp = 2000;
      wanderlust = 90;
      maxDTheta = Math.PI/15;
      drops = 75;
      mass = 250;
      radius = 45;
    }
    
    @Override
    public boolean damage(double ouch)
    {
        return super.damage(ouch-10);
    }
    
    @Override
    public void act()
    {
        Tower[] taunt = Engine.getBloodVessel().towersNearby(this, 200);
        for (int j=0; j<taunt.length; j++)
            taunt [j].target=this;
    }
    
    @Override
    public void move()
    {
       fTheta = theta;
       targetVel = maxVel;
          vel = (ratDown*vel + ratUp*targetVel)/(ratUp+ratDown);
        if (walking){
          x += vel*Math.cos(theta);
          y += vel*Math.sin(theta);
          if(wanderlust <= 0){
              walking = false;
              wanderlust = 5+(int)(Math.random()*11);
              dPTheta = Math.signum(Math.random()-.5)*maxDTheta;
          }
        }
        else
        {
            fTheta = theta += dPTheta;
            if(wanderlust <= 0){
                walking = true;
                wanderlust = 30+(int)(Math.random()*61);
            }
        }
        wanderlust--;
    }
    
    public void render(Graphics2D g){
      if (sprite.numFrames() > 0) {
         frame = (frame + 1) % (sprite.numFrames() * FRAMES_PER);
         sprite.setCurrentFrame(frame / FRAMES_PER);
         sprite.drawRot(g, (int) x, (int) y, fTheta);
         //System.out.println(frame + " / " + sprite.numFrames() + " / " + FRAMES_PER);
      }
    }
    
    @Override
    public void onCollision(Entity other){
        if(other instanceof Tower){
            other.damage(30);
        }
    }
    
//    public void bounceX()
//    {
//       super.bounceX();
//        wanderlust = 5+(int)(Math.random()*11);
//        walking=!walking;
//    }
//    
//    public void bounceY()
//    {
//       super.bounceY();
//        wanderlust = 5+(int)(Math.random()*11);
//        walking=!walking;
//    }
}
