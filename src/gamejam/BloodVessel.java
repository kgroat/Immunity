/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
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
   
   BufferedImage normal, bad, good;

   private void normalizeProbs() {
      double total = 0;
      for(int i=0; i<intruderProbs.length; i++){
         total += intruderProbs[i];
      }
      for(int i=0; i<intruderProbs.length; i++){
         intruderProbs[i]/=total;
      }
   }

   private void spawnIntruder() {
      double t = Math.random();
      int curr = 0;
      while(t > 0 && curr < IntruderType.values().length){
         t -= intruderProbs[curr];
         curr++;
      }
      curr--;
      add(getIntruder(curr));
   }
   
   public enum TowerType { neutrophil, macrophage, monocyte, dendrite, basophil, lymphocyte, naturalkiller, killert };
   public enum IntruderType { ciliate, ecoli, flagellate, fungalspore, parasite, pillbacteria, spirillum, virus };

   public static final BufferedImage BG = FileUtility.loadImage("resources/images/bg.png");
   public static final BufferedImage PANEL = FileUtility.loadImage("resources/images/panel.png");
   public static final int FRAMES_PER=2;
   ArrayList<Entity> entities;
   ArrayList<Tower> towers;
   ArrayList<Civilian> civilians;
   ArrayList<Intruder> intruders;
   ArrayList<Projectile> projectiles;
   ArrayList<Shockwave> waves;
   ArrayList<Particle> particles;
   double inTh, outTh;
   String nextName;
   
   int aminoAcids, framesLeft, spawnFreq, spawnLeft, aminoFrame;
   boolean[] towersEnabled;
   double[] intruderProbs;
   TowerType selected;
   Tower ontop;

   public BloodVessel() {
      towersEnabled = new boolean[TowerType.values().length];
      intruderProbs = new double[IntruderType.values().length];
      for(int i=0; i<towersEnabled.length; i++){
         towersEnabled[i] = true;
      }
      for(int i=0; i<intruderProbs.length; i++){
         intruderProbs[i] = 1;
      }
      selected = TowerType.neutrophil;
      entities = new ArrayList();
      towers = new ArrayList();
      civilians = new ArrayList();
      intruders = new ArrayList();
      projectiles = new ArrayList();
      particles = new ArrayList();
      waves = new ArrayList();
      nextName = "intro.txt";
      normalizeProbs();
      AudioClip.get("time-to-kick-ass.ogg").forcePlay(true, true);
      normal = FileUtility.loadImage(ADVMode.IMG+"Charlie Normal.png");
      good = FileUtility.loadImage(ADVMode.IMG+"Charlie Gnarly.png");
      bad = FileUtility.loadImage(ADVMode.IMG+"Charlie Unpleasant.png");
   }
   
   public BloodVessel(String s){
      this();
      if(s.contains("/"))
         name = s.substring(s.lastIndexOf("/")+1);
      else
         name = s;
      if(s.endsWith("intro.txt")){
         nextName = "tutorial1.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 0, 0, 1, 0, 1, 0, 0};
         towersEnabled = new boolean[]{true, true, false, false, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 4000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("tutorial1.txt")){
         nextName = "tutorial2.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 0, 1, 1, 0, 1, 1, 0};
         towersEnabled = new boolean[]{true, true, true, true, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 3000;
         for(int i=0; i<30; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("tutorial2.txt")){
         nextName = "tutorial3.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 1, 1, 1, 0, 1, 1, 0};
         towersEnabled = new boolean[]{true, true, true, true, true, true, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 3000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("tutorial3.txt")){
         nextName = "stage1.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 1, 1, 1, 0, 1, 1, 1};
         towersEnabled = new boolean[]{true, true, true, true, true, true, true, true};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 2000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("stage1.txt")){
         nextName = "stage2.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 1, 1, 1, 0, 1, 1, 5};
         towersEnabled = new boolean[]{true, true, true, true, true, true, true, true};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 1000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("stage2.txt")){
         nextName = "stage3.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 0, 0, 1, 0, 1, 0, 0};
         towersEnabled = new boolean[]{true, true, false, false, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 4000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("stage3.txt")){
         nextName = "stage4.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 0, 0, 1, 0, 1, 0, 0};
         towersEnabled = new boolean[]{true, true, false, false, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 4000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("stage4.txt")){
         nextName = "stage5.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 0, 0, 1, 0, 1, 0, 0};
         towersEnabled = new boolean[]{true, true, false, false, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 4000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("stage5.txt")){
         nextName = "stage6.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 0, 0, 1, 0, 1, 0, 0};
         towersEnabled = new boolean[]{true, true, false, false, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 4000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("stage6.txt")){
         nextName = "stage7.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 0, 0, 1, 0, 1, 0, 0};
         towersEnabled = new boolean[]{true, true, false, false, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 4000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("stage7.txt")){
         nextName = "stage8.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 0, 0, 1, 0, 1, 0, 0};
         towersEnabled = new boolean[]{true, true, false, false, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 4000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("stage8.txt")){
         nextName = "stage9.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 0, 0, 1, 0, 1, 0, 0};
         towersEnabled = new boolean[]{true, true, false, false, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 4000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("stage9.txt")){
         nextName = "stage10.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 0, 0, 1, 0, 1, 0, 0};
         towersEnabled = new boolean[]{true, true, false, false, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 4000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else if(s.endsWith("stage10.txt")){
         nextName = "infinitemode.txt";
         framesLeft = 30*60; //60 seconds
         intruderProbs = new double[]{1, 0, 0, 1, 0, 1, 0, 0};
         towersEnabled = new boolean[]{true, true, false, false, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 4000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }else{//infinitemode
         nextName = "infinitemode.txt";
         framesLeft = Integer.MAX_VALUE; //60 seconds
         intruderProbs = new double[]{1, 0, 0, 1, 0, 1, 0, 0};
         towersEnabled = new boolean[]{true, true, false, false, false, false, false, false};
         normalizeProbs();
         spawnFreq = 20;
         aminoAcids = 4000;
         for(int i=0; i<20; i++){
            spawnIntruder();
         }
         for(int i=0; i<10; i++){
            add(new Civilian());
         }
      }
   }

   @Override
   public void update() {
      spawnLeft--;
      framesLeft--;
      if(framesLeft > 0 && spawnLeft <= 0){
         spawnLeft = spawnFreq;
         spawnIntruder();
      }
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
            e.onDispose();
            entities.remove(e);
            towers.remove(e);
            intruders.remove(e);
            civilians.remove(e);
            projectiles.remove(e);
            particles.remove(e);
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
      Civilian c = null;
      double best = Double.MAX_VALUE;
      Civilian tc;
      for (int i=0; i<civilians.size(); i++) {
         tc = civilians.get(i);
         if (e.dist(tc) < best) {
            best = e.dist(tc);
            c = tc;
         }
      }
      return c;
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
      Intruder ti;
      for (int j=0; j<intruders.size(); j++) {
         ti = intruders.get(j);
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
      Entity te;
      for (int i=0; i<entities.size(); i++) {
         te = entities.get(i);
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
      Intruder i;
      for (int j=0; j<intruders.size(); j++) {
         i = intruders.get(j);
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
             if(towersEnabled[0])
               selected = TowerType.neutrophil;
             break;
          case KeyEvent.VK_2:
             if(towersEnabled[1])
               selected = TowerType.macrophage;
             break;
          case KeyEvent.VK_3:
             if(towersEnabled[2])
               selected = TowerType.monocyte;
             break;
          case KeyEvent.VK_4:
             if(towersEnabled[3])
               selected = TowerType.dendrite;
             break;
          case KeyEvent.VK_5:
             if(towersEnabled[4])
               selected = TowerType.basophil;
             break;
          case KeyEvent.VK_6:
             if(towersEnabled[5])
               selected = TowerType.lymphocyte;
             break;
          case KeyEvent.VK_7:
             if(towersEnabled[6])
               selected = TowerType.naturalkiller;
             break;
          case KeyEvent.VK_8:
             if(towersEnabled[7])
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
      for (int i = 0; i < particles.size(); i++) {
         particles.get(i).render(g);
      }
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
      AminoParticle.SP.setCurrentFrame(aminoFrame/FRAMES_PER);
      Font aminoFont = new Font("sans", Font.BOLD, 20), nameFont = new Font("sans", Font.PLAIN, 10);
      Color aminoColor = Color.GREEN, nameColor = Color.RED;
      aminoFrame = (aminoFrame + 1) % (AminoParticle.SP.numFrames() * FRAMES_PER);
      for(int i=0; i<towersEnabled.length; i++){
         if(towersEnabled[i]){
            curr = placeSprite(i+1);
            curr.enact("pre");
            curr.drawRot(g, i*81+41, Engine.getGameHeight()+40, inTh);
            curr.enact("post");
            curr.drawRot(g, i*81+41, Engine.getGameHeight()+40, outTh);
            AminoParticle.SP.draw(g, i*81+12, Engine.getGameHeight()+75);
            g.setFont(aminoFont);
            g.setColor(aminoColor);
            g.drawString(cost(i), i*81+32, Engine.getGameHeight()+91);
            g.setFont(nameFont);
            g.setColor(nameColor);
            g.drawString(name(i), i*81+12, Engine.getGameHeight()+106);
         }
      }
      g.setFont(aminoFont);
      g.setColor(aminoColor);
      g.drawImage(normal, Engine.getWidth()-150, Engine.getHeight()-200, null);
      AminoParticle.SP.draw(g, 5, 5);
      g.drawString(String.valueOf(aminoAcids), 20, 16);
   }
    
   public String cost(int i){
      switch(i){
         case 0: return String.valueOf(Neutrophil.COST);
         case 1: return String.valueOf(Macrophage.COST);
         case 2: return String.valueOf(Monocyte.COST);
         case 3: return String.valueOf(Dendrite.COST);
         case 4: return String.valueOf(Basophil.COST);
         case 5: return String.valueOf(Lymphocyte.COST);
         case 6: return String.valueOf(NaturalKiller.COST);
         case 7: return String.valueOf(KillerT.COST);
      }
      return "N/A";
   }
   public String name(int i){
      switch(i){
         case 0: return "Neutrophil";
         case 1: return "Macrophage";
         case 2: return "Monocyte";
         case 3: return "Dendrite";
         case 4: return "Basophil";
         case 5: return "Lymphocyte";
         case 6: return "Natural Killer";
         case 7: return "Killer T";
      }
      return "N/A";
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
   
   public final void add(Entity e) {
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
         if(e instanceof Particle){
            particles.add((Particle)e);
         }
         entities.add(e);
      }
   }
   
   public Intruder randIntruder(){
      int i = (int)(Math.random()*8);
      switch(i){
         case 0:
            return new Ciliate();
         case 1:
            return new EColi();
         case 2:
            return new Flagellate();
         case 3:
            return new FungalSpore();
         case 4:
            return new Parasite();
         case 5:
            return new PillBacteria();
         case 6:
            return new Spirillum();
         case 7:
            return new Virus();
         default:
            return null;
      }
   }
   
   public Intruder getIntruder(IntruderType t){
      return getIntruder(t.ordinal());
   }
   
   public Intruder getIntruder(int i){
      switch(i){
         case 0:
            return new Ciliate();
         case 1:
            return new EColi();
         case 2:
            return new Flagellate();
         case 3:
            return new FungalSpore();
         case 4:
            return new Parasite();
         case 5:
            return new PillBacteria();
         case 6:
            return new Spirillum();
         case 7:
            return new Virus();
         default:
            return null;
      }
   }
   
   public void add(Shockwave s){
      waves.add(s);
   }

   @Override
   public GameMode escape() {
      if(civilians.size() > 0)
         return new ADVMode(ADVMode.LOC+nextName);
      else
         return new LossScreen();
   }

   @Override
   public void mousePress(MouseEvent e) {
      if(e.getButton() == MouseEvent.BUTTON1){
         ontop = getTower(selected);
         ontop.setLoc(e.getPoint());
      }else{
         Intruder i = randIntruder();
         i.setLoc(e.getPoint());
         add(i);
      }
   }

   @Override
   public void mouseRelease(MouseEvent e) {
      if(ontop != null && (ontop.cost < aminoAcids || Engine.isDebug())){
         add(ontop);
         aminoAcids -= ontop.cost;
      }
      ontop = null;
   }

   @Override
   public void mouseMove(MouseEvent e) {
      if(ontop != null)
         ontop.setLoc(e.getPoint());
   }

   @Override
   public void mouseDrag(MouseEvent e) {
      if(ontop != null)
         ontop.setLoc(e.getPoint());
   }
   
   @Override
   public boolean isDone(){
      return civilians.isEmpty() || framesLeft<=0 && intruders.isEmpty();
   }
}
