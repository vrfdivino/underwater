package services;

import java.util.*;

public interface Database {
	
	static final String DEV_DB  = "jdbc:sqlite:test.db";
	static final String PROD_DB = "jdbc:sqlite:prod.db";
	static final String DB_API  = "org.sqlite.JDBC";
	
	boolean connecToDb();
	boolean createTable();
	ArrayList<Data> retrieveAllData();
	Data retrieveData();
	Data insertData(Data model);
	Data updateData(Data model, String field, String value);;
	boolean deleteData(Data model);
	boolean closeDb();
}
