package nl.utwente.trimm.group42.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * A class that handles the session tokens by fetching or setting the sessions
 *
 */
public class SessionTokenDao extends Dao {

	public static int EXPIERY = 60 * 60; // seconds

	/**
	 * 
	 * @param sessionToken The token of the user that is requested
	 * @return The user that has SessionToken sessionToken or null if the token is
	 *         not allocated to a user
	 */
	public static String getUser(String sessionToken) {
		try {
			Date currentTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			connection.setAutoCommit(false);
			 connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			query = "SELECT u.usern, u.session_expire_time\r\n FROM trimm.user u\r\n WHERE u.sessiontoken = ?;";
			statement = connection.prepareStatement(query);
			statement.setString(1, sessionToken);
			statement.execute();
			resultSet = statement.executeQuery();
			connection.commit();
			query = "UPDATE trimm.user SET session_expire_time = ? WHERE sessiontoken = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, sdf.format(addSecondsToDate(currentTime, EXPIERY)));
			statement.setString(2, sessionToken);
			statement.execute();
			connection.commit();
			connection.setAutoCommit(true);
			 connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.close();
			if (resultSet.next()) {
				Date expireTime = sdf.parse(resultSet.getString(2));
				if (currentTime.before(expireTime)) {
					return resultSet.getString(1);
				}
			}
		} catch (ParseException | ClassNotFoundException | SQLException e) {
			try {
				connection.rollback();
				e.printStackTrace();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		return null;
	}

	/**
	 * Sets the SessionToken of user username to sessionToken
	 * 
	 * @param sessionToken The token that should be used for the user
	 * @param username     The user who should have his/her SessionToken changed
	 */
	public static void setUserToken(String sessionToken, String username) {
		try {
			Date currentTime = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "UPDATE trimm.user SET session_expire_time = ?, sessiontoken = ? WHERE usern = ?";
			statement = connection.prepareStatement(query);
			statement.setString(1, sdf.format(addSecondsToDate(currentTime, EXPIERY)));
			statement.setString(2, sessionToken);
			statement.setString(3, username);
			statement.executeUpdate();
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param date    The date/time to which the seconds should be added
	 * @param seconds The amount of seconds that should be added to the Date
	 * @return The date that has increased by seconds
	 */
	public static Date addSecondsToDate(Date date, int seconds) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.SECOND, seconds);
		return calendar.getTime();
	}
	public static void deleteToken(String sessiontoken) {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "UPDATE trimm.user SET sessiontoken=NULL,session_expire_time=NULL WHERE sessiontoken=?";
			statement = connection.prepareStatement(query);
			statement.setString(1, sessiontoken);
			statement.executeUpdate();
			System.out.println("token deleted");
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	public static String gettokenByusername(String username) {
		String sessiontoken="";
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT u.sessiontoken FROM trimm.user u WHERE u.usern=?";
			statement = connection.prepareStatement(query);
			statement.setString(1, username);
			resultSet=statement.executeQuery();
			while(resultSet.next()) {
				sessiontoken=resultSet.getString(1);
			}
			connection.close();
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return sessiontoken;
	}
}
