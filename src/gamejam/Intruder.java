/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

/**
 *
 * @author Kevin
 */
public abstract class Intruder extends Entity {
   protected int drops;
   
   public Intruder(boolean left){
      super(left);
   }
   
   public boolean damage(double ouch)
   {
       boolean result = super.damage(ouch);
       if (disposable)
           Engine.getBloodVessel().aminoAcids+=drops;
       return result;
   }
   
   public void onDispose(){
      super.onDispose();
      int count = drops / 5;
      for(int i=0; i<count; i++){
         Engine.getBloodVessel().add(new AminoParticle(this));
      }
      Engine.getBloodVessel().aminoAcids+=drops;
   }
}
