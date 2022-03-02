package nl.utwente.trimm.group42.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import nl.utwente.trimm.group42.models.SessionToken;

/**
 * Class which accesses the database and fetches,adds or updates data related to
 * the authentication functionality of the project
 *
 */
public class AuthDao extends Dao {
	/**
	 * A method that fetches the salt for a given user so that it can be used for
	 * hashing the password
	 * 
	 * @param username
	 * @return salt in byte array
	 */
	public static byte[] getSalt(String username) {
		byte[] salt = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT u.salt " + "FROM trimm.user u " + "WHERE u.usern = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			connection.close();
			if (resultSet.next()) {
				salt = resultSet.getBytes(1);
				System.out.println("Found salt for user " + username + ".");
			} else {
				System.err.println("Salt not found for user " + username);
				salt = "Anyk".getBytes();
			}
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
		}
		return salt;

	}

	/**
	 * A method that checks the database for user with the given hashed password
	 * 
	 * @param username
	 * @param pass
	 * @return success;(random sessiontoken) if the password and username are legal
	 * @return fail if the password and username are not legal
	 */
	public static String login(String username, byte[] pass) {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT * " + "FROM trimm.user u " + "WHERE u.usern = ? AND u.passpbkdf = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setBytes(2, pass);
			resultSet = statement.executeQuery();
			connection.close();
			result = resultSet.next();
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
		}
		if (result) {
			System.out.println("User " + username + " successfully logged in.");
			String sessiontoken = new SessionToken(username).sessiontoken;
			SessionTokenDao.setUserToken(sessiontoken, username);
			return "success;" + sessiontoken;
		} else {
			System.err.println("User " + username + " failed to login");
			return "fail";
		}
	}

	/**
	 * A method that "registers the user" into the database with given hashed
	 * password and salt used for the hashing of the password
	 * 
	 * @param username
	 * @param pass
	 * @param salt
	 * @return "success" if there is no user with the same username
	 * @return "fail" if there is a user with the same username
	 */

	public static String register(String username, byte[] pass, byte[] salt) {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT * " + "FROM trimm.user u " + "WHERE u.usern = ?";
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			connection.commit();
			if (!resultSet.next()) {
				query = "INSERT INTO trimm.user VALUES (?,?,?);";
				statement = connection.prepareStatement(query);
				statement.setString(1, username);
				statement.setBytes(2, salt);
				statement.setBytes(3, pass);
				statement.executeUpdate();
				connection.commit();
				connection.setAutoCommit(true);
				connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				connection.close();
				return "success";
			}
			connection.setAutoCommit(true);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			try {
				connection.rollback();// rollback the transaction if there is an exception
				System.err.println("SQL Exception: "+e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.err.println("Rollback went wrong");
			}
		}
		System.err.println("User " + username + " has failed to register");
		return "fail";
	}

	/**
	 * A method that changes the password and the salt used for hashing the password
	 * for a given username
	 * 
	 * @param username
	 * @param pass
	 * @param salt
	 */

	public static void changePass(String username, byte[] pass, byte[] salt) {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "UPDATE trimm.user SET salt=?,passpbkdf=? WHERE usern=?;";
			statement = connection.prepareStatement(query);
			statement.setBytes(1, salt);
			statement.setBytes(2, pass);
			statement.setString(3, username);
			statement.executeUpdate();
			System.out.println("Password of user " + username + " has been changed");
			closeConnection();
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
		}
	}
}