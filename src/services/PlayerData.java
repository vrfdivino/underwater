package services;

import java.util.HashMap;

public class PlayerData extends Data {
	private int id;
	private String name;
	private int score;
	
	PlayerData(String name) {
		this.name  = name;
		this.score = 0;
	}
	
	PlayerData(int id, String name, int score) {
		this.id    = id;
		this.name  = name;
		this.score = score;
	}
	
	void setScore(int _score) {
		this.score = _score;
	}
	
	public String getName() { return this.name; };
	public int getScore() { return this.score; };
	int getId() { return this.id; };
	HashMap<String, String> getJSON() {
		
		HashMap<String,String> player = new HashMap<String, String>();
		
		player.put("id", String.valueOf(this.id));
		player.put("name", this.name);
		player.put("score", String.valueOf(this.score));
		
		return player;
	}
}
