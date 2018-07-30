package forum.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	private static final String DATABASE = "localhost:3306/ForumOWP";
	private static final String USER_NAME = "root";
	private static final String PASSWORD = "root";

	private ConnectionManager() {
	}

	private static Connection CONNECTION;

	public static void destroy() {
		System.out.println("Closing connection to database...");
		try {
			CONNECTION.close();

			System.out.println("Success!");
		} catch (SQLException ex) {
			System.out.println("Failed!");
			ex.printStackTrace();
		}
	}

	public static void init() {
		if (CONNECTION != null)
			destroy();
		System.out.println("Opening connection to database...");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			CONNECTION = DriverManager.getConnection("jdbc:mysql://" + DATABASE, USER_NAME, PASSWORD);

			System.out.println("Success!");
		} catch (Exception ex) {
			System.out.println("Failed!");
			ex.printStackTrace();
		}
	}

	public static Connection getConnection() {
		if (CONNECTION == null)
			init();
		return CONNECTION;
	}
}
