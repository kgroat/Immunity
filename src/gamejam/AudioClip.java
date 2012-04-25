/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.io.IOException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Scanner;
import org.lwjgl.BufferUtils;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.AL10;
import org.lwjgl.util.WaveData;

/**
 *
 * @author kevingroat
 */
public class AudioClip {

   public static final String LOC = "resources/audio/";
   public static final double DISTANCER = 100;

   public static enum ClipType {

      sfx, music
   };
   private static float[] subGains = new float[ClipType.values().length];
   private static final String[] names;// = {"Error1.ogg", "Error2.ogg", "MoveCursor.ogg", "Select1.ogg", "Select2.ogg", "Select3.ogg", "Select4.ogg"};
   private static final boolean[] loop;// = {false, false, false, false, false, false, false};
   private static final ClipType[] types;// = {ClipType.sfx, ClipType.sfx, ClipType.sfx, ClipType.sfx, ClipType.sfx, ClipType.sfx, ClipType.sfx};
   private static final float[] gains;// = {0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f, 0.5f};
   private static final int[] priorities;
   private static final long[] millis;

   static {
      int count = 0;
      float sfx = 1, mus = 1;
      Scanner in = null;
      try {
         in = FileUtility.loadScanner(LOC + "AudioList.txt");
         count = in.nextInt();
         sfx = in.nextFloat();
         mus = in.nextFloat();
         in.nextLine();
      } catch (Exception e) {
         e.printStackTrace();
      }
      ClipType[] vals = ClipType.values();
      for (int i = 0; i < vals.length; i++) {
         if (vals[i] == ClipType.sfx) {
            subGains[i] = sfx;
         }
         if (vals[i] == ClipType.music) {
            subGains[i] = mus;
         }
      }
      names = new String[count];
      loop = new boolean[count];
      types = new ClipType[count];
      gains = new float[count];
      priorities = new int[count];
      millis = new long[count];
      if (in != null) {
         try {
            count = 0;
            while (in.hasNext()) {
               String next = in.nextLine().trim();
               int place = next.indexOf(";");
               names[count] = next.substring(0, place);
               next = next.substring(place + 1).trim();
               place = next.indexOf(";");
               String test = next.substring(0, place).toUpperCase();
               next = next.substring(place + 1).trim();
               if (test.contains("SFX")) {
                  types[count] = ClipType.sfx;
               } else {
                  types[count] = ClipType.music;
               }
               place = next.indexOf(";");
               test = next.substring(0, place).toUpperCase();
               loop[count] = test.contains("LOOP");
               next = next.substring(place + 1).trim();
               place = next.indexOf(";");
               test = next.substring(0, place).toUpperCase();
               gains[count] = Float.parseFloat(test);
               next = next.substring(place + 1).trim();
               next = next.replace(";", "");
               priorities[count] = Integer.parseInt(next);
               count++;
            }
         } catch (Exception e) {
            e.printStackTrace();
         }
      }
   }
   private static HashMap<String, AudioClip> map = new HashMap<String, AudioClip>();
   /**
    * Maximum data buffers we will need.
    */
   public static final int NUM_BUFFERS = names.length;
   /**
    * Maximum emissions we will need.
    */
   public static final int NUM_SOURCES = names.length;
   /**
    * Buffers hold sound data.
    */
   private static final IntBuffer buffer = BufferUtils.createIntBuffer(NUM_BUFFERS);
   /**
    * Sources are points emitting sound.
    */
   private static final IntBuffer source = BufferUtils.createIntBuffer(NUM_BUFFERS);
   /**
    * Position of the source sound.
    */
   private static final FloatBuffer sourcePos = BufferUtils.createFloatBuffer(3);
   /*
    * These are 3D cartesian vector coordinates. A structure or class would be a
    * more flexible of handling these, but for the sake of simplicity we will
    * just leave it as is.
    */
   /**
    * Velocity of the source sound.
    */
   private static final FloatBuffer sourceVel = BufferUtils.createFloatBuffer(3);
   /**
    * Position of the listener.
    */
   private static final FloatBuffer listenerPos = BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f});
   /**
    * Velocity of the listener.
    */
   private static final FloatBuffer listenerVel = BufferUtils.createFloatBuffer(3).put(new float[]{0.0f, 0.0f, 0.0f});
   /**
    * Orientation of the listener. (first 3 elements are "at", second 3 are
    * "up") Also note that these should be units of '1'.
    */
   private static final FloatBuffer listenerOri = BufferUtils.createFloatBuffer(6).put(new float[]{0.0f, 0.0f, -1.0f, 0.0f, 1.0f, 0.0f});
   private static float masterGain = 1;
   private static boolean works;
   protected static float[] listPosVals = {0, 0, 0};

   static {
      // Initialize OpenAL and clear the error bit.
      try {

         AL.create();
         AL10.alGetError();

         AL10.alGenBuffers(buffer);
         final int D = (int) Math.log10(names.length) + 1;
         if (AL10.alGetError() == AL10.AL_NO_ERROR) {
            WaveData waveFile;
            OggData oggFile;
            for (int i = 0; i < names.length; i++) {
               if (names[i].toLowerCase().endsWith(".wav")) {
                  System.out.println("Loading audio clip (" + String.format("%0" + D + "d/%0" + D + "d", i + 1, names.length) + "): " + names[i]);
                  waveFile = WaveData.create(FileUtility.loadURL(LOC + names[i]));
                  AL10.alBufferData(buffer.get(i), waveFile.format, waveFile.data, waveFile.samplerate);
                  millis[i] = waveFile.data.capacity() * ((waveFile.format == AL10.AL_FORMAT_STEREO16) ? 250L : ((waveFile.format != AL10.AL_FORMAT_MONO8) ? 500L : 1000L)) / waveFile.samplerate;
                  System.out.println(waveFile.format);
                  System.out.println(millis[i]);
                  waveFile.dispose();
               } else if (names[i].toLowerCase().endsWith(".ogg")) {
                  System.out.println("Loading audio clip (" + String.format("%0" + D + "d/%0" + D + "d", i + 1, names.length) + "): " + names[i]);
                  oggFile = new OggData(FileUtility.loadURL(LOC + names[i]));
                  AL10.alBufferData(buffer.get(i), oggFile.format, oggFile.data, oggFile.samplerate);
                  millis[i] = oggFile.data.capacity() * ((oggFile.format == AL10.AL_FORMAT_STEREO16) ? 250L : ((oggFile.format != AL10.AL_FORMAT_MONO8) ? 500L : 1000L)) / oggFile.samplerate;
                  System.out.println(oggFile.format);
                  System.out.println(millis[i]);
               }
            }
         }

         // Bind buffers into audio sources.
         AL10.alGenSources(source);

         if (AL10.alGetError() == AL10.AL_NO_ERROR) {
            for (int i = 0; i < names.length; i++) {
               AL10.alSourcei(source.get(i), AL10.AL_BUFFER, buffer.get(i));
               AL10.alSourcef(source.get(i), AL10.AL_PITCH, 1.0f);
               AL10.alSourcef(source.get(i), AL10.AL_GAIN, 1.0f);
               sourcePos.put(new float[]{0, 0, 0});
               sourceVel.put(new float[]{0, 0, 0});
               sourcePos.flip();
               sourceVel.flip();
               AL10.alSource(source.get(i), AL10.AL_POSITION, sourcePos);
               AL10.alSource(source.get(i), AL10.AL_VELOCITY, sourceVel);
               AL10.alSourcei(source.get(i), AL10.AL_LOOPING, (loop[i] ? AL10.AL_TRUE : AL10.AL_FALSE));
               AL10.alSourcef(source.get(i), AL10.AL_REFERENCE_DISTANCE, (float) DISTANCER);
               AL10.alSourcei(source.get(i), AL10.AL_SOURCE_RELATIVE, AL10.AL_TRUE);
            }
         }

         String tName, first, last;
         for (int i = 0; i < names.length; i++) {
            tName = names[i];
            if (map.containsKey(tName)) {
               int count = 1;
               first = tName.substring(0, tName.lastIndexOf("."));
               last = tName.substring(tName.lastIndexOf("."));
               tName = first + "_" + count + last;
               while (map.containsKey(tName)) {
                  count++;
                  tName = first + "_" + count + last;
               }
            }
            map.put(tName, new AudioClip(i).setPriority(priorities[i]));
         }
         listenerOri.flip();
         AL10.alListener(AL10.AL_ORIENTATION, listenerOri);
         listenerPos.flip();
         AL10.alListener(AL10.AL_POSITION, listenerPos);
         listenerVel.flip();
         AL10.alListener(AL10.AL_VELOCITY, listenerVel);
         AL10.alDistanceModel(AL10.AL_INVERSE_DISTANCE_CLAMPED);
         works = true;
      } catch (Exception e) {
         works = false;
      }
   }
   protected int sourceNum, priority;
   protected boolean looping, forceEnd;
   protected float[] position, velocity;
   protected float pitch, gain, baseGain;
   protected ClipType type;

   public AudioClip(int tSourceNum) {
      if (tSourceNum < 0) {
         sourceNum = (tSourceNum + Integer.MAX_VALUE / names.length * names.length) % names.length;
      } else {
         sourceNum = tSourceNum % names.length;
      }
      looping = loop[sourceNum];
      pitch = 1.0f;
      gain = baseGain = gains[sourceNum];
      position = new float[]{0.0f, 0.0f, 0.0f};
      velocity = new float[]{0.0f, 0.0f, 0.0f};
      type = types[sourceNum];
   }

   public AudioClip(String s) {
      this(map.get(s));
   }

   public AudioClip(AudioClip origin) {
      sourceNum = origin.sourceNum;
      looping = origin.looping;
      pitch = origin.pitch;
      gain = origin.gain;
      baseGain = origin.baseGain;
      position = new float[3];
      System.arraycopy(origin.position, 0, position, 0, 3);
      velocity = new float[3];
      System.arraycopy(origin.velocity, 0, velocity, 0, 3);
      type = origin.type;
   }

   private AudioClip setPriority(int p) {
      priority = p;
      return this;
   }

   public void forcePlay(boolean restart, boolean setProperties) {
      if (works) {
         if (type == ClipType.music) {
            AudioClip tmp;
            for (int i = 0; i < names.length; i++) {
               tmp = get(i);
               if (tmp.type == ClipType.music) {
                  tmp.pause();
               }
            }
         }
         forceEnd = false;
         if (setProperties) {
            putProperties();
         }
         if (restart) {
            AL10.alSourceStop(source.get(sourceNum));
         }
         AL10.alSourcePlay(source.get(sourceNum));
      }
   }

   public void tryPlay(boolean restart, boolean setProperties) {
      if (works) {
         boolean isHigh = true;
         if (type == ClipType.music) {
            AudioClip tmp;
            for (int i = 0; i < names.length; i++) {
               tmp = get(i);
               if (tmp != this) {
                  if (tmp.type == ClipType.music) {
                     if (priority >= tmp.priority) {
                        tmp.pause();
                     } else if (tmp.isPlaying()) {
                        isHigh = false;
                     }
                  }
               }
            }
         }
         if (isHigh) {
            if (!isPlaying()) {
               forceEnd = false;
               forcePlay(restart, setProperties);
            }
         }
      }
   }

   public void stop() {
      forceEnd = true;
      AL10.alSourceStop(source.get(sourceNum));
   }

   public boolean pause() {
      boolean tmp = isPlaying();
      AL10.alSourcePause(source.get(sourceNum));
      return tmp;
   }

   public boolean isPlaying() {
      return AL10.alGetSourcei(source.get(sourceNum), AL10.AL_SOURCE_STATE) == AL10.AL_PLAYING;
   }

   public float getGain() {
      return gain;
   }

   public void setGain(float gain) {
      this.gain = gain;
   }

   public void setRelativeGain(float gain) {
      this.gain = baseGain * gain;
   }

   public void setGainFromDist(double dist) {
      this.gain = baseGain * (float) Math.min(1, DISTANCER / (dist + .001));
   }

   public float getPitch() {
      return pitch;
   }

   public long getMillis() {
      return millis[sourceNum];
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   public float[] getPosition() {
      return position;
   }

   public void setPosition(float... position) {
      this.position = position;
   }

   public int getSourceNum() {
      return sourceNum;
   }

   public void setSourceNum(int sourceNum) {
      this.sourceNum = sourceNum;
   }

   public float[] getVelocity() {
      return velocity;
   }

   public void setVelocity(float... velocity) {
      this.velocity = velocity;
   }

   public void putProperties() {
      for (int i = 0; i < 3; i++) {
         sourcePos.put(-position[i]);
         sourceVel.put(-velocity[i]);
      }
      sourcePos.flip();
      sourceVel.flip();
      AL10.alSource(source.get(sourceNum), AL10.AL_POSITION, sourcePos);
      AL10.alSource(source.get(sourceNum), AL10.AL_VELOCITY, sourceVel);
      AL10.alSourcef(source.get(sourceNum), AL10.AL_PITCH, pitch);
      AL10.alSourcef(source.get(sourceNum), AL10.AL_GAIN, gain * masterGain * subGains[type.ordinal()]);
      AL10.alSourcei(source.get(sourceNum), AL10.AL_LOOPING, (looping ? AL10.AL_TRUE : AL10.AL_FALSE));

   }

   public void putPitch() {
      AL10.alSourcef(source.get(sourceNum), AL10.AL_PITCH, pitch);
   }

   public void putGain() {
      AL10.alSourcef(source.get(sourceNum), AL10.AL_GAIN, gain * masterGain * subGains[type.ordinal()]);
   }

   public void putPosition() {
      sourcePos.clear();
      sourcePos.put(position);
      sourcePos.flip();
      System.out.println(position[0] + " / " + position[1] + " / " + position[2]);
      AL10.alSource(source.get(sourceNum), AL10.AL_POSITION, sourcePos);
   }

   public void putVelocity() {
      for (int i = 0; i < 3; i++) {
         sourceVel.put(-velocity[i]);
      }
      sourceVel.flip();
      AL10.alSource(source.get(sourceNum), AL10.AL_VELOCITY, sourceVel);
   }

   public void putPositionAndVelocity() {
      for (int i = 0; i < 3; i++) {
         sourcePos.put(-position[i]);
         sourceVel.put(-velocity[i]);
      }
      sourcePos.flip();
      sourceVel.flip();
      AL10.alSource(source.get(sourceNum), AL10.AL_POSITION, sourcePos);
      AL10.alSource(source.get(sourceNum), AL10.AL_VELOCITY, sourceVel);
   }

   public void putLooping() {
      AL10.alSourcei(source.get(sourceNum), AL10.AL_LOOPING, (looping ? AL10.AL_TRUE : AL10.AL_FALSE));
   }

   public boolean isPertinent(float... vals) {
      return !isPlaying() || dist(vals) < dist(position);
   }

   public double dist(float[] in) {
      double total = 0;
      for (int i = 0; i < in.length; i++) {
         total += in[i] * in[i];
      }
      return Math.sqrt(total);
   }

   public static AudioClip get(int i) {
      return map.get(names[i]);
   }

   public static AudioClip get(String s) {
      return map.get(s);
   }

   public static float getMasterGain() {
      return masterGain;
   }

   public static void setMasterGain(float masterGain) {
      AudioClip.masterGain = masterGain;
      putAllGain();
   }

   public static void setSubGain(ClipType t, float musicGain) {
      AudioClip.subGains[t.ordinal()] = musicGain;
      putAllGain();
   }

   public static float getSubGain(ClipType t) {
      return subGains[t.ordinal()];
   }

   public AudioClip clone() {
      return new AudioClip(this);
   }

   private static void putAllGain() {
      AudioClip tmp;
      for (int i = 0; i < names.length; i++) {
         tmp = get(names[i]);
         tmp.putGain();
      }
   }

   private static class OggData {
      // temporary buffer

      public ByteBuffer data = ByteBuffer.allocateDirect(4096 * 8);
      public int samplerate, format;

      public OggData(URL u) throws IOException {
         OggInputStream oggInputStream = new OggInputStream(u);

         format = oggInputStream.getFormat();
         samplerate = oggInputStream.getRate();

         int count = 0;
         try {
            int bytesRead = 1;
            do {
               bytesRead = oggInputStream.read(data, 0, data.capacity());
               if (bytesRead > 0) {
                  count += bytesRead;
               }
            } while (bytesRead > 0);
         } catch (IOException e) {
            e.printStackTrace();
         }
         data = ByteBuffer.allocateDirect(count);
         data.rewind();
         oggInputStream = new OggInputStream(u);

         try {
            int bytesRead = oggInputStream.read(data, 0, data.capacity());
            if (bytesRead >= 0) {
               data.rewind();
            }
         } catch (IOException e) {
            e.printStackTrace();
         }
      }
   }

   protected static IntBuffer createIntBuffer(int size) {
      ByteBuffer temp = ByteBuffer.allocateDirect(4 * size);
      temp.order(ByteOrder.nativeOrder());
      return temp.asIntBuffer();
   }

   public void startAfter(String s, final boolean force, final boolean restart, final boolean setProperties) {
      final AudioClip other = get(s);
      if (other != null) {
         Thread t = new Thread() {

            @Override
            public synchronized void run() {
               while (other.isPlaying()) {
                  try {
                     wait(5);
                  } catch (InterruptedException ex) {
                     break;
                  }
               }
               if (!other.forceEnd) {
                  if (force) {
                     forcePlay(restart, setProperties);
                  } else {
                     tryPlay(restart, setProperties);
                  }
               }
            }
         };
         t.start();
      }
   }

   public static void setListeningPosition(float... vals) {
      float[] tmp;
      if (vals.length < 3) {
         tmp = new float[3];
         System.arraycopy(vals, 0, tmp, 0, vals.length);
      } else {
         tmp = vals;
      }
      listPosVals = tmp;
      listenerPos.clear();
      listenerPos.put(vals);
      listenerPos.flip();
      AL10.alListener(AL10.AL_POSITION, listenerPos);
   }

   public static void setListeningVelocity(float... vals) {
      float[] tmp;
      if (vals.length < 3) {
         tmp = new float[3];
         System.arraycopy(vals, 0, tmp, 0, vals.length);
      } else {
         tmp = vals;
      }
      listenerVel.clear();
      listenerVel.put(vals);
      listenerVel.flip();
      AL10.alListener(AL10.AL_VELOCITY, listenerVel);
   }

   public ClipType getType() {
      return type;
   }
}