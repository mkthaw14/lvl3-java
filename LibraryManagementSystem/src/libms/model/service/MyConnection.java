package libms.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection
{
	static String url = "jdbc:mariadb://localhost:3306/library_db";
	static String user = "root";
	static String password = "";
	
	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(url, user, password);
	}
}
