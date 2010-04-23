package kth.projects.slutprojekt;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import kth.projects.slutprojekt.Network.*;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class GameServer {
	private boolean running = true;
	Server server;
	HashMap players = new HashMap();
	ArrayList<Missile> missiles = new ArrayList<Missile>();
	
	public GameServer () throws Exception{
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
					Player player = new Player();
					UpdatePlayers updatePlayers = new UpdatePlayers();
					
					player.setID(c.getID());
					Ship ship = player.getShip();
					
					c.sendTCP(updatePlayers);
					
					players.put(player.getID(), player);
					
					RegisterResponse r = new RegisterResponse();
					r.x = ship.x;
					r.y = ship.y;
					
					c.sendTCP(r);
									
					return;
				}
				
				if (object instanceof NewMissile) {
					
					NewMissile missile = (NewMissile) object;
					
					Missile newMissile = new Missile(missile.x, missile.y, missile.angle, missile.thrust);
					
					missiles.add(newMissile);
					
					System.out.println("On x pos: " + missile.x);
									
					return;
				}
				
				if(object instanceof PlayerPosition) {
					// update new player positions on server list
					PlayerPosition pp = (PlayerPosition) object;
					Player player = (Player) players.get(c.getID());
					Ship ship = player.getShip();
					ship.x = pp.x;
					ship.y = pp.y;
					ship.angle = pp.angle;
					// send new position to all other clients
					server.sendToTCP(c.getID(), object);
				}
			}

			public void disconnected (Connection c) {

			}
		});
		
		server.bind(Network.port);
		server.start();
		
		
		while(running) {
			Thread.sleep(100);
		}
	
	}

	// This holds per connection state.
	static class GameConnection extends Connection {
		public String name;
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
}

