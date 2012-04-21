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
        ratUp = 0;
        ratDown = 7;
        primeDist = sprite.getSpriteWidth() / 2;
        maxHp = hp = 50;
        name = "v"+(int)(Math.random()*4+1);
    }

    public Virus(double tx, double ty) {
        this();
        x = tx;
        y = ty;
        vel = Math.random() * maxVel * 5;
        hp = 500;
    }
    
    public Virus(double tx, double ty, double tTheta) {
        this();
        x = tx;
        y = ty;
        theta = tTheta;
        hp = 500;
    }

    @Override
    public void act() {
//        target = Engine.getBloodVessel().nearestUninfectedTower(this);
//        super.act();
    }
    
    @Override
    public void onCollision(Entity e) {
        if(e instanceof Tower) {
            ((Tower)e).infect();
            disposable = true;
        }
    }
    
   @Override
   public void render(Graphics2D g){
      sprite.enact(name);
      super.render(g);
   }
}
