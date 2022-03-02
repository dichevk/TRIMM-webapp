package nl.utwente.trimm.group42.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import nl.utwente.trimm.group42.models.BodyPack;
import nl.utwente.trimm.group42.models.Shoes;
/**
 * A class that provide functionality for fetching shoes from the database
 *
 */
public class ShoesDao extends Dao{
	/**
	 * A method that fetches the shoe data
	 * @param sid uniquely identifies a shoe in the database
	 * @return Shoes object instance
	 */
	public static Shoes getShoeData(int sid) {
		Shoes shoe = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(url, user, password);
			query = "SELECT *\r\n" + "FROM trimm.Shoes s\r\n"
					+ "WHERE s.sid=?";
			connection.setAutoCommit(false);
			connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);// more performance
			statement = connection.prepareStatement(query);
			statement.setInt(1, sid);
			resultSet = statement.executeQuery();
			connection.commit();
			if (resultSet.next()) {
				shoe = new Shoes(resultSet.getString(2), resultSet.getString(3), resultSet.getInt(4), 
						resultSet.getInt(5), 
						resultSet.getInt(6), resultSet.getInt(7));
			}
			connection.setAutoCommit(true);
			connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			connection.close();
		} catch (SQLException e) {
			try {
				connection.rollback();
				System.err.println("Error loading driver---: " + e);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				System.err.println("Error with rollbacking the transaction");
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return shoe;
	}
	/**
	 * Black box test of of this functionality
	 * @param args
	 */
	public static void main(String[] args) {
		Shoes shoe=ShoesDao.getShoeData(2);
		System.out.println(shoe.getBrand());
		System.out.println();
		System.out.println(shoe.getDropmm()+" drop");
		System.out.println();
		System.out.println(shoe.getForefootmm()+" forefoot");
		System.out.println();
		System.out.println(shoe.getHeelmm()+" heel");
		System.out.println();
		System.out.println(shoe.getName()+" model");
		System.out.println(shoe.getWeightgr()+" weight");
	}

}
