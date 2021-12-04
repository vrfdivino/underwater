package services;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;


import com.sun.net.httpserver.HttpServer;

public class GameServer {
	
	public static int PORT = 8000;
	public static String ROOT_URI = "/";
	public static String API_URI = "/api";
	
	private HttpServer server;
	
	public GameServer() throws Exception {
		
		this.server = HttpServer.create(new InetSocketAddress(GameServer.PORT), 0);
		this.server.createContext(GameServer.ROOT_URI, new ServerHandler());
		this.server.createContext(GameServer.API_URI, new APIHandler());
		
	}
	
	public void listen() {
		
		this.server.setExecutor(null);
		this.server.start();
		System.out.println("Server is running on PORT " + PORT);
		System.out.println("Visit http://localhost:" + PORT);
		
	}


	public static void main(String[] args) throws Exception {
		
		GameServer gameServer = new GameServer();
		gameServer.listen();
		
	}

}
