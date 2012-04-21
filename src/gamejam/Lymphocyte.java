/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
//This Tower moves towards the nearest Intruder and damages it on contact.
//Additionally, this Tower calls nearby Towers to attack the Intruder it is attacking.
public class Lymphocyte extends Tower {
    public Lymphocyte()
    {
        x = Math.random()*Engine.getWidth();
        y = Math.random() * Engine.getHeight();
        maxVel = 4.08;
        vel = 0;
        fTheta = Math.random() * Math.PI * 2;
        theta = 0;
        sprite = null;
        ratUp=2;
        ratDown=8;
        primeDist = 100;
        maxHp = hp=430;
        bounces = false;
        infectionsRemaining = 2;
    }
    
    public Lymphocyte(double placewidth, double placeheight)
    {
        this();
        x = placewidth;
        y = placeheight;
    }
    
    public void act()
    {
        if (target != null && target.disposable)
            target=null;
        if (target==null)
        {
            if ((target = Engine.getBloodVessel().nearestIntruder(this))!=null)
                super.act();
            else
            {
                targetVel = 0;
            }
        }
        //hits the target lightly, then calls nearby towers for help
        if (target != null && Helper.intersects(this.getBounds(), target.getBounds()))
        {
            if (!target.disposable)
            {
                Tower[] heyyou = Engine.getBloodVessel().towersNearby(this, 200);
                for (int j=0; j<heyyou.length; j++)
                {
                    heyyou[j].target=this.target;
                }
            }
        }
    }
    
   @Override
   public void onCollision(Entity other){
      if(other instanceof Intruder)
         other.damage(7);
   }
}
