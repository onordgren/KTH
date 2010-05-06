package kth.projects.slutprojekt;


import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.JFrame;

import kth.projects.slutprojekt.Network.NewEnemyMissile;
import kth.projects.slutprojekt.Network.NewMissile;
import kth.projects.slutprojekt.Network.NewPlayer;
import kth.projects.slutprojekt.Network.PlayerHitted;
import kth.projects.slutprojekt.Network.PlayerPosition;
import kth.projects.slutprojekt.Network.RegisterPlayer;
import kth.projects.slutprojekt.Network.RegisterResponse;
import kth.projects.slutprojekt.Network.UpdatePlayers;
import kth.projects.slutprojekt.Network.UpdatePosition;
import kth.projects.slutprojekt.Network.UpdateScore;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class GameClient {
	
	private static GameClient gameClient;
	Client client;
	GamePanel gamePanel;
	public double startX;
	public double startY;
	
	public static GameClient sharedInstance(String ip) {
		if(gameClient == null) {
			gameClient = new GameClient(ip);
		}
		return gameClient;
	}


	public GameClient (final String IP) {

		
		client = new Client();
		client.start();
		gamePanel = new GamePanel(this, startX, startY);

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof RegisterResponse) {
					RegisterResponse response = (RegisterResponse) object;
					
					startX = response.x;
					startY = response.y;
					
					gamePanel.addPlayer(response.id, response.x, response.y);
				}
				if(object instanceof UpdatePlayers) {
					UpdatePlayers updatePlayers = (UpdatePlayers) object;
					Player newPlayer = new Player(updatePlayers.id, updatePlayers.x, updatePlayers.y, updatePlayers.name);
					gamePanel.addEnemy(newPlayer);
				}
						
				if(object instanceof NewMissile) {
					NewMissile missile = (NewMissile) object;
					
					Missile newMissile = new Missile(missile.playerID, missile.x, missile.y, missile.angle, missile.thrust);
					
					Log.info("Own missile added");
					gamePanel.addMissile(newMissile);				
				}
				if(object instanceof NewEnemyMissile) {
					NewEnemyMissile missile = (NewEnemyMissile) object;
					
					Missile newMissile = new Missile(missile.enemyID, missile.x, missile.y, missile.angle, missile.thrust);
					
					Log.info("Enemy missile added");
					gamePanel.addEnemyMissile(newMissile);				
				}
				if(object instanceof NewPlayer) {
					NewPlayer player = (NewPlayer) object;
					
					Player newPlayer = new Player(player.id, player.x, player.y, player.name);
					
					gamePanel.addEnemy(newPlayer);
				}
				
				if(object instanceof PlayerPosition) {
					PlayerPosition playerPosition = (PlayerPosition) object;
			    	gamePanel.updatePlayers(playerPosition.id, playerPosition.x, playerPosition.y, playerPosition.angle);
				}
				
				if(object instanceof PlayerHitted) {
					PlayerHitted playerHitted = (PlayerHitted) object;
					gamePanel.playerHit(playerHitted.id);
				}
				if(object instanceof UpdatePosition) {
					UpdatePosition updatePosition = (UpdatePosition) object;
					gamePanel.setPlayerPosition(updatePosition.x, updatePosition.y);
				}
				if(object instanceof UpdateScore) {
					UpdateScore updateScore = (UpdateScore) object;
					gamePanel.updateScore(updateScore.id, updateScore.score);
				}
			}

			public void disconnected (Connection connection) {
				EventQueue.invokeLater(new Runnable() {
					public void run () {
						// Closing the frame calls the close listener which will stop the client's update thread.
						//chatFrame.dispose();
					}
				});
			}
		});
		
		
		// Open a window to provide an easy way to stop the server.
		JFrame frame = new JFrame("Chat Server");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed (WindowEvent evt) {
				client.stop();
				System.exit(1);
			}
		});
		frame.getContentPane().add(gamePanel);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

		// We'll do the connect on a new thread so the ChatFrame can show a progress bar.
		// Connecting to localhost is usually so fast you won't see the progress bar.
		new Thread("Connect") {
			public void run () {
				try {
					client.connect(5000, IP, Network.TCPport);
				} catch (IOException ex) {
					ex.printStackTrace();
					System.exit(1);
				}
				RegisterPlayer registerPlayer = new RegisterPlayer();
				registerPlayer.name = "otto";
				client.sendTCP(registerPlayer);
				
			}
		}.start();
	}
	
	public Client getClient() {
		return client;
	}


}
