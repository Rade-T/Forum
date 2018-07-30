package forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import forum.model.User;

public class UserDAO {
	public static User get(String username, String password) {
		User user = null;

		String sql = "Select * From ForumUser Where UserName = ? And UserPassword = ? And Deleted != True;";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, username);
			statement.setString(index++, password);

			resultSet = statement.executeQuery();
			resultSet.next();

			int userId = resultSet.getInt("Id");
			String UserName = resultSet.getString("UserName");
			String Password = resultSet.getString("UserPassword");
			String FirstName = resultSet.getString("FirstName");
			String Surname = resultSet.getString("Surname");
			String Email = resultSet.getString("Email");
			boolean Disabled = resultSet.getBoolean("Disabled");
			String UserRole = resultSet.getString("Role");
			Date DateCreated = resultSet.getTimestamp("DateCreated");
			user = new User(userId, UserName, Password, FirstName, Surname, Email, Disabled, DateCreated, UserRole);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				resultSet.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return user;
	}

	public static User get(String id) {
		User user = null;

		String sql = "Select * From ForumUser Where Id = ? And Deleted != True;";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index++, id);

			resultSet = statement.executeQuery();
			resultSet.next();

			int userId = resultSet.getInt("Id");
			String UserName = resultSet.getString("UserName");
			String Password = resultSet.getString("UserPassword");
			String FirstName = resultSet.getString("FirstName");
			String Surname = resultSet.getString("Surname");
			String Email = resultSet.getString("Email");
			boolean Disabled = resultSet.getBoolean("Disabled");
			String UserRole = resultSet.getString("Role");
			Date DateCreated = resultSet.getTimestamp("DateCreated");
			user = new User(userId, UserName, Password, FirstName, Surname, Email, Disabled, DateCreated, UserRole);
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				resultSet.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return user;
	}
	
	public static List<User> get() {
		List<User> users = new ArrayList<>();

		String sql = "Select * From ForumUser Where Deleted != True;";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);

			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				int userId = resultSet.getInt("Id");
				String UserName = resultSet.getString("UserName");
				String Password = resultSet.getString("UserPassword");
				String FirstName = resultSet.getString("FirstName");
				String Surname = resultSet.getString("Surname");
				String Email = resultSet.getString("Email");
				boolean Disabled = resultSet.getBoolean("Disabled");
				String UserRole = resultSet.getString("Role");
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				User user = new User(userId, UserName, Password, FirstName, Surname, Email, Disabled, DateCreated, UserRole);
				users.add(user);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			try {
				resultSet.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return users;
	}
	
	public static void create(User user) {
		String sql = "Insert Into ForumUser" + "(UserName, UserPassword, FirstName, Surname, Email, Role, DateCreated)"
				+ "Values(?, ?, ?, ?, ?, ?, ?);";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setString(index++, user.getUserName());
			statement.setString(index++, user.getPassword());
			statement.setString(index++, user.getName());
			statement.setString(index++, user.getSurname());
			statement.setString(index++, user.getEmail());
			statement.setString(index++, user.getRole());
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			statement.setString(index++, dateFormat.format(user.getDateCreated()));

			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void update(User user) {
		
		String sql = "Update ForumUser Set "
				+ "UserName = ?, UserPassword = ?, FirstName = ?, Surname = ?, Email = ?, Role = ?, Disabled = ? "
				+ "Where Id = ?;";
		
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setString(index++, user.getUserName());
			statement.setString(index++, user.getPassword());
			statement.setString(index++, user.getName());
			statement.setString(index++, user.getSurname());
			statement.setString(index++, user.getEmail());
			statement.setString(index++, user.getRole());
			statement.setBoolean(index++, user.isDisabled());
			statement.setInt(index++, user.getId());

			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void delete(int id) {
		String sql = "Update ForumUser Set "
				+ "Deleted = True "
				+ "Where Id = ?;";
		
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setInt(index++, id);

			statement.executeUpdate();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			try {
				statement.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
