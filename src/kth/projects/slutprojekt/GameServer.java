package kth.projects.slutprojekt;

import java.io.IOException;
import java.util.LinkedList;

import kth.projects.slutprojekt.Network.NewEnemyMissile;
import kth.projects.slutprojekt.Network.NewMissile;
import kth.projects.slutprojekt.Network.NewPlayer;
import kth.projects.slutprojekt.Network.PlayerHitted;
import kth.projects.slutprojekt.Network.PlayerPosition;
import kth.projects.slutprojekt.Network.RegisterPlayer;
import kth.projects.slutprojekt.Network.RegisterResponse;
import kth.projects.slutprojekt.Network.UpdatePlayers;
import kth.projects.slutprojekt.Network.UpdatePosition;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class GameServer {
	private boolean running = true;
	GameServer gameServer;
	Server server;
	LinkedList<Player> players = new LinkedList<Player>();
	LinkedList<Missile> missiles = new LinkedList<Missile>();
	
	public GameServer () throws Exception{
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
					Player player = new Player(c.getID(), x, y, rPlayer.name);	
					 
					// Skickar startpositioner till den nya spelaren
					RegisterResponse registerResponse = new RegisterResponse();
					registerResponse.id = player.id;
					registerResponse.x = player.x;
					registerResponse.y = player.y;				
					connection.sendTCP(registerResponse);
					
					//Skickar samtliga anslutna spelare till den nya spelaren
					for(int i = 0; i < players.size(); i++) {
						Player addPlayer = players.get(i);
						UpdatePlayers updatePlayers = new UpdatePlayers();
						updatePlayers.id = addPlayer.id;
						updatePlayers.x = addPlayer.x;
						updatePlayers.y = addPlayer.y;
						updatePlayers.angle = addPlayer.angle;
						connection.sendTCP(updatePlayers);
					}
					
					if(!players.isEmpty()) {
						//Skickar ut den nya spelaren till samtliga anslutna spelare
						NewPlayer newPlayer = new NewPlayer();
						newPlayer.name = player.name;
						newPlayer.x = player.x;
						newPlayer.y = player.y;
						server.sendToAllExceptTCP(c.getID(), newPlayer);
					}
					
					player.setID(c.getID());					
					players.add(player);
									
					return;
				}
				
				if (object instanceof NewMissile) {				
					NewMissile missile = (NewMissile) object;
					NewEnemyMissile enemyMissile = new NewEnemyMissile();	
					enemyMissile.x = missile.x;
					enemyMissile.y = missile.y;
					enemyMissile.angle = missile.angle;
					enemyMissile.thrust = missile.thrust;
					
					server.sendToAllExceptTCP(c.getID(), enemyMissile);
					connection.sendTCP(missile);
					Log.info(Integer.toString(missile.playerID));
					return;
				}
				
				if(object instanceof UpdatePosition) {
					// update new player positions on server list
					UpdatePosition updatePlayer = (UpdatePosition) object;
					PlayerPosition playerPosition = new PlayerPosition();
					
					//Updates the server list of players positions
					for(int i = 0; i < players.size(); i++) {
						Player player = players.get(i);
						if(player.id == updatePlayer.id) {
							player.x = updatePlayer.x;
							player.y = updatePlayer.y;
							player.angle = updatePlayer.angle;
						}
					}
					
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
					UpdatePosition updatePosition = new UpdatePosition();
					updatePosition.x = (Math.random() * 800);
					updatePosition.y = (Math.random() * 600);

					connection.sendTCP(updatePosition);
					
					
					PlayerPosition playerPosition = new PlayerPosition();

					playerPosition.id = playerHitted.id;
					playerPosition.x = updatePosition.x;
					playerPosition.y = updatePosition.y;
					playerPosition.angle = updatePosition.angle;
					
					// send new position to all other clients
					server.sendToAllExceptTCP(c.getID(), playerPosition);
					return;
				}
			}

			public void disconnected (Connection c) {

			}
		});
		
		server.bind(Network.TCPport);
		server.start();
		
		
		while(running) {
			Thread.sleep(100);
		}
	
	}

	// This holds per connection state.
	static class GameConnection extends Connection {
		public String name;
	}
	
	public LinkedList<Player> getPlayers() {
		return this.players;
	}

	public static void main (String[] args) throws IOException {
		Log.set(Log.LEVEL_INFO);
		try {
			new GameServer();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

