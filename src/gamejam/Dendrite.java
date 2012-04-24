/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class Dendrite extends Tower {
   public static final int COST = 300;
   public static final SpriteSet SP = SpriteSet.load("resources/images/dendritic.txt");
    
    private int cooldown;
    
    public Dendrite()
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
        maxHp = hp=700;
        cooldown = 30;
        bounces = true;
        radius = 22;
        cost = COST;
        mass = 80;
    }
    
    public void act()
    {
        Engine.getBloodVessel().aminoAcids++;
    }

}
