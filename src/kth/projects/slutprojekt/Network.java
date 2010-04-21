package kth.projects.slutprojekt;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
	static public final int port = 54555;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(String[].class);
		kryo.register(ChatMessage.class);
	}

	static public class RegisterPlayer {
		public String player;
	}
	
	static public class UpdatePlayers {
		public String[] players;
	}

	static public class ChatMessage {
		public String text;
	}
}
