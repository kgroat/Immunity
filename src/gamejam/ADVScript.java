/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Kevin
 */
public class ADVScript {
   private ArrayList<ADVCommand> commands;
   private int place;
   
   
   private enum State{ left, right, say, narrate, shake, audio };
   
   public ADVScript parse(String loc){
      ADVScript out = new ADVScript();
      ADVCommand c;
      String next, tmp;
      State st;
      State[] states = State.values();
      Scanner in = FileUtility.loadScanner(loc);
      while(in.hasNext()){
         next = in.nextLine().trim();
         for(int i=0; i<states.length; i++){
            tmp = states[i].name()+":";
            if(next.startsWith(tmp)){
               st = states[i];
               c = new ADVCommand();
               next = next.substring(tmp.length()).trim();
            }
         }
         c = new ADVCommand();
      }
      return out;
   }
   
   private ADVScript(){
      commands = new ArrayList();
   }
   
   public void reset(){
      place = 0;
   }
   
   public ADVCommand next(){
      place = Math.max(0, place);
      return commands.get(place++);
   }
   
   public boolean setPlace(int p){
      if(p >= 0 && p < commands.size()){
         place = p;
         return true;
      }
      return false;
   }
   
   public boolean hasNext(){
      return place < commands.size()-1;
   }
}
