/* ===========================================================================
Created:	2015/08/15 - https://github.com/yes4me/
Author:		Thomas Nguyen - thomas_ejob@hotmail.com
Purpose:	My library
Requires:	mysql-connector-java-5.1.31-bin.jar
=========================================================================== */

package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQL {
	private static final String DB_NAME		= "zillow"; 
	private static final String DB_USERNAME	= "root"; 
	private static final String DB_PASSWORD	= "MySQL"; 

	private Connection conn = null;

	//========================================================
	// DB METHODS
	//========================================================
	public Connection connectDB() {
		try {
			//load the JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			//Class.forName("com.mysql.jdbc.Driver").newInstance();

			String DB_url	= "jdbc:mysql://localhost/"+ DB_NAME;
			conn			= DriverManager.getConnection (DB_url, DB_USERNAME, DB_PASSWORD);
			System.out.println ("Database connection established");
		}
		catch (Exception e) {
			System.err.println ("Cannot connect to database server");
			System.out.println(e);
			return null;
		}
		return conn;
	}
	public boolean closeDB() {
		if (conn != null)
		{
			try
			{
				conn.close ();
				System.out.println ("Database connection terminated");
			}
			catch (Exception e) {
				return false;
			}
		}
		return true;
	}

	//========================================================
	// SQL METHODS
	//========================================================
	public int executeUpdate(String sql) {
		int nbRecordsUpdated = 0;
		if (conn==null)
			return nbRecordsUpdated;

		try {
			Statement statement		= conn.createStatement();
			nbRecordsUpdated		= statement.executeUpdate (sql);
			statement.close();
			//conn.commit();		//MySQL has auto commmit by default 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return nbRecordsUpdated;
	}
	public void executeQuery(String sql) {
		if (conn==null)
			return;

		//on statement object, call executeQuery()==>ResultSet or executeUpdate()==>return int
		try {
			//statements allow to issue SQL queries to the database
			Statement statement	= conn.createStatement();
			ResultSet resultSet	= statement.executeQuery(sql); 	

			//resultSet gets the result of the SQL query
			while (resultSet.next()) {
				int id			= resultSet.getInt("id");
				String name		= resultSet.getString("name");
				String email	= resultSet.getString("email");
				//Date registration_date = resultSet.getDate("registration_date");

				System.out.println("id: "+ id);
				System.out.println("name: "+ name);
				System.out.println("email: "+ email);
				//System.out.println("Date: " + registration_date);
			}

			resultSet.close();
			statement.close();
		}
		catch (Exception e)
		{
			System.err.println ("Cannot connect to database server");
			System.out.println(e);
		}
	}

	public static void main(String[] args) throws SQLException {
		MySQL mySQL = new MySQL();
		Connection conn	= mySQL.connectDB(); 

		int i = mySQL.executeUpdate("UPDATE student SET email='xxx' WHERE id=3");
		System.out.println("number of rows updated="+ i);

		mySQL.executeQuery("SELECT * FROM zillow.student");		//SELECT * FROM student also work

		mySQL.closeDB();
	}
}