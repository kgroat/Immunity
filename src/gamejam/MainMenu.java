/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
/**
 *
 * @author Rurouni
 */
public class MainMenu extends GameMode {
    
    public MainMenu(){
        buttons = new ArrayList();
        int cx = ((Engine.getWidth()-300)/2);
        int cy = ((Engine.getHeight()-200)/2);
        buttons.add(new Button("String Test", new Font("serif", Font.PLAIN, 20), Engine.getWidth()/2, cy+10, Button.CENTER) {

            @Override
            public void onClick() {
                Engine.start2();
                System.out.println("STUFFFFFFF\n");
            }
        });
    }
    
    ArrayList<Button> buttons;
    
    @Override
    public void press(KeyEvent e) {
       
    }

    @Override
    public void release(KeyEvent e) {
        
    }

    @Override
    public void mousePress(MouseEvent e) {
        System.out.println(e.getX() + " / " + e.getY());
        
        for (int i=0; i<buttons.size(); i++){
            System.out.println(buttons.get(i).x + " / " + buttons.get(i).y);
            System.out.println(buttons.get(i).width + " / " + buttons.get(i).height);

            if (buttons.get(i).getBounds().contains(e.getPoint())){
                buttons.get(i).onClick();
            }
        }
        
    }

    @Override
    public void mouseRelease(MouseEvent e) {
       
    }

    @Override
    public void render(Graphics2D g) {
        g.setColor(new Color (0,0,70));
        g.fillRect(0, 0, Engine.getWidth(), Engine.getHeight());
        g.setColor(Color.white);
        int cx = ((Engine.getWidth()-300)/2);
        int cy = ((Engine.getHeight()-200)/2);
        g.fillRect(cx, cy, 300, 200);
        g.setColor(new Color(255, 255, 0, 100));
        for (int j=0; j<buttons.size(); j++){
            g.fill(buttons.get(j).getBounds());
            buttons.get(j).render(g);
        }
       
    }

    @Override
    public void update() {
        
    }

    @Override
    public GameMode escape() {
        return this;
    }
    
    
}
