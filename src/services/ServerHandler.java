package services;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class ServerHandler implements HttpHandler {
	
	public static String ENTRY_POINT = "public/index.html";
	
	@Override
	public void handle(HttpExchange http) throws IOException {
		
		String response = new String(Files.readAllBytes(Paths.get(ServerHandler.ENTRY_POINT)));
		http.getResponseHeaders().set("Content-Type", "text/html");
		
		http.sendResponseHeaders(200, response.length());
		
		OutputStream os = http.getResponseBody();
		os.write(response.getBytes());
		os.close();
		
	}

}
