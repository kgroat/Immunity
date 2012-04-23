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
   public static final int COST = 300;
   public static final SpriteSet SP = SpriteSet.load("resources/images/naturalKiller.txt");

   //default constructor
   public NaturalKiller() {
      x = Math.random() * Engine.getGameWidth();        //width of the window
      y = Math.random() * Engine.getGameHeight();     //height of the window
      maxVel = 5.4;           //maximum possible velocity
      vel = 0;                //starting velocity
      fTheta = Math.random() * Math.PI * 2;    //direction in which it is facing
      theta = 0;              //dircetion in which it is travelling
      sprite = SP;          //the image - do not manipulate
      ratUp = 5;            //acceleration rate w/ respect to ratDown
      ratDown = 7;
      primeDist = 100;    //if the target distance is over 100 pixels, it will
      //try to achieve maxVel by the time it reaches the target;
      //maxVel is a mathematical limit
      //less than 100 pixels means it will only try to accelerate as much as needed to
      //get that distance
      maxHp = hp = 1;
      bounces = true;
      infectionsRemaining = 3;
      maxDTheta = Math.PI / 17;
      radius = 21;
        cost = COST;
      mass = 50;
   }

   @Override
   //act on an enemy unit
   public void act() {
      //if you have a target but it's about to die, forget that target
      if (target != null && target.disposable) {
         target = null;
      }
      if (target==null)
          target=Engine.getBloodVessel().nearestIntruder(this);
      super.act();
   }

   //called whenever this() collides with ANYTHING else (friend or foe)
   @Override
   public void onCollision(Entity other) {
      //and if it collides with an enemy, it explodes and tries to kill it
      //(50 damage)
      if (other instanceof Intruder && !disposable) {
         disposable = true;
      }
   }

   public void onDispose() {
      super.onDispose();
      Engine.getBloodVessel().add(new Shockwave(x, y, 100));
   }
}
