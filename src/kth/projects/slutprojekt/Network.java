package kth.projects.slutprojekt;

import java.util.ArrayList;
import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
	static public final int port = 54555;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(String[].class);
		kryo.register(RegisterPlayer.class);
		kryo.register(UpdatePlayers.class);
		kryo.register(UpdatePosition.class);
		kryo.register(Player.class);
		kryo.register(RegisterResponse.class);
		kryo.register(NewMissile.class);
		kryo.register(ArrayList.class);
		kryo.register(Missile.class);
	}

	static public class RegisterPlayer {
		public String player;
	}
	
	static public class RegisterResponse {
		public double x, y;
	}
	
	static public class UpdatePlayers {
		public HashMap<Integer, Player> players;
	}
	
	static public class UpdatePosition {
		public double x, y;
		public int angle, id;
	}

	static public class NewMissile {
		public double x, y, thrust;
		public int angle;
	}
	
	static public class PlayerPosition {
		int id;
		double x, y;
		int angle;
	}
}
