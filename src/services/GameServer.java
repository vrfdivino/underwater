package services;

import java.net.InetSocketAddress;
import com.sun.net.httpserver.HttpServer;

/**
 * The server that will handle the viewing of project documentation.
 * 
 * @author Von Divino
 */

public class GameServer implements Server {
	
	/// PROPERTIES ///
	public static int PORT = 8000;
	public static String ROOT_URI = "/";
	
	private HttpServer server;
	
	/**
	 * Creates a new game server
	 * 
	 * @throws Exception
	 */
	public GameServer() throws Exception {
		this.server = HttpServer.create(new InetSocketAddress(GameServer.PORT), 0);
		this.server.createContext(GameServer.ROOT_URI, new ServerHandler());
	}
	
	/**
	 * Start and listen with the server.
	 * 
	 * @author Von Divino
	 */
	
	@Override
	public void listen() {
		this.server.setExecutor(null);
		this.server.start();
		System.out.println("Server is running on PORT " + PORT);
		System.out.println("Visit http://localhost:" + PORT);
	}
}
