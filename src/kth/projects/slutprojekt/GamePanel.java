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
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.Timer;

import kth.projects.slutprojekt.Network.PlayerHitted;

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
	private LinkedList<Missile> enemyMissiles = new LinkedList<Missile>();
	private LinkedList<Player> players = new LinkedList<Player>();


    public GamePanel(GameClient gameClient, double x, double y) {
    	addKeyListener(new TAdapter());
        setFocusable(true);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        ingame = true;        

        setSize(800, 600);
        
        //player = new Player("Otto");
        
        asteroid = new Asteroid();

        timer = new Timer(5, this);
        timer.start();
	}
    
    public void setPlayers(LinkedList<Player> players) {
    	this.players = players;
    }
    
    public void playerHit(int ID) {
    	for(int i = 0; i < players.size(); i++) {
    		Player playerHit = players.get(i);
    		if(playerHit.id == ID) {
    			playerHit.setSpawning(true);
    			return;
    		}
    	}
    }
    
    public void updatePlayers(int ID, double x, double y, int angle) {
    	for(int i = 0; i < players.size(); i++) {
    		Player playerUpdate = players.get(i);
    		if(playerUpdate.id == ID) {
    			playerUpdate.x = x;
    			playerUpdate.y = y;
    			playerUpdate.angle = angle;
    			return;
    		}
    	}
    }
    
    public Player getPlayer() {
    	return this.player;
    }
    
    public void addPlayer(int id, double x, double y) {
    	this.player = new Player(id, x, y, "OTto");
    	this.player.setSpawning(false);
    }
    
    public void addEnemy(Player player) {
    	this.players.add(player);
    }
     
    public void addMissile(Missile missile) {
    	this.missiles.add(missile);
    }
    
    public void addEnemyMissile(Missile missile) {
    	this.enemyMissiles.add(missile);
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

            if(player != null && !player.isSpawning()) {	
            	player.draw(g2d); // Draws the ship on the current position	
            }
            
            if(players != null) {
            	for(int i = 0; i < players.size(); i++) {
            		Player enemy = players.get(i);
	            	if(!enemy.isSpawning()){
	            		enemy.draw(g2d);
	            	}
	            }
            }
            
            if(!missiles.isEmpty()) {
	            for (int i = 0; i < missiles.size(); i++) {
	                Missile missile = missiles.get(i);       
	                missile.draw(g2d); // Draws the missile on the current position
	            }
            }
            
            if(!enemyMissiles.isEmpty()) {
	            for (int i = 0; i < enemyMissiles.size(); i++) {
	                Missile missile = enemyMissiles.get(i);       
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
    	if(player != null) {
	    	checkCollisions();
	        moveMissiles(missiles);
	        moveMissiles(enemyMissiles);       
	        player.move(); 
    	}
        repaint();  
    }
    
    private void moveMissiles(LinkedList<Missile> missileList) {
    	if(!missileList.isEmpty()) {
	    	for (int i = 0; i < missileList.size(); i++) {
	            Missile missile = (Missile) missileList.get(i);
	            if (missile.isVisible()) { 
	            	missile.move();
	            }
	            else {
	            	missileList.remove(i);
	            }
	        }
    	}
    }

    private void checkCollisions() {
    	checkPlayerCollisions();
    	checkMissileCollisions(missiles);
    	checkMissileCollisions(enemyMissiles);
    }
    
    private void checkPlayerCollisions() {
    	if(player.getBounds().intersects(asteroid.getBounds())) {
    		PlayerHitted playerHitted = new PlayerHitted();
    		playerHitted.x = player.x;
    		playerHitted.y = player.y;
			playerHitted.id = player.id;
			GameClient.sharedInstance().getClient().sendTCP(playerHitted);
    		player.setSpawning(true);
    	}
    	for(int i = 0; i < enemyMissiles.size(); i++) {
    		Missile enemyMissile = (Missile) enemyMissiles.get(i);
    		if(enemyMissile.getBounds().intersects(player.getBounds())) {
    			enemyMissile.setVisible(false);
    			PlayerHitted playerHitted = new PlayerHitted();
    			playerHitted.missileID = enemyMissile.getPlayerID();
    			playerHitted.id = player.id;
    			player.setSpawning(true);
    			GameClient.sharedInstance().getClient().sendTCP(playerHitted);
    		}	
    	}
    }

	private void checkMissileCollisions(LinkedList<Missile> missileList) {
    	for(int i = 0; i < missileList.size(); i++) {
    		Missile missile = (Missile) missileList.get(i);
    		if(missile.getBounds().intersects(asteroid.getBounds())) {
    			missile.setVisible(false);
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

	public void setPlayerPosition(double x, double y) {
		player.x = x;
		player.y = y;
		player.setSpawning(false);	
	}
}