/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
//This Tower flies at the nearest enemy and then explodes, dealing damage to everything nearby (friendly and enemy).
public class NaturalKiller extends Tower {
    
    public NaturalKiller()
    {
        x = Math.random()*Engine.getWidth();        //width of the window
        y = Math.random() * Engine.getHeight();     //height of the window
        maxVel = 5.4;           //maximum possible velocity
        vel = 0;                //starting velocity
        fTheta =Math.random() * Math.PI * 2;    //direction in which it is facing
        theta = 0;              //dircetion in which it is travelling
        sprite = null;          //the image - do not manipulate
        ratUp=5;            //acceleration rate w/ respect to ratDown
        ratDown=7;
        primeDist = 100;    //if the target distance is over 100 pixels, it will
                            //try to achieve maxVel by the time it reaches the target;
                            //maxVel is a mathematical limit
        //less than 100 pixels means it will only try to accelerate as much as needed to
        //get that distance
        maxHp = hp=150;
        bounces = true;
        infectionsRemaining = 3;
    }
    
    public NaturalKiller(double placewidth, double placeheight)
    {
        this();
        x = placewidth;
        y = placeheight;
    }
    
    @Override
    public void act()
    {
        if (target != null && target.disposable)
            target=null;
        super.act();
        if (target != null && Helper.intersects(this.getBounds(), target.getBounds()))
        {
            Entity [] victims = Engine.getBloodVessel().entitiesNearby(this, 150);
            for (int k=0; k<victims.length; k++)
            {
                victims[k].damage(500);
            }
            disposable = true;
        }
    }
    
    //called whenever this() collides with ANYTHING else (friend or foe)
    public void onCollision(Entity other){
        if (other instanceof Intruder && !disposable){
            Entity[] e = Engine.getBloodVessel().entitiesNearby(this, 200);            
            for(int i=0; i<e.length; i++){
                e[i].damage(50);
            }
        }    
    }
}
