package kth.projects.slutprojekt;

import java.util.HashMap;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;

// This class is a convenient place to keep things common to both the client and server.
public class Network {
	static public final int TCPport = 54555;
	static public final int UDPport = 54577;

	// This registers objects that are going to be sent over the network.
	static public void register (EndPoint endPoint) {
		Kryo kryo = endPoint.getKryo();
		kryo.register(HashMap.class);
		kryo.register(RegisterPlayer.class);
		kryo.register(UpdatePlayers.class);
		kryo.register(UpdatePosition.class);
		kryo.register(Player.class);
		kryo.register(RegisterResponse.class);
		kryo.register(NewMissile.class);
		kryo.register(NewPlayer.class);
		kryo.register(Missile.class);
		kryo.register(PlayerPosition.class);
	}

	static public class RegisterPlayer {
		public double x, y;
		public String name;
		public HashMap<Integer, Player> players;
	}
	
	static public class RegisterResponse {
		public double x, y;
	}
	
	static public class UpdatePlayers {
		public double x, y;
		public String name;
		public int angle;
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
	static public class NewPlayer {
		public int angle;
		public double x, y;
		public String name;
	}
	
	static public class PlayerPosition {
		public int id, angle;
		public double x, y;
	}
}
