/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class Dendritic extends Tower {
   public static final SpriteSet SP = SpriteSet.load("resources/images/friendly.txt");
    
    private int cooldown;
    
    public Dendritic()
    {
        x = Math.random()*Engine.getGameWidth();
        y = Math.random() * Engine.getGameHeight();
        maxVel = 0;
        vel = 0;
        fTheta =Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=0;
        ratDown=1;
        primeDist = 100;
        maxHp = hp=450;
        cooldown = 30;
    }
    
    public Dendritic(double startwidth, double startheight)
    {
        this();
        x = startwidth;
        y = startheight;
    }
    
    public void act()
    {
        if (cooldown==0)
        {
            Engine.getBloodVessel().aminoAcids++;
            cooldown=31;
        }
        cooldown--;
    }

}
