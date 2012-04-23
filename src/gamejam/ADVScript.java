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
   
   
   private enum State{ left, right, say, narrate, shake, audio, wait };
   
   public static ADVScript parse(String loc){
      ADVScript out = new ADVScript();
      ADVCommand c = null;
      String next, tmp;
      State st;
      State[] states = State.values();
      Scanner in = FileUtility.loadScanner(loc);
      while(in.hasNext()){
         next = in.nextLine().trim();
         for(int i=0; i<states.length; i++){
            tmp = states[i].name()+":";
            if(next.toLowerCase().startsWith(tmp)){
               st = states[i];
               c = new ADVCommand(states[i]);
               next = next.substring(tmp.length()).trim();
            }
         }
         if(c != null)
            c.read(next);
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
   
   private static class ADVCommand{
      protected State state;
      protected String text, name;
      protected int one, two;
      private ADVCommand(State s){
         state = s;
         text = "";
         name = "";
      }
      
      public State getState(){
         return state;
      }
      
      public String getText(){
         return text;
      }
      
      public String getName(){
         return name;
      }
      
      public int getOne(){
         return one;
      }
      
      public int getTwo(){
         return two;
      }
      
      private boolean read(String s){
         try{
            switch(state){
               case say:
               case narrate:
                  String tmp=s;
                  if(state == State.say){
                     if(s.startsWith("\"")){
                        tmp = tmp.substring(1);
                        if(tmp.contains("\"")){
                           name = tmp.substring(0, tmp.indexOf("\""));
                           tmp = tmp.substring(name.length() + 1);
                        }
                     }
                  }
                  text += " "+tmp;
                  text = text.trim();
                  break;
               case left:
               case right:
               case audio:
                  name = s.trim();
                  break;
               default:
                  String[] bits = s.split(" ");
                  for(int i=1; i<bits.length; i++){
                     if(bits[i].length()>0 && bits[i-1].length()==0){
                        bits[i-1]=bits[i];
                        bits[i] = "";
                        i--;
                     }
                  }
                  one = Integer.parseInt(bits[0]);
                  two = Integer.parseInt(bits[1]);
            }
            return true;
         }catch(Exception e){
            return false;
         }
      }
   }
}
