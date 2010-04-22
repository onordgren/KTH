package kth.projects.slutprojekt;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;

import kth.projects.slutprojekt.Network.*;


import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;

public class GameServer {
	Server server;

	public GameServer () throws IOException {
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
					// Ignore the object if a client has already registered a name. This is
					// impossible with our client, but a hacker could send messages at any time.
					if (connection.name != null) 
						return;
					
					// Ignore the object if the name is invalid.
					String player = ((RegisterPlayer)object).player;
					
					if (player == null) 
						return;
					
					player = player.trim();
					if (player.length() == 0) 
						return;
					
					// Store the name on the connection.
					connection.name = player;
					
					// Send a "connected" message to everyone except the new client.
//					ChatMessage chatMessage = new ChatMessage();
//					chatMessage.text = player + " connected.";
//					server.sendToAllExceptTCP(connection.getID(), chatMessage);
					// Send everyone a new list of connection names.
					updatePlayers();
					return;
				}
			}
		});
		
		server.bind(Network.port);
		server.start();

		// Open a window to provide an easy way to stop the server.
		JFrame frame = new JFrame("Chat Server");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosed (WindowEvent evt) {
				server.stop();
			}
		});
		frame.getContentPane().add(new JLabel("Close to stop the chat server."));
		frame.setSize(320, 200);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	void updatePlayers () {
		// Collect the names for each connection.
		Connection[] connections = server.getConnections();
		ArrayList<String> players = new ArrayList<String>(connections.length);
		for (int i = connections.length - 1; i >= 0; i--) {
			GameConnection connection = (GameConnection)connections[i];
			players.add(connection.name);
		}
		// Send the names to everyone.
		UpdatePlayers updatePlayers = new UpdatePlayers();
		updatePlayers.players = (String[])players.toArray(new String[players.size()]);
		server.sendToAllTCP(updatePlayers);
	}

	// This holds per connection state.
	static class GameConnection extends Connection {
		public String name;
	}

	public static void main (String[] args) throws IOException {
		Log.set(Log.LEVEL_DEBUG);
		new GameServer();
	}
}

