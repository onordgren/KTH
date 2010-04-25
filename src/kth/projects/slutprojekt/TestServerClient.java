package kth.projects.slutprojekt;

import junit.framework.TestCase;

public class TestServerClient extends TestCase {
	GameServer server;
	GameClient client1;
	GameClient client2;

	public TestServerClient(String name) {
		super(name);
	}

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testServer() throws Exception {
		server = new GameServer();
		client1 = new GameClient();
	}

}
