package kth.projects.slutprojekt;


import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import kth.projects.slutprojekt.Network.NewMissile;
import kth.projects.slutprojekt.Network.NewPlayer;
import kth.projects.slutprojekt.Network.PlayerPosition;
import kth.projects.slutprojekt.Network.RegisterPlayer;
import kth.projects.slutprojekt.Network.RegisterResponse;
import kth.projects.slutprojekt.Network.UpdatePlayers;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class GameClient {
	private static GameClient gameClient;
	Player player;
	Client client;
	GamePanel gamePanel;
	public double startX;
	public double startY;
	ArrayList<Missile> missiles;
	
	HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	
	public static GameClient sharedInstance() {
		if(gameClient == null) {
			gameClient = new GameClient();
		}
		return gameClient;
	}

	public GameClient () {
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

				}
				if(object instanceof UpdatePlayers) {
					UpdatePlayers updatePlayers = (UpdatePlayers) object;
					Player newPlayer = new Player(updatePlayers.x, updatePlayers.y, updatePlayers.name);
					gamePanel.addPlayer(newPlayer);
				}
						
				if(object instanceof NewMissile) {
					NewMissile missile = (NewMissile) object;
					
					Missile newMissile = new Missile(missile.x, missile.y, missile.angle, missile.thrust);
					
					gamePanel.addMissle(newMissile);				
					
					System.out.println("Added missile");
				}
				if(object instanceof NewPlayer) {
					NewPlayer player = (NewPlayer) object;
					
					Player newPlayer = new Player(player.x, player.y, player.name);
					
					gamePanel.addPlayer(newPlayer);
				}
				
				if(object instanceof PlayerPosition) {
					PlayerPosition playerPosition = (PlayerPosition) object;
			    	gamePanel.updatePlayers(playerPosition.id, playerPosition.x, playerPosition.y, playerPosition.angle);
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
					client.connect(5000, "localhost", Network.TCPport);
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

	public static void main (String[] args) {
		Log.set(Log.LEVEL_DEBUG);
		sharedInstance();
	}

}
