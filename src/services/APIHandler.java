package services;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class APIHandler implements HttpHandler {
	
	public static String ENTRY_POINT = "public/index.html";
	
	@Override
	public void handle(HttpExchange http) throws IOException {
		
		GameDB db = new GameDB(Database.DEV_DB);
		ArrayList<HashMap<String,String>> data = new ArrayList<HashMap<String,String>>();
		
		if (db.connecToDb()) {
			for(Data d: db.retrieveAllData()) {
				PlayerData player = (PlayerData) d;
				data.add(player.getJSON());
			}
		}

		JSONArray json = new JSONArray(data);
		
		String response = new String(json.toString());
		http.getResponseHeaders().set("Content-Type", "application/json");
		
		http.sendResponseHeaders(200, response.length());
		
		OutputStream os = http.getResponseBody();
		os.write(response.getBytes());
		os.close();
		
	}

}
