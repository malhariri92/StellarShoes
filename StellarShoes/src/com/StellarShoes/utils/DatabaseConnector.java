package com.StellarShoes.utils;


import java.sql.Connection;
import java.sql.SQLException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
public class DatabaseConnector {

	
	private static Connection conn;
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
	
	public static void close(Connection con) {
		try {
			con.close();
		} catch (Exception ex) {
		}
	}
}
