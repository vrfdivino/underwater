package services;

import java.sql.*;
import java.util.ArrayList;

public class GameDB implements Database {
	
	private Connection conn;
	private Statement stmt;
	private String database;
	
	public GameDB(String database) {
		this.database = database;
	}

	/**
	 * 
	 * Connect the application to the database service.
	 * For this setup, the application will be using sqlite API to handle operations.
	 * 
	 * @return success The status of connection.
	 * 
	 */
	
	@Override
	public boolean connecToDb() {
		
		boolean success = false;
		
		try {
			Class.forName(Database.DB_API);
			this.conn = DriverManager.getConnection(this.database);
			this.stmt = conn.createStatement();
			success = !success;
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return success;
	
	}
	
	/**
	 *
	 * Close the database connection.
	 * 
	 * @return success The status of close connection.
	 * 
	 */
	
	@Override
	public boolean closeDb() {

		boolean success = false;
		
		try {
			this.stmt.close();
			this.conn.close();
			success = !success;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return success;
		
	}

	/**
	 *
	 * Create a players table in the application.
	 * 
	 * @return success The status of table creation.
	 * 
	 */
	
	@Override
	public boolean createTable() {
		

		boolean success = false;
		
		try {
			String sql = "CREATE TABLE IF NOT EXISTS players (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL, score INTEGER NOT NULL);";
			this.stmt.executeUpdate(sql);
			success = !success;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return success;
		
	}

	/**
	 *
	 * Retrieve and return all players from the players table.
	 * 
	 * @return players The committed players data from the table.
	 * 
	 */
	
	@Override
	public ArrayList<Data> retrieveAllData() {
		
		ArrayList<Data> players = new ArrayList<Data>();
		
		try {
			String sql = "SELECT * FROM players;";
			ResultSet res = this.stmt.executeQuery(sql);
			while(res.next()) {
				players.add((Data) new PlayerData(res.getInt("id"), res.getString("name"), res.getInt("score")));
			}
			res.close();
			this.stmt.close();
		} catch (Exception e) {
			players = null;
			System.out.println(e.getMessage());
		}
		
		return players;
		
	}

	/**
	 *
	 * Retrieve and return the specific player from the players table.
	 * 
	 * @return player The committed player data from the table.
	 * 
	 */
	
	@Override
	public Data retrieveData() {
		
		PlayerData player = null;
		return (Data) player;
		
	}

	/**
	 * 
	 * Insert new player in the players table.
	 * 
	 * @param model The player to be committed.
	 * @return player The newly committed player data.
	 * 
	 */
	
	@Override
	public Data insertData(Data model) {
		
		PlayerData player = (PlayerData) model; 
		
		try {
			String sql = "INSERT INTO players (name, score) VALUES ( '" + player.getName() + "', " + player.getScore() + ");";
			this.stmt.executeUpdate(sql);
			this.stmt.close();
			this.conn.commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			player = null;
		}
		
		return (Data) player;
		
	}
	
	/**
	 *
	 * Update the player in the players table.
	 * 
	 * @param model The player to be committed.
	 * @param filter The field to be filtered.
	 * @return player The committed player data.
	 *
	 */
	
	@Override
	public Data updateData(Data model, String field, String value) {
		
		PlayerData player = (PlayerData) model;
		
		try {
			String sql = "UPDATE players SET " + value + " = " + player.getScore() + " where " + field + " = " + player.getId() + ";";
			this.stmt.executeUpdate(sql);
			this.conn.commit();	
		} catch (Exception e) {
			System.out.println(e.getMessage());
			player = null;
		}
		
		return player;
		
	}

	/**
	 *
	 * Delete the player in the players table.
	 * 
	 * @param model The player to be committed.
	 * @return success The status of deletion.
	 * 
	 */
	
	@Override
	public boolean deleteData(Data model) {

		boolean success = true;
		return success;
		
	}

	public static void main(String[] args) {
		
		GameDB db = new GameDB(Database.DEV_DB);

		// ONLY CALLED ONCE!
		if (db.connecToDb()) {
			
			// CAN BE CALLED ONCE! BUT OK TO CALL MULTIPLE TIMES.
			// sample to create table
			db.createTable();
			
			// sample to insert a player
			PlayerData testPlayer = new PlayerData("test_user");
			db.insertData(testPlayer);
			
			// sample to retrieve all players
			for (Data player: db.retrieveAllData()) {
				PlayerData p = (PlayerData) player;
				System.out.println("Name: " + p.getName() + " score: " + p.getScore() + " id: " + p.getId());
			}
			
			// sample to update a player
			testPlayer.setScore(200);
			db.updateData(testPlayer, "id", "score");
			
			// ONLY CALLED ONCE!
			// sample to close connection
			db.closeDb();
			
		}
	}
}
