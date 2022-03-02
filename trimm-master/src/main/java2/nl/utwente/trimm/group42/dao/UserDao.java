package nl.utwente.trimm.group42.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import nl.utwente.trimm.group42.models.BodyPack;
import nl.utwente.trimm.group42.models.Run;
import nl.utwente.trimm.group42.models.SessionToken;
import nl.utwente.trimm.group42.models.User;

/**
 * A class that handles the account functionality with fetching and updating the
 * account information in the database
 *
 */
public class UserDao extends Dao {
	/**
	 * A method that fetches the account information from the database
	 * 
	 * @param sessiontoken uniquely identifies the user session
	 * @return User object instance
	 */
	public static User getAcc(String sessiontoken) {
		User account = null;
		String username = SessionTokenDao.getUser(sessiontoken);
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			connection.setAutoCommit(false);
			 connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			query = "SELECT* FROM trimm.account a WHERE a.username=?";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			connection.commit();
			connection.setAutoCommit(true);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.close();
			if (resultSet.next()) {
				account = new User(resultSet.getString(1), resultSet.getString(2), resultSet.getString(3),
						resultSet.getString(4), resultSet.getInt(5), resultSet.getInt(6));
			}
		} catch (SQLException | ClassNotFoundException e) {
			try {
				System.err.println("Error loading driver---: " + e);
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return account;
	}

	/**
	 * A method that runs a stored procedure on the database that sums the steps of
	 * all runs of a given user
	 * 
	 * @param username identifies the user in the database
	 * @return the total steps
	 */

	public static int totalstepsUser(String username) {
		int totalsteps = 0;
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
		}
		try {
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT* FROM TotalSteps3(?);";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				totalsteps = resultSet.getInt(1);
			}
			connection.close();
		} catch (SQLException e) {
			System.err.println("Error loading driver---: " + e);
		}
		return totalsteps;
	}

	/**
	 * A method that adds the total steps and total distance(km) into a User object
	 * 
	 * @param user
	 * @param sessiontoken uniquely identifies the user session
	 */

	public static void addstepsnKM(User user, String sessiontoken) {
		String username = SessionTokenDao.getUser(sessiontoken);
		// add all the distances of the runs
		double totaldistance = BodyPackDao.totalKm(username);
		int totalsteps = totalstepsUser(username);
		user.setRankm(totaldistance);
		user.setSteps(totalsteps);
	}

	/**
	 * A method that updates the account of a user
	 * 
	 * @param userA the new account information
	 */

	public static void updateUser(User userA) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
		}
		try {
			connection = DriverManager.getConnection(url, user, password);
			query = "UPDATE trimm.account SET firstname=?, lastname=?,weight=?,height=? WHERE username=?";
			 connection.setAutoCommit(false);
			 connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.prepareStatement(query);
			statement.setString(1, userA.getfirstname());
			statement.setString(2, userA.getlastname());
			statement.setInt(3, userA.getweight());
			statement.setInt(4, userA.getheight());
			statement.setString(5, userA.getUsername());
			statement.executeUpdate();
			connection.commit();
			connection.setAutoCommit(true);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			closeConnection();
		} catch (SQLException e) {
			try {
				System.err.println("Error loading driver---: " + e);
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}

	/**
	 * A method that checks if an email is valid. Used for checking if the given
	 * email exists in the database
	 * 
	 * @param email
	 * @return true if the email is in the database/false if it is not in the
	 *         database
	 */

	public static boolean validEmail(String email) {
		loadDriver();
		establishConnection();

		try {
			query = "SELECT a.email FROM trimm.account a WHERE a.email=?";
			statement = connection.prepareStatement(query);
			statement.setString(1, email);
			resultSet = statement.executeQuery();
			result = resultSet.next();
			System.out.println(result);
			closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * A method that maps a token to an email and adds it in the database
	 * @param token identifies the user which wants to change its password
	 * @param email
	 */

	public static void addToken(String token, String email) {
		loadDriver();
		establishConnection();
		try {
			query = "UPDATE trimm.account SET token=? WHERE email=?";
			statement = connection.prepareStatement(query);
			connection.setAutoCommit(false);
			 connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement.setString(1, token);
			statement.setString(2, email);
			statement.executeUpdate();
			// connection.com
			System.out.println("token added");
			connection.commit();
			connection.setAutoCommit(true);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			closeConnection();
		} catch (SQLException e) {
			try {
				System.err.println("Error loading driver---: " + e);
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	/**
	 * A method that checks by given token and email if it exist in the database
	 * @param token
	 * @param email
	 * @return false if there is no such token mapped to that/true otherwise
	 */

	public static boolean validToken(String token, String email) {
		loadDriver();
		establishConnection();
		try {
			query = "SELECT a.token FROM trimm.account a WHERE a.token=? AND a.email=?";
			statement = connection.prepareStatement(query);
			statement.setString(1, token);
			statement.setString(2, email);
			resultSet = statement.executeQuery();
			result = resultSet.next();
			closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * A method that gets the username by a given token from the database
	 * @param token identifies the user who wants to change passwords
	 * @return username of the user requested change of passwords
	 */

	public static String getUsername(String token) {
		String username = "";
		loadDriver();
		establishConnection();
		try {
			query = "SELECT a.username FROM trimm.account a WHERE a.token=?";
			statement = connection.prepareStatement(query);
			connection.setAutoCommit(false);
			 connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement.setString(1, token);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				username = resultSet.getString(1);
			}
			connection.commit();
			connection.setAutoCommit(true);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			closeConnection();
		} catch (SQLException e) {
			try {
				System.err.println("Error loading driver---: " + e);
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		return username;

	}
	/**
	 * A method that adds a user to the account table in the database when a user has successfully registered
	 * @param username
	 * @param email
	 */

	public static void addUser(String username, String email) {
		loadDriver();
		establishConnection();
		try {
			query = "INSERT INTO trimm.account (username,email) VALUES (?,?)";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setString(2, email);
			statement.executeUpdate();
			closeConnection();
			System.out.println("Account added");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * A method that deletes the user from the database wherever it occurs in the tables
	 */
	public static void deleteUser(String username) {
		loadDriver();
		establishConnection();
		try {
		query="DELETE FROM trimm.user u WHERE u.usern=?";
		statement = connection.prepareStatement(query);
		connection.setAutoCommit(false);
		 connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement.setString(1, username);
			statement.executeUpdate();
			connection.commit();
		query="DELETE FROM trimm.account a WHERE a.username=?";
		statement = connection.prepareStatement(query);
		statement.setString(1, username);
		statement.executeUpdate();
		connection.commit();
		connection.setAutoCommit(true);
		connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
		closeConnection();
		System.out.println("account deleted");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			try {
				connection.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("total steps " + totalstepsUser("JF"));
		System.out.println();
		System.out.println("valid: "+validEmail("j.ohn180700@gmail.com"));

	}

}
