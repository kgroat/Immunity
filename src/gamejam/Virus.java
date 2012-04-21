/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Graphics2D;

/**
 *
 * @author dlederle
 */
public class Virus extends Intruder {

    public static final SpriteSet SP = SpriteSet.load("resources/images/particles.txt");

    private String name;
    
    public Virus() {
        x = Math.random() * Engine.getWidth();
        y = Math.random() * Engine.getHeight();
        maxVel = .5;
        vel = Math.random() * maxVel;
        theta = fTheta = Math.random() * Math.PI * 2;
        sprite = SP;
        ratUp = 3;
        ratDown = 7;
        primeDist = sprite.getSpriteWidth() / 2;
        hp = 50;
        name = "v"+(int)(Math.random()*4+1);
        bounces = false;
    }

    public Virus(double tx, double ty, double tTheta) {
        x = tx;
        y = ty;
        theta = tTheta;
        hp = 500;
    }

    @Override
    public void act() {
        
        super.act();
    }
    
   @Override
   public void render(Graphics2D g){
      sprite.enact(name);
      super.render(g);
   }
}
