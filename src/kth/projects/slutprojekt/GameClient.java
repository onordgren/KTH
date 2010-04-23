package kth.projects.slutprojekt;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JLabel;

import kth.projects.slutprojekt.Network.RegisterPlayer;
import kth.projects.slutprojekt.Network.UpdatePlayers;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.minlog.Log;

public class GameClient {
	private static GameClient gameClient;
	//ChatFrame chatFrame;
	Client client;
	GamePanel gamePanel;
	
	HashMap<Integer, Player> players = new HashMap<Integer, Player>();
	
	public static GameClient sharedInstance() {
		if(gameClient == null) {
			gameClient = new GameClient();
		}
		return gameClient;
	}

	private GameClient () {
		client = new Client();
		gamePanel = new GamePanel(this);
		client.start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof UpdatePlayers) {
					
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
					client.connect(5000, "localhost", Network.port);
				} catch (IOException ex) {
					ex.printStackTrace();
					System.exit(1);
				}
				RegisterPlayer registerPlayer = new RegisterPlayer();
				registerPlayer.player = "otto";
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
