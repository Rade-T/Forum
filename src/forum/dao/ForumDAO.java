package forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import forum.model.Forum;
import forum.model.Thread;
import forum.model.User;

public class ForumDAO {

	public static List<Forum> getSubforums(Forum parent, User user) {
		List<Forum> forums = new ArrayList<>();
		String sql = "Select * From Forum Where ParentForum = ? And Deleted != True;";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);

			if (parent != null) {
				statement.setString(1, String.valueOf(parent.getId()));
			}
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int Id = resultSet.getInt("Id");
				String ForumName = resultSet.getString("ForumName");
				String Description = resultSet.getString("Description");
				boolean Locked = resultSet.getBoolean("Locked");
				String FType = resultSet.getString("ForumType");
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				User ForumOwner = UserDAO.get(String.valueOf(resultSet.getInt("ForumOwner")));
				Forum found = new Forum(Id, ForumName, Description, ForumOwner, Locked, parent, FType, DateCreated);
				found.setFlags(user);
				forums.add(found);
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
		return forums;
	}
	
	public static List<Forum> getUserForums(User user, User owner) {
		List<Forum> forums = new ArrayList<>();
		String sql = "Select * From Forum Where ForumOwner = ? And Deleted != True;";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);

			statement.setInt(1, owner.getId());
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int Id = resultSet.getInt("Id");
				String ForumName = resultSet.getString("ForumName");
				String Description = resultSet.getString("Description");
				boolean Locked = resultSet.getBoolean("Locked");
				String FType = resultSet.getString("ForumType");
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				Forum parent = ForumDAO.get(user, String.valueOf(resultSet.getInt("ParentForum")));
				Forum found = new Forum(Id, ForumName, Description, owner, Locked, parent, FType, DateCreated);
				found.setFlags(user);
				forums.add(found);
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
		return forums;
	}
	
	public static List<Forum> getRootForums(User user) {
		List<Forum> forums = new ArrayList<>();
		String sql = "Select * From Forum Where ParentForum is null And Deleted != True;";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int Id = resultSet.getInt("Id");
				String ForumName = resultSet.getString("ForumName");
				String Description = resultSet.getString("Description");
				boolean Locked = resultSet.getBoolean("Locked");
				String FType = resultSet.getString("ForumType");
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				User ForumOwner = UserDAO.get(String.valueOf(resultSet.getInt("ForumOwner")));
				Forum found = new Forum(Id, ForumName, Description, ForumOwner, Locked, null, FType, DateCreated);
				found.setFlags(user);
				forums.add(found);
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
		return forums;
	}

	/*
	public static Forum get(String id) {
		Forum forum = null;
		String sql = "Select * From Forum Where Id = ?;";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index, id);

			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int Id = resultSet.getInt("Id");
				String ForumName = resultSet.getString("ForumName");
				String Description = resultSet.getString("Description");
				boolean Locked = resultSet.getBoolean("Locked");
				String FType = resultSet.getString("ForumType");
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				int parent = resultSet.getInt("ParentForum");
				Forum ParentForum = null;
				if (parent != 0) {
					ParentForum = get(String.valueOf(parent));
				}
				User ForumOwner = UserDAO.get(String.valueOf(resultSet.getInt("ForumOwner")));
				forum = new Forum(Id, ForumName, Description, ForumOwner, Locked, ParentForum, FType, DateCreated);
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
		
		return forum;
	}
	*/
	
	public static Forum get(User user, String id) {
		Forum forum = null;
		String sql = "Select * From Forum Where Id = ? And Deleted != True;";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index, id);

			resultSet = statement.executeQuery();
			if (resultSet.next()) {
				int Id = resultSet.getInt("Id");
				String ForumName = resultSet.getString("ForumName");
				String Description = resultSet.getString("Description");
				boolean Locked = resultSet.getBoolean("Locked");
				String FType = resultSet.getString("ForumType");
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				int parent = resultSet.getInt("ParentForum");
				Forum ParentForum = null;
				if (parent != 0) {
					ParentForum = get(user, String.valueOf(parent));
				}
				User ForumOwner = UserDAO.get(String.valueOf(resultSet.getInt("ForumOwner")));
				forum = new Forum(Id, ForumName, Description, ForumOwner, Locked, ParentForum, FType, DateCreated);
				forum.setFlags(user);
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
		
		return forum;
	}

	public static void create(String name, String description, User owner, String forumType, Date dateCreated) {
		String sql = "Insert Into Forum" + "(ForumName, Description, DateCreated, ForumOwner, ForumType) "
				+ "Values(?, ?, ?, ?, ?);";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setString(index++, name);
			statement.setString(index++, description);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			statement.setString(index++, dateFormat.format(dateCreated));
			statement.setInt(index++, owner.getId());
			statement.setString(index++, forumType);

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

	public static void create(String name, String description, User owner, String forumType, Date dateCreated,
			Forum parent) {
		String sql = "Insert Into Forum(ForumName, Description, DateCreated, ForumOwner, ForumType, ParentForum) Values(?, ?, ?, ?, ?, ?);";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setString(index++, name);
			statement.setString(index++, description);

			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			statement.setString(index++, dateFormat.format(dateCreated));
			statement.setInt(index++, owner.getId());
			statement.setString(index++, forumType);
			statement.setInt(index++, parent.getId());

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
	
	public static void update(Forum forum) {
		String sql = "Update Forum Set "
				+ "ForumName = ?, Description = ?, DateCreated = ?, ForumOwner = ?, ForumType = ?, Locked = ? "
				+ "Where Id = ?;";
		if (forum.getParentForum() != null) {
			sql = "Update Forum Set "
					+ "ForumName = ?, Description = ?, DateCreated = ?, ForumOwner = ?, ForumType = ?, Locked = ?, ParentForum = ? "
					+ "Where Id = ?;";
		}
		
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setString(index++, forum.getName());
			statement.setString(index++, forum.getDescription());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			statement.setString(index++, dateFormat.format(forum.getDateCreated()));
			statement.setInt(index++, forum.getOwner().getId());
			statement.setString(index++, forum.getType());
			statement.setBoolean(index++, forum.isLocked());
			if (forum.getParentForum() != null) {
				statement.setInt(index++, forum.getParentForum().getId());
			}
			statement.setInt(index++, forum.getId());

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

	public static void deletePhysical(String forumId) {
		String sql = "Delete From Forum Where Id = ?;";
		
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setInt(index++, Integer.parseInt(forumId));

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

	public static void delete(User user, Forum forum) {
		String sql = "Update Forum Set Deleted = true Where Id = ?;";
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		List<Thread> forumThreads = ThreadDAO.getForumThreads(user, forum);
		
		for (Thread thread: forumThreads) {
			ThreadDAO.delete(user, thread);
		}
		
		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setInt(index++, forum.getId());

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
