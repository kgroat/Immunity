/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author Kevin
 */
public class BloodVessel extends GameMode {
   
   public enum TowerType { neutrophil, macrophage, monocyte, dendrite, basophil, lymphocyte, naturalkiller, killert };

   public static final BufferedImage BG = FileUtility.loadImage("resources/images/bg.png");
   public static final BufferedImage PANEL = FileUtility.loadImage("resources/images/panel.png");
   ArrayList<Entity> entities;
   ArrayList<Tower> towers;
   ArrayList<Civilian> civilians;
   ArrayList<Intruder> intruders;
   ArrayList<Projectile> projectiles;
   ArrayList<Shockwave> waves;
   double inTh, outTh;
   
   int aminoAcids, furthest;
   boolean[] enabled;
   TowerType selected;
   Entity ontop;

   public BloodVessel() {
      furthest = 8;
      enabled = new boolean[8];
      for(int i=0; i<8; i++){
         enabled[i] = true;
      }
      selected = TowerType.neutrophil;
      entities = new ArrayList();
      towers = new ArrayList();
      civilians = new ArrayList();
      intruders = new ArrayList();
      projectiles = new ArrayList();
      waves = new ArrayList();
   }

   @Override
   public void update() {
      Helper.Intersection pt;
      Entity e, other;
      PillBacteria p1, p2;
      int code1, code2;
      double tVel;
      for (int i = 0; i < entities.size(); i++) {
         e = entities.get(i);
         e.act();
         for (int j = i + 1; j < entities.size(); j++) {
            other = entities.get(j);
            if (e.collides(other)) {
               e.onCollision(other);
               other.onCollision(e);
               if (!(e instanceof Virus || other instanceof Virus) && (e.bounces && other.bounces || e.getClass() == other.getClass())) {
                  Helper.Velocity vel = Helper.sum(e.vel, e.theta, other.vel, other.theta);
                  tVel = Helper.subtract(e, vel, .5).vel;
                  tVel += Helper.subtract(other, vel, .5).vel;
                  e.vel = vel.vel / 2;
                  e.theta = vel.theta;
                  other.vel = vel.vel / 2;
                  other.theta = vel.theta;
                  vel = Helper.collisionVel(e, other, tVel);
                  Helper.add(e, vel, .5);
                  Helper.add(other, vel, -.5);
               }
            }
         }
         double newX = e.x + e.vel * Math.cos(e.theta);
         double newY = e.y + e.vel * Math.sin(e.theta);
         if (newX < 0 || newX > Engine.getGameWidth()) {
            e.bounceX();
         }
         if (newY < 0 || newY > Engine.getGameHeight()) {
            e.bounceY();
         }
      }
      Shockwave s;
      for(int i=0; i<waves.size(); i++){
         s = waves.get(i);
         if(s.isDisposable()){
            waves.remove(i);
            i--;
         }else{
            s.update();
            for(int j=0; j<entities.size(); j++){
               e = entities.get(j);
               s.collideAndEffect(e);
            }
         }
      }
      for (int i = 0; i < entities.size(); i++) {
         e = entities.get(i);
         e.move();
      }
      for (int i = 0; i < entities.size(); i++) {
         e = entities.get(i);
         if (e.disposable) {
            entities.remove(e);
            towers.remove(e);
            intruders.remove(e);
            civilians.remove(e);
            projectiles.remove(e);
            if (e instanceof Tower) {
               int q = ((Tower) e).numViruses;
               for (int j = 0; j < q; j++) {
                  add(new Virus(e.x, e.y));
               }
            }
            i--;
         }
      }
   }

   public Civilian nearestCivilian(Entity e) {
      Civilian i = null;
      double best = Double.MAX_VALUE;
      for (Civilian ti : civilians) {
         if (e.dist(ti) < best) {
            best = e.dist(ti);
            i = ti;
         }
      }
      return i;
   }

   public Tower nearestTower(Entity e) {
      Tower t = null;
      double best = Double.MAX_VALUE;
      Tower tt;
      for (int i = 0; i < towers.size(); i++) {
         tt = towers.get(i);
         if (e.dist(tt) < best) {
            best = e.dist(tt);
            t = tt;
         }
      }
      return t;
   }

   public Tower nearestUninfectedTower(Entity e) {
      Tower t = null;
      double best = Double.MAX_VALUE;
      for (Tower tt : towers) {
         if (e.dist(tt) < best && !tt.isInfected()) {
            best = e.dist(tt);
            t = tt;
         }
      }
      return t;
   }

   public Tower[] towersNearby(Entity e, double radius) {
      ArrayList<Tower> out = new ArrayList();
      for (int j = 0; j < towers.size(); j++) {
         if ((e.dist(towers.get(j)) <= radius) && (towers.get(j) != e)) {
            out.add(towers.get(j));
         }
      }
      return out.toArray(new Tower[out.size()]);
   }

   public Tower randomTower() {
      if (towers.size() > 0) {
         int returner = (int) (Math.random() * towers.size());
         return towers.get(returner);
      } else {
         return null;
      }
   }

   public Intruder randomIntruder() {
      int returner = (int) (Math.random() * intruders.size());
      return intruders.get(returner);
   }

   public Intruder nearestIntruder(Entity e) {
      Intruder i = null;
      double best = Double.MAX_VALUE;
      for (Intruder ti : intruders) {
         if (e.dist(ti) < best) {
            best = e.dist(ti);
            i = ti;
         }
      }
      return i;
   }

   public Intruder[] intrudersNearby(Entity e, double radius) {
      ArrayList<Intruder> targets = new ArrayList();
      for (int j = 0; j < intruders.size(); j++) {
         if ((e.dist(intruders.get(j)) <= radius) && (intruders.get(j) != e)) {
            targets.add(intruders.get(j));
         }
      }
      return targets.toArray(new Intruder[targets.size()]);
   }

   public Entity nearestEntity(Entity e) {
      Entity ne = null;
      double best = Double.MAX_VALUE;
      for (Entity te : entities) {
         if (e.dist(te) < best) {
            best = e.dist(te);
            ne = te;
         }
      }
      return ne;
   }

   public Entity[] entitiesNearby(Entity e, double radius) {
      ArrayList<Entity> targets = new ArrayList();
      for (int j = 0; j < entities.size(); j++) {
         if ((e.dist(entities.get(j)) <= radius) && (entities.get(j) != e)) {
            targets.add(entities.get(j));
         }
      }
      return targets.toArray(new Entity[targets.size()]);
   }

   public PillBacteria nearestPill(PillBacteria p) {
      PillBacteria out = null, tmp;
      double dist = Double.MAX_VALUE;
      for (Intruder i : intruders) {
         if (i instanceof PillBacteria) {
            tmp = (PillBacteria) i;
            if (tmp.col == p.col && tmp.dist(p) < dist && tmp != p && !p.isProblem(tmp)) {
               dist = tmp.dist(p);
               out = tmp;
            }
         }
      }
      return out;
   }

   @Override
   public void press(KeyEvent e) {
       switch(e.getKeyCode()){
          case KeyEvent.VK_1:
             selected = TowerType.neutrophil;
             break;
          case KeyEvent.VK_2:
             selected = TowerType.macrophage;
             break;
          case KeyEvent.VK_3:
             selected = TowerType.monocyte;
             break;
          case KeyEvent.VK_4:
             selected = TowerType.dendrite;
             break;
          case KeyEvent.VK_5:
             selected = TowerType.basophil;
             break;
          case KeyEvent.VK_6:
             selected = TowerType.lymphocyte;
             break;
          case KeyEvent.VK_7:
             selected = TowerType.naturalkiller;
             break;
          case KeyEvent.VK_8:
             selected = TowerType.killert;
             break;
       }
   }

   @Override
   public void release(KeyEvent e) {
      
   }

   @Override
   public void render(Graphics2D g) {
      g.drawImage(BG, 0, 0, null);
      for (int i = 0; i < towers.size(); i++) {
         towers.get(i).prerender(g);
      }
      for (int i = 0; i < intruders.size(); i++) {
         intruders.get(i).prerender(g);
         intruders.get(i).render(g);
      }
      for (int i = 0; i < projectiles.size(); i++) {
         projectiles.get(i).prerender(g);
         projectiles.get(i).render(g);
      }
      for (int i = 0; i < towers.size(); i++) {
         towers.get(i).render(g);
      }
      for(int i=0; i<waves.size(); i++){
         if(waves.get(i).isDisposable()){
            waves.remove(i);
            i--;
         }else{
            waves.get(i).render(Engine.getImage());
         }
      }
      if(ontop != null){
         ontop.render(g);
      }
      //Interface
      drawInterface(g);
   }

   public void drawInterface(Graphics2D g){
      g.drawImage(PANEL, 0, Engine.getGameHeight(), null);
      g.setColor(Color.red);
      g.setStroke(new BasicStroke(10));
      g.drawRoundRect(selected.ordinal()*81+6, Engine.getGameHeight()+5, 70, 140, 20, 20);
      inTh -= Math.PI/37;
      //outTh += Math.PI/53;
      SpriteSet curr;
      for(int i=0; i<furthest; i++){
         if(enabled[i]){
            curr = placeSprite(i+1);
            curr.enact("pre");
            curr.drawRot(g, i*81+41, Engine.getGameHeight()+40, inTh);
            curr.enact("post");
            curr.drawRot(g, i*81+41, Engine.getGameHeight()+40, outTh);
         }
      }
   }
    
   public SpriteSet placeSprite(int t){
      switch(t){
         case 1:
            return Neutrophil.SP;
         case 2:
            return Macrophage.SP;
         case 3:
            return Monocyte.SP;
         case 4:
            return Dendrite.SP;
         case 5:
            return Basophil.SP;
         case 6:
            return Lymphocyte.SP;
         case 7:
            return NaturalKiller.SP;
         case 8:
            return KillerT.SP;
         default:
            return Civilian.SP;
      }
   }
   
   public Tower getTower(TowerType t){
      switch(t){
         case neutrophil:
            return new Neutrophil();
         case macrophage:
            return new Macrophage();
         case monocyte:
            return new Monocyte();
         case dendrite:
            return new Dendrite();
         case basophil:
            return new Basophil();
         case lymphocyte:
            return new Lymphocyte();
         case naturalkiller:
            return new NaturalKiller();
         case killert:
            return new KillerT();
         default:
            return null;
      }
   }
   
   public void add(Entity e) {
      if (e != null) {
         if (e instanceof Tower) {
            towers.add((Tower) e);
            if (e instanceof Civilian) {
               civilians.add((Civilian) e);
            }
         }
         if (e instanceof Intruder) {
            intruders.add((Intruder) e);
         }
         if (e instanceof Projectile) {
            projectiles.add((Projectile) e);
         }
         entities.add(e);
      }
   }
   
   public void add(Shockwave s){
      waves.add(s);
   }

   @Override
   public GameMode escape() {
      throw new UnsupportedOperationException("Not supported yet.");
   }

   @Override
   public void mousePress(MouseEvent e) {
      ontop = getTower(selected);
      ontop.setLoc(e.getPoint());
   }

   @Override
   public void mouseRelease(MouseEvent e) {
      add(ontop);
      ontop = null;
   }

   @Override
   public void mouseMove(MouseEvent e) {
      ontop.setLoc(e.getPoint());
   }

   @Override
   public void mouseDrag(MouseEvent e) {
      ontop.setLoc(e.getPoint());
   }
}
