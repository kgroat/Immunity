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
public class Particle extends Entity {
   public static final SpriteSet SP = SpriteSet.load("resources/images/particles.txt");
   String name;
   int life;
   public Particle(Entity parent){
      life = (int)(Math.random()*26+5);
      sprite = SP;
      ratUp = 0;
      ratDown = 1;
      theta = Math.random()*Math.PI*2;
      vel = Math.random()*3;
      double place = parent.radius*Math.random();
      double thet = Math.random()*Math.PI*2;
      x = parent.x + Math.cos(thet)*place;
      y = parent.y + Math.sin(thet)*place;
      name = "p"+(int)(Math.random()*4+1);
   }
   
   public Particle(double tx, double ty){
      life = (int)(Math.random()*26+5);
      sprite = SP;
      ratUp = 0;
      ratDown = 1;
      theta = Math.random()*Math.PI*2;
      vel = Math.random()*1;
      x = tx;
      y = ty;
      name = "k"+(int)(Math.random()*4+1);
   }
   
   @Override
   public boolean damage(double d){
      return disposable;
   }
   
   public void act(){
      life--;
      if(life<0){
         disposable=true;
      }
   }
   
   public void render(Graphics2D g){
      sprite.enact(name);
      sprite.draw(g, (int)x, (int)y);
   }
   
   public void onDispose(){
      //Do Nothing
   }
}
