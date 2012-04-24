/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package gamejam;

/**
 *
 * @author Clem
 */
public class Parasite extends Intruder {
   public static final SpriteSet SP = SpriteSet.load("resoureces/images/parasite.txt");
    
    private int wanderlust;
    private boolean walking;
    
    public Parasite(){
       this(true);
    }
    
    public Parasite (boolean left)
    {
       super(left);
      bounces = false;
      maxVel = 3;
      vel = Math.random() * maxVel;
      theta = fTheta = Math.random() * Math.PI * 2;
      sprite = SP;
      ratUp = 3;
      ratDown = 7;
      primeDist = 0;
      maxHp = hp = 2000;
      wanderlust = 90;
      maxDTheta = Math.PI/15;
      drops = 75;
      mass = 250;
      radius = 45;
    }
    
    @Override
    public boolean damage(double ouch)
    {
        return super.damage(ouch-10);
    }
    
    @Override
    public void act()
    {
        Tower[] taunt = Engine.getBloodVessel().towersNearby(this, 200);
        for (int j=0; j<taunt.length; j++)
            taunt [j].target=this;
    }
    
    @Override
    public void move()
    {
        if (walking){
          vel = (ratDown*vel + ratUp*targetVel)/(ratUp+ratDown);
          x += vel*Math.cos(theta);
          y += vel*Math.sin(theta);
          if(wanderlust <= 0){
              walking = false;
              wanderlust = 5+(int)(Math.random()*11);
              dPTheta = Math.signum(Math.random()-.5)*maxDTheta;
          }
        }
        else
        {
            fTheta = theta += dPTheta;
            if(wanderlust <= 0){
                walking = true;
                wanderlust = 30+(int)(Math.random()*61);
            }
        }
        wanderlust--;
    }
    
    @Override
    public void onCollision(Entity other){
        if(other instanceof Tower){
            other.damage(30);
        }
    }
    
    public void bounceX()
    {
        wanderlust = 5+(int)(Math.random()*11);
        walking=!walking;
    }
    
    public void bounceY()
    {
        wanderlust = 5+(int)(Math.random()*11);
        walking=!walking;
    }
}
