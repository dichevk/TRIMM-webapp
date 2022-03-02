package nl.utwente.trimm.group42.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nl.utwente.trimm.group42.models.BodyPack;
import nl.utwente.trimm.group42.models.Run;
import nl.utwente.trimm.group42.models.SessionToken;
import nl.utwente.trimm.group42.models.StepData;

/**
 * A class that fetches run data from the database
 *
 */
public class RunnerDataDao extends Dao {
	/**
	 * A method that runs a query that counts the number of runs for a given user
	 * 
	 * @param sessiontoken uniquely identifies the user session
	 * @return number of runs this use has
	 */
	public static int getCount(String sessiontoken) {
		String username = SessionTokenDao.getUser(sessiontoken);
		int count = 0;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT COUNT(*) " + "FROM (SELECT DISTINCT s.run " + "FROM trimm.step_data s "
					+ "WHERE s.username=?) as count";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			resultSet.next();
			count = resultSet.getInt(1);
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return count;
	}
	/**
	 * A method that gets all the data of a run by given sessiontoken and run
	 * @param sessiontoken uniquely  identifies the uses session
	 * @param run 
	 * @return Run object instance with all the information for a run
	 */

	public static Run getRun(String sessiontoken, int run) {
		String username = SessionTokenDao.getUser(sessiontoken);
		Run run1 = new Run();
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			connection.setAutoCommit(false);
			 connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			query = "SELECT *\r\n" + "FROM trimm.step_data s\r\n" + "WHERE username = ?\r\n" + "AND run = ?\r\n"
					+ "ORDER BY step ASC";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setInt(2, run);
			resultSet = statement.executeQuery();
			connection.commit();
			while (resultSet.next()) {
				StepData sdata = new StepData(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3),
						resultSet.getInt(4), resultSet.getInt(5), resultSet.getString(6), resultSet.getString(7),
						resultSet.getDouble(8), resultSet.getDouble(9), resultSet.getDouble(10),
						resultSet.getDouble(11), resultSet.getDouble(12), resultSet.getDouble(13),
						resultSet.getDouble(14), resultSet.getDouble(15), resultSet.getString(16),
						resultSet.getString(17), resultSet.getDouble(18), resultSet.getDouble(19),
						resultSet.getDouble(20), resultSet.getDouble(21), resultSet.getDouble(22),
						resultSet.getDouble(23), resultSet.getDouble(24), resultSet.getDouble(25));
				run1.addStepData(sdata);
			}
			connection.setAutoCommit(true);
			 connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			BodyPack metadata = BodyPackDao.getBodyPackData(sessiontoken, run);
			run1.addMetaData(metadata);
			
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.err.println("Error with rollbacking the transaction");
			}
			
		}
		return run1;
	}
	/**
	 * A method that gets a run but without the meta data and shoes
	 * it is done that way to provide more performance for functionality that does not need all the data
	 * @param sessiontoken uniquely identifies the user session
	 * @param run
	 * @return Run object instance without the meta data and the shoes
	 */

	public static Run getRunWithoutMeta(String sessiontoken, int run) {
		String username = SessionTokenDao.getUser(sessiontoken);
		Run run1 = new Run();
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT *\r\n" + "FROM trimm.step_data s\r\n" + "WHERE username = ?\r\n" + "AND run = ?\r\n"
					+ "ORDER BY step ASC";
			connection.setAutoCommit(false);
			 connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);//more performance
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setInt(2, run);
			resultSet = statement.executeQuery();
			connection.commit();
			while (resultSet.next()) {
				StepData sdata = new StepData(resultSet.getString(1), resultSet.getInt(2), resultSet.getString(3),
						resultSet.getInt(4), resultSet.getInt(5), resultSet.getString(6), resultSet.getString(7),
						resultSet.getDouble(8), resultSet.getDouble(9), resultSet.getDouble(10),
						resultSet.getDouble(11), resultSet.getDouble(12), resultSet.getDouble(13),
						resultSet.getDouble(14), resultSet.getDouble(15), resultSet.getString(16),
						resultSet.getString(17), resultSet.getDouble(18), resultSet.getDouble(19),
						resultSet.getDouble(20), resultSet.getDouble(21), resultSet.getDouble(22),
						resultSet.getDouble(23), resultSet.getDouble(24), resultSet.getDouble(25));
				run1.addStepData(sdata);
			}
			connection.setAutoCommit(true);
			 connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);//return to default settings
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			try {
				connection.rollback();
				System.err.println("Error loading driver---: " + e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.err.println("rollback did not succeded" + e1);
			}
			
		}
		return run1;
	}

}
