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
import forum.model.Post;
import forum.model.Thread;
import forum.model.User;

public class ThreadDAO {

	public static void Create(String name, String description, User owner, Forum forum, Date dateCreated) {
		String sql = "Insert Into Thread" + "(ThreadName, Description, ThreadOwner, ParentForum, DateCreated)"
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
			statement.setInt(index++, owner.getId());
			statement.setInt(index++, forum.getId());
			statement.setString(index++, dateFormat.format(dateCreated));

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

	/*
	public static Thread get(String id) {
		Thread thread = null;

		String sql = "Select * From Thread Where Id = ?";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index, id);

			resultSet = statement.executeQuery();
			resultSet.next();

			int Id = resultSet.getInt("Id");
			String ThreadName = resultSet.getString("ThreadName");
			String Description = resultSet.getString("Description");
			User ThreadOwner = UserDAO.get(String.valueOf(resultSet.getInt("ThreadOwner")));
			boolean Pinned = resultSet.getBoolean("Pinned");
			boolean Locked = resultSet.getBoolean("Locked");
			Date DateCreated = resultSet.getTimestamp("DateCreated");
			Forum ParentForum = ForumDAO.get(String.valueOf(resultSet.getInt("ParentForum")));
			thread = new Thread(Id, ThreadName, Description, ThreadOwner, Pinned, Locked, ParentForum, DateCreated);
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

		return thread;
	}
	*/
	
	public static Thread get(User user, String id) {
		Thread thread = null;

		String sql = "Select * From Thread Where Id = ? And Deleted != True";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;
			statement.setString(index, id);

			resultSet = statement.executeQuery();
			resultSet.next();

			int Id = resultSet.getInt("Id");
			String ThreadName = resultSet.getString("ThreadName");
			String Description = resultSet.getString("Description");
			User ThreadOwner = UserDAO.get(String.valueOf(resultSet.getInt("ThreadOwner")));
			boolean Pinned = resultSet.getBoolean("Pinned");
			boolean Locked = resultSet.getBoolean("Locked");
			Date DateCreated = resultSet.getTimestamp("DateCreated");
			Forum ParentForum = ForumDAO.get(user, String.valueOf(resultSet.getInt("ParentForum")));
			thread = new Thread(Id, ThreadName, Description, ThreadOwner, Pinned, Locked, ParentForum, DateCreated);
			thread.setFlags(user);
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

		return thread;
	}

	public static List<Thread> getUserThreads(User user, User owner) {
		List<Thread> threads = new ArrayList<>();

		String sql = "Select * From Thread Where ThreadOwner = ? And Deleted != True Order By DateCreated Desc";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, String.valueOf(owner.getId()));
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int Id = resultSet.getInt("Id");
				String ThreadName = resultSet.getString("ThreadName");
				String Description = resultSet.getString("Description");
				Forum ParentForum = ForumDAO.get(user, String.valueOf(resultSet.getInt("ParentForum")));
				boolean Pinned = resultSet.getBoolean("Pinned");
				boolean Locked = resultSet.getBoolean("Locked");
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				Thread found = new Thread(Id, ThreadName, Description, owner, Pinned, Locked, ParentForum, DateCreated);
				found.setFlags(owner);
				threads.add(found);
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

		return threads;
	}

	/*
	public static List<Thread> getForumThreads(Forum parent) {
		List<Thread> threads = new ArrayList<>();

		String sql = "Select * From Thread Where ParentForum = ? Order By(pinned) Desc";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, String.valueOf(parent.getId()));
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int Id = resultSet.getInt("Id");
				String ThreadName = resultSet.getString("ThreadName");
				String Description = resultSet.getString("Description");
				User ThreadOwner = UserDAO.get(String.valueOf(resultSet.getInt("ThreadOwner")));
				boolean Pinned = resultSet.getBoolean("Pinned");
				boolean Locked = resultSet.getBoolean("Locked");
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				Thread found = new Thread(Id, ThreadName, Description, ThreadOwner, Pinned, Locked, parent,
						DateCreated);
				threads.add(found);
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

		return threads;
	}
	*/
	
	public static List<Thread> getForumThreads(User user, Forum parent) {
		List<Thread> threads = new ArrayList<>();

		String sql = "Select * From Thread Where ParentForum = ? And Deleted != True Order By(pinned) Desc";

		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			statement.setString(1, String.valueOf(parent.getId()));
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int Id = resultSet.getInt("Id");
				String ThreadName = resultSet.getString("ThreadName");
				String Description = resultSet.getString("Description");
				User ThreadOwner = UserDAO.get(String.valueOf(resultSet.getInt("ThreadOwner")));
				boolean Pinned = resultSet.getBoolean("Pinned");
				boolean Locked = resultSet.getBoolean("Locked");
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				Thread found = new Thread(Id, ThreadName, Description, ThreadOwner, Pinned, Locked, parent,
						DateCreated);
				found.setFlags(user);
				threads.add(found);
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

		return threads;
	}
	
	public static void update(Thread thread) {
		String sql = "Update Thread Set "
				+ "ThreadName = ?, Description = ?, DateCreated = ?, ThreadOwner = ?, ParentForum = ?, Locked = ?, Pinned = ? "
				+ "Where Id = ?;";
		
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setString(index++, thread.getName());
			statement.setString(index++, thread.getDescription());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			statement.setString(index++, dateFormat.format(thread.getDateCreated()));
			statement.setInt(index++, thread.getOwner().getId());
			statement.setInt(index++, thread.getForum().getId());
			statement.setBoolean(index++, thread.isLocked());
			statement.setBoolean(index++, thread.isPinned());
			statement.setInt(index++, thread.getId());

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

	public static void deletePhysical(String threadId) {
		String sql = "Delete From Thread Where Id = ?;";
		
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setInt(index++, Integer.parseInt(threadId));

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

	public static void delete(User user, Thread thread) {
		String sql = "Update Thread Set Deleted = true Where Id = ?;";
		
		Connection connection = null;
		PreparedStatement statement = null;
		
		List<Post> threadPosts = PostDAO.getThreadPosts(user, thread);
		
		for (Post post : threadPosts) {
			PostDAO.delete(post.getId());
		}
		
		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setInt(index++, thread.getId());

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
