package nl.utwente.trimm.group42.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A class that extends Dao and handles the fetching and adding data to the
 * database
 *
 */
public class VerificationDao extends Dao {
	/**
	 * A method that maps email to token used for verifying email when registering
	 * @param email of user trying to register
	 * @param token generated for this user
	 */
	public static void AddEmailnToken(String email, String token) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
		}
		try {
			connection = DriverManager.getConnection(url, user, password);
			query = "INSERT INTO trimm.emailverify VALUES (?,?)";
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.prepareStatement(query);
			statement.setString(1, email);
			statement.setString(2, token);
			statement.executeUpdate();
			connection.commit();
			System.out.println("Added");
			connection.setAutoCommit(true);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.close();
		} catch (SQLException e) {
			try {
				connection.rollback();// rollback the transaction if there is an exception
				System.err.println("An SQL exception occured. Rolling back transaction"+e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.err.println("Rollback went wrong");
			}
		}
	}
	/**
	 * A method that checks if the verification requested is a valid one and the deletes it
	 * @param token sended to the user by email
	 * @param email of the user
	 * @return true if the token and email are valid/false otherwise
	 */

	public static boolean checkToken(String token, String email) {
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
		}
		try {
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT* FROM trimm.emailverify v WHERE v.token=? AND v.email=?";
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			statement = connection.prepareStatement(query);
			statement.setString(1, token);
			statement.setString(2, email);
			resultSet = statement.executeQuery();
			connection.commit();
			result = resultSet.next();
			if (result) {
				query = "DELETE FROM trimm.emailverify WHERE email=?";
				statement = connection.prepareStatement(query);
				statement.setString(1, email);
				statement.executeUpdate();
			}
			connection.commit();
			connection.setAutoCommit(true);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.close();
		} catch (SQLException e) {
			try {
				connection.rollback();// rollback the transaction if there is an exception
				System.err.println("An SQL exception occured. Rolling back transaction"+e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.err.println("Rollback went wrong");
			}
		}
		return result;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("add");
		AddEmailnToken("rnd@rnd.com", "iawdgiqdgqwgdwq2");
		System.out.println("check: "+checkToken("iawdgiqdgqwgdwq2", "rnd@rnd.com"));

	}

}
