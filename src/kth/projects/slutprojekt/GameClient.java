package kth.projects.slutprojekt;


import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JOptionPane;

import kth.projects.slutprojekt.Network.*;


import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class GameClient {
	//ChatFrame chatFrame;
	Client client;

	public GameClient () {
		client = new Client();
		client.start();

		// For consistency, the classes to be sent over the network are
		// registered by the same method for both the client and server.
		Network.register(client);

		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof UpdatePlayers) {
					//Update players for the current client
					return;
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

		// Request the host from the user.
		String input = (String)JOptionPane.showInputDialog(null, "Host:", "Connect to chat server", JOptionPane.QUESTION_MESSAGE,
			null, null, "localhost");
		if (input == null || input.trim().length() == 0) System.exit(1);
		final String host = input.trim();

		// Request the user's name.
		input = (String)JOptionPane.showInputDialog(null, "Name:", "Connect to chat server", JOptionPane.QUESTION_MESSAGE, null,
			null, "Test");
		if (input == null || input.trim().length() == 0) System.exit(1);
		final String name = input.trim();

		// We'll do the connect on a new thread so the ChatFrame can show a progress bar.
		// Connecting to localhost is usually so fast you won't see the progress bar.
		new Thread("Connect") {
			public void run () {
				try {
					client.connect(5000, host, Network.port);
				} catch (IOException ex) {
					ex.printStackTrace();
					System.exit(1);
				}
				RegisterPlayer registerPlayer = new RegisterPlayer();
				registerPlayer.player = name;
				client.sendTCP(registerPlayer);
			}
		}.start();
	}
}
