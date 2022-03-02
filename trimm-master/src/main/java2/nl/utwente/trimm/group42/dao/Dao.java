package nl.utwente.trimm.group42.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
/**
 * A class that provides connection to the database
 * also some instances that are used often in other classes that extend
 * Dao
 *
 */
public class Dao {
	static String host = "bronto.ewi.utwente.nl";
	static String dbName = "dab_dda19202b_7";
	static String url = "jdbc:postgresql://" + host + ":5432/" + dbName;
	static String user = "dab_dda19202b_7";
	static String password = "xCUB/Pe77jyzHeni";
	static Connection connection;
	static PreparedStatement statement;
	static String query;
	static ResultSet resultSet;
	static boolean result;

	public static void loadDriver() {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
		}
	}

	public static void establishConnection() {
		try {
			connection = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
