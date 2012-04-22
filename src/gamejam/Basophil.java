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

    public Basophil()
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
        maxHp = hp=1000;
    }
    
    public Basophil(double placex, double placey)
    {
        this();
        x=placex;
        y=placey;
    }
}
