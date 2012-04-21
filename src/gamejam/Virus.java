/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

/**
 *
 * @author dlederle
 */
public class Virus extends Intruder {

    public static final SpriteSet SP = SpriteSet.load("resources/images/virus.txt");

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
}
