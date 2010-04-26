package kth.projects.slutprojekt;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import kth.projects.slutprojekt.Network.*;


import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener {

    /**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;
	private Timer timer;
    private Asteroid asteroid;
    private Player player;
    private boolean ingame;
    private int B_WIDTH;
    private int B_HEIGHT;
	private LinkedList<Missile> missiles = new LinkedList<Missile>();
	private HashMap<Integer, Player> players = new HashMap<Integer, Player>();


    public GamePanel(GameClient gameClient, double x, double y) {
    	addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        ingame = true;

        setSize(800, 600);
        
        player = new Player(x, y, "Otto");
        
        asteroid = new Asteroid();

        timer = new Timer(5, this);
        timer.start();
	}
    
    public void setPlayers(HashMap<Integer, Player> players) {
    	this.players = players;
    }
    
    public void playerHit(int ID) {
    	Iterator it = players.entrySet().iterator();
    	while(it.hasNext()) {
    		Player player = (Player)((Map.Entry)it.next()).getValue();
    		if(player.id == ID) {
    			player.setVisible(false);
    			return;
    		}
    	}
    }
    
    public void updatePlayers(int ID, double x, double y, int angle) {
    	Iterator it = players.entrySet().iterator();
    	while(it.hasNext()) {
    		Player player = (Player)((Map.Entry)it.next()).getValue();
    		if(player.id == ID) {
    			player.x = x;
    			player.y = y;
    			player.angle = angle;
    			return;
    		}
    	}
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public void addPlayer(Player player) {
    	this.players.put(player.getID(), player);
    }
     
    public void addMissle(Missile missile) {
    	this.missiles.add(missile);
    }

	public void addNotify() {
        super.addNotify();
        B_WIDTH = getWidth();
        B_HEIGHT = getHeight();   
    }

    public void paint(Graphics g) {
        super.paint(g);
        if (ingame) {
            Graphics2D g2d = (Graphics2D)g;

            	
            player.draw(g2d); // Draws the ship on the current position
            
            if(players != null) {
	            Iterator it = players.entrySet().iterator();
	            while(it.hasNext()) {
	            	Player p = (Player)((Map.Entry)it.next()).getValue();
	            	if(p.isVisible()){
	            		p.draw(g2d);
	            	}
	            }
            }
            
            if(!missiles.isEmpty()) {
	            for (int i = 0; i < missiles.size(); i++) {
	                Missile missile = missiles.get(i);       
	                missile.draw(g2d); // Draws the missile on the current position
	            }
            }
            
            if(asteroid.isVisible()) {
            	asteroid.draw(g2d);
            }
            g2d.setColor(Color.WHITE);


        } else {
            String msg = "Game Over";
            Font small = new Font("Helvetica", Font.BOLD, 14);
            FontMetrics metr = this.getFontMetrics(small);

            g.setColor(Color.white);
            g.setFont(small);
            g.drawString(msg, (B_WIDTH - metr.stringWidth(msg)) / 2,
                         B_HEIGHT / 2);
        }

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    /**
     * Runs every time the timer ticks. Moves all the objects and repaints the panel.
     */
    public void actionPerformed(ActionEvent e) {
        for (int i = 0; i < missiles.size(); i++) {
            Missile missile = (Missile) missiles.get(i);
            if (missile.isVisible()) { 
            	missile.move();
            }
            else {
            	missiles.remove(i);
            }
        }
        
        player.move();
        checkCollisions();
        repaint();  
    }

    private void checkCollisions() {
    	checkShipCollisions();
    	checkMissileCollisions();
    }
    
    private void checkShipCollisions() {
    	if(player.getBounds().intersects(asteroid.getBounds())) {
    		player.setVisible(false);
    		ingame = false;
    	}
    }
    
    private void checkMissileCollisions() {
    	for(int i = 0; i < missiles.size(); i++) {
    		Missile missile = (Missile) missiles.get(i);
    		if(missile.getBounds().intersects(asteroid.getBounds())) {
    			missile.setVisible(false);
    		}
    		if(missile.getBounds().intersects(player.getBounds())) {
    			PlayerHitted playerHitted = new PlayerHitted();
    			playerHitted.id = player.getID();
    			GameClient.sharedInstance().getClient().sendTCP(playerHitted);
    		}
    		missile.checkOuterBounds(B_WIDTH, B_HEIGHT);
    	}
    }
    

    private class TAdapter extends KeyAdapter {

        public void keyReleased(KeyEvent e) {
            player.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            player.keyPressed(e);
        }
    }
}