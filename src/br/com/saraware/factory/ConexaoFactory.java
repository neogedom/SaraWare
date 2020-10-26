package br.com.saraware.factory;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexaoFactory {
	private static Connection connection;
	private static final String USUARIO = "root";
	private static final String SENHA = "123456";
	private static final String URL = "jdbc:mysql://localhost:3306/saraware";

public static Connection getConnection() throws Exception {
		
		try {
				conecta();
				return connection;
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
			throw ex;
				
		}
	}


	
	public static void conecta() throws Exception {
		try {
			DriverManager.registerDriver(new com.mysql.jdbc.Driver());
			connection = DriverManager.getConnection(URL, USUARIO, SENHA);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;

		}
	}
	
}
