package services;

import java.sql.*;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * The actual game database service.
 * 
 * @author Von Divino
 */

public class GameDB implements Database {

	/// PROPERTIES ///
	
	private Connection conn;
	private Statement stmt;
	private String database;
	
	public GameDB(String database) {
		this.database = database;
	}

	/**
	 * Connect the application to the database service.
	 * For this setup, the application will be using sqlite API to handle operations.
	 * 
	 * @return success The status of connection.
	 * @author Von Divino
	 */
	
	@Override
	public boolean connectToDb() {
		boolean success = false;

		try {
			Class.forName("org.sqlite.JDBC");
			this.conn = DriverManager.getConnection(this.database);
			this.stmt = conn.createStatement();
			success = !success;
		} catch (Exception eString) {
		    System.err.println("Could not init JDBC driver - driver not found");
		}
		return success;
	}
	
	/**
	 * Close the database connection.
	 * 
	 * @return success The status of close connection.
	 * @author Von Divino
	 */
	
	@Override
	public boolean closeDb() {
		boolean success = false;
		try {
			//this.stmt.close();
			this.conn.close();
			success = !success;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return success;
	}

	/**
	 * Create a players table in the application.
	 * 
	 * @return success The status of table creation.
	 * @author Von Divino
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
	 * Retrieve and return all players from the players table.
	 * 
	 * @return players The committed players data from the table.
	 * @author Von Divino
	 */
	
	@Override
	public ArrayList<Data> retrieveAllData() {
		ArrayList<Data> players = new ArrayList<Data>();
		try {
			String sql = "SELECT * FROM players ORDER BY score DESC;";
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
	 * @author Von Divino
	 */
	
	@Override
	public Data retrieveData() {
		
		PlayerData player = null;
		return (Data) player;
		
	}

	/**
	 * Insert new player in the players table.
	 * 
	 * @param model The player to be committed.
	 * @return player The newly committed player data.
	 * @author Von Divino
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
	 * Update the player in the players table.
	 * 
	 * @param model The player to be committed.
	 * @param filter The field to be filtered.
	 * @return player The committed player data.
	 * @author Von Divino
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
	 * Delete the player in the players table.
	 * 
	 * @param model The player to be committed.
	 * @return success The status of deletion.
	 * @author Von Divino
	 */
	
	@Override
	public boolean deleteData(Data model) {
		boolean success = true;
		return success;
	}
}
