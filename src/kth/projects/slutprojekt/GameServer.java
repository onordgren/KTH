package kth.projects.slutprojekt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

import kth.projects.slutprojekt.Network.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class GameServer extends Thread {
	private boolean running = true;
	GameServer gameServer;
	Server server;
	HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	LinkedList missiles = new LinkedList();
	
	public GameServer () throws Exception{
       
	
	}

	// This holds per connection state.
	static class GameConnection extends Connection {
		public String name;
	}
	
	public HashMap<Integer, Player> getPlayers() {
		return this.players;
	}

	public static void main (String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		try {
			new GameServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		 
		this.gameServer = this;
		server = new Server() {
			protected Connection newConnection () {
				// By providing our own connection implementation, we can store per
				// connection state without a connection ID to state look up.
				return new GameConnection();
			}
		};
		
		Network.register(server);
		
		server.addListener(new Listener() {
			public void received (Connection c, Object object) {
				// We know all connections for this server are actually GameConnections.
				GameConnection connection = (GameConnection)c;
				
				if (object instanceof RegisterPlayer) {
					RegisterPlayer rPlayer = (RegisterPlayer) object;
					// set new player a random position
					double x = (Math.random() * 800);
					double y = (Math.random() * 600);
					
					// Skapar en ny spelare
					Player player = new Player(x, y, rPlayer.name);	
					 
					// Skickar startpositioner till den nya spelaren
					RegisterResponse registerResponse = new RegisterResponse();
					registerResponse.x = player.x;
					registerResponse.y = player.y;				
					connection.sendTCP(registerResponse);
					
					//Skickar samtliga anslutna spelare till den nya spelaren
					Iterator it = players.entrySet().iterator();
					while(it.hasNext()) {
						Player addPlayer = (Player)((Map.Entry)it.next()).getValue();
						UpdatePlayers updatePlayers = new UpdatePlayers();
						updatePlayers.x = addPlayer.x;
						updatePlayers.y = addPlayer.y;
						//updatePlayers.name = addPlayer.name;
						connection.sendTCP(updatePlayers);
					}
					
					if(!players.isEmpty()) {
						//Skickar ut den nya spelaren till samtliga anslutna spelare
						NewPlayer newPlayer = new NewPlayer();
						newPlayer.name = rPlayer.name;
						newPlayer.x = rPlayer.x;
						newPlayer.y = rPlayer.y;
						server.sendToAllExceptTCP(c.getID(), newPlayer);
					}
					
					player.setID(c.getID());					
					players.put(c.getID(), player);
									
					return;
				}
				
				if (object instanceof NewMissile) {
					
					NewMissile missile = (NewMissile) object;
					
					//Missile newMissile = new Missile(missile.x, missile.y, missile.angle, missile.thrust);
					
					//missiles.add(newMissile);
					
					System.out.println("On x pos: " + missile.x);
					
					server.sendToAllTCP(missile);
					
					return;
				}
				
				if(object instanceof UpdatePosition) {
					// update new player positions on server list
					UpdatePosition updatePlayer = (UpdatePosition) object;
					PlayerPosition playerPosition = new PlayerPosition();
					
					playerPosition.id = updatePlayer.id;
					playerPosition.x = updatePlayer.x;
					playerPosition.y = updatePlayer.y;
					playerPosition.angle = updatePlayer.angle;
					// send new position to all other clients
					server.sendToAllExceptTCP(c.getID(), playerPosition);
					return;
				}
				if(object instanceof PlayerHitted) {
					// Updates all players that the player has been hit
					PlayerHitted playerHitted = (PlayerHitted) object;
					
					server.sendToAllExceptTCP(c.getID(), playerHitted); 
				}
			}

			public void disconnected (Connection c) {

			}
		});
		
		try {
			server.bind(Network.TCPport);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		server.start();		

		
		
		while(running) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

