package services;

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
	
	String getName() { return this.name; };
	int getScore() { return this.score; };
	int getId() { return this.id; };
}
