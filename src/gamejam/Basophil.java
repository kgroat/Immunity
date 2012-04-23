/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class Basophil extends Tower {
   public static final int COST = 200;
   public static final SpriteSet SP = SpriteSet.load("resources/images/basophil.txt");

    public Basophil()
    {
        x = Math.random()*Engine.getGameWidth();
        y = Math.random() * Engine.getGameHeight();
        maxVel = 0;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = SP;
        ratUp=0;
        ratDown=1;
        primeDist = 100;
        maxHp = hp=4000;
        infectionsRemaining = 10;
        radius = 31;
        bounces = true;
        cost = COST;
    }
    
    public Basophil(double placex, double placey)
    {
        this();
        x=placex;
        y=placey;
    }
}
