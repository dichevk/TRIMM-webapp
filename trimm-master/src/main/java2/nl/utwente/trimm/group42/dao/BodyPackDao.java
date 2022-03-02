package nl.utwente.trimm.group42.dao;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import nl.utwente.trimm.group42.extras.EncryptionPassStronger;
import nl.utwente.trimm.group42.models.BodyPack;
import nl.utwente.trimm.group42.models.SessionToken;
import nl.utwente.trimm.group42.models.Shoes;
/**
 * Class which accesses the database and fetches data
 * related to the overview(metadata) of the run data functionality of the project
 *
 */
public class BodyPackDao extends Dao {
	/**
	 * A method that fetches the meta data from the database by given session token(represents user session)
	 * and run
	 * @param sessiontoken
	 * @param run
	 * @return BodyPack object instance
	 */
	public static BodyPack getBodyPackData(String sessiontoken, int run) {
		String username = SessionTokenDao.getUser(sessiontoken);
		BodyPack bodypack = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT b.Distance,b.Time,b.shoes,b.description,b.date\r\n" + "FROM trimm.BodyPackRuns b\r\n"
					+ "WHERE b.runner = ? AND b.run_no=?\r\n" + "ORDER BY run_no ASC";
			connection.setAutoCommit(false);
			 connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);//more performance
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			statement.setInt(2, run);
			resultSet = statement.executeQuery();
			connection.commit();
			while (resultSet.next()) {
				if (!resultSet.getString(1).equals("Treadmill")) {
					String sc = resultSet.getString(1).replaceAll("km", "");
					double distance = Double.parseDouble(sc);
					
					bodypack = new BodyPack(distance, resultSet.getString(2), null, resultSet.getString(4),
							resultSet.getString(5));
				} else {
					bodypack = new BodyPack(0, resultSet.getString(2), null, resultSet.getString(4),
							resultSet.getString(5));
				}
				connection.setAutoCommit(true);
				 connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
				Shoes shoes=ShoesDao.getShoeData(resultSet.getInt(3));
				bodypack.setShoe(shoes);
			}
			
			//connection.close();

		} catch (SQLException | ClassNotFoundException e) {
			try {
				System.err.println("Error loading driver: " + e);
				connection.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return bodypack;
	}
	/**
	 * A method that uses trimm.BodyPackRuns to calculate how much km in total
	 * the given user has ran
	 * @param username
	 * @return distance
	 */

	public static double totalKm(String username) {
		double distance = 0.0;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT b.Distance\r\n" + "FROM trimm.BodyPackRuns b\r\n" + "WHERE b.runner = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				if (!resultSet.getString(1).equals("Treadmill")) {
					String sc = resultSet.getString(1).replaceAll("km", "");
					double add = Double.parseDouble(sc);
					distance = round(distance + add, 2);
				}
			}
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
		}
		return distance;
	}
	public static ArrayList<String> RunDate(String sessiontoken){
		ArrayList<String> dates = new ArrayList<String>();
		String username = SessionTokenDao.getUser(sessiontoken);
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT b.run_no,b.date\r\n" + "FROM trimm.BodyPackRuns b\r\n" + "WHERE b.runner = ? \r\n"+"ORDER BY b.run_no ASC";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
					dates.add(resultSet.getString(2));
			}
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			System.err.println("Error loading driver: " + e);
		}
		return dates;
	}
	/**
	 * A method that round a double value. It is needed because
	 * Java cannot substract or add double value properly
	 * @param value
	 * @param places
	 * @return
	 */

	public static double round(double value, int places) {
		if (places < 0) {
			throw new IllegalArgumentException();
		}
		BigDecimal bd = BigDecimal.valueOf(value);
		bd = bd.setScale(places, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}
	public static void main(String[] args) {
		byte[] salt = AuthDao.getSalt("CvdB");
		System.out.println("pass");
		byte[] hashedpass = EncryptionPassStronger.HashPassStr("firstrunner18", salt);
		String answer = AuthDao.login("CvdB", hashedpass);
		String[] split= answer.split(";");
		System.out.println(split[1]);
		String token=split[1];
		ArrayList<String> dates=BodyPackDao.RunDate(token);
		for(String key:dates) {
			String key2=key;
			System.out.println(key2);
		}
	}

}
