package com.StellarShoes.utils;


import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * To obtain a new connection from the connections pool.
 * @author Mutasem Alhariri 
 *         07/04/2020
 *         Version 1.0
 *
 */
public class DatabaseConnector {

	
	private static Connection conn;
	
	/**
	 * To get a new connection from the data source.
	 * @return a new connection.
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException{
		try {
		Context context = new InitialContext();
		DataSource ds = (DataSource) context.lookup("java:comp/env/jdbc/CyberLife Database");
		
		conn = ds.getConnection();
		
		}catch(NamingException ne) {
			ne.printStackTrace();
		}
		return conn;
	}
	/**
	 * To close database connection
	 * @param con
	 */
	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
		}
	}
	
}
