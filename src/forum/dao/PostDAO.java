package forum.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import forum.model.Post;
import forum.model.Thread;
import forum.model.User;

public class PostDAO {

	public static void Create(String content, User owner, Thread thread, Date dateCreated) {
		String sql = "Insert Into Post(Content, PostOwner, ParentThread, DateCreated)" + "Values(?, ?, ?, ?)";

		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setString(index++, content);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			statement.setInt(index++, owner.getId());
			statement.setInt(index++, thread.getId());
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
	public static Post get(String id) {
		Post post = null;

		String sql = "Select * From Post Where Id = ? And Deleted != True;";

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
			String Content = resultSet.getString("Content");
			User PostOwner = UserDAO.get(String.valueOf(resultSet.getInt("PostOwner")));
			Date DateCreated = resultSet.getTimestamp("DateCreated");
			Thread ParentThread = ThreadDAO.get(String.valueOf(resultSet.getInt("ParentThread")));
			post = new Post(Id, Content, PostOwner, ParentThread, DateCreated);
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

		return post;
	}
	*/
	
	public static Post get(User user, String id) {
		Post post = null;

		String sql = "Select * From Post Where Id = ? And Deleted != True;";

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
			String Content = resultSet.getString("Content");
			User PostOwner = UserDAO.get(String.valueOf(resultSet.getInt("PostOwner")));
			Date DateCreated = resultSet.getTimestamp("DateCreated");
			Thread ParentThread = ThreadDAO.get(user, String.valueOf(resultSet.getInt("ParentThread")));
			post = new Post(Id, Content, PostOwner, ParentThread, DateCreated);
			post.setFlags(user);
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

		return post;
	}

	/*
	public static List<Post> getThreadPosts(Thread parent) {
		List<Post> posts = new ArrayList<>();

		String sql = "Select * From Post Where ParentThread = ? And Deleted != True;";

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
				String Content = resultSet.getString("Content");
				User PostOwner = UserDAO.get(String.valueOf(resultSet.getInt("PostOwner")));
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				Thread ParentThread = ThreadDAO.get(String.valueOf(resultSet.getInt("ParentThread")));
				posts.add(new Post(Id, Content, PostOwner, ParentThread, DateCreated));
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

		return posts;
	}
	*/
	
	public static List<Post> getThreadPosts(User user, Thread parent) {
		List<Post> posts = new ArrayList<>();

		String sql = "Select * From Post Where ParentThread = ? And Deleted != True Order By DateCreated Desc;";

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
				String Content = resultSet.getString("Content");
				User PostOwner = UserDAO.get(String.valueOf(resultSet.getInt("PostOwner")));
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				//Thread ParentThread = ThreadDAO.get(user, String.valueOf(resultSet.getInt("ParentThread")));
				Post post = new Post(Id, Content, PostOwner, parent, DateCreated);
				post.setFlags(user);
				posts.add(post);
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

		return posts;
	}

	public static List<Post> getUserPosts(User user, User owner) {
		List<Post> posts = new ArrayList<>();

		String sql = "Select * From Post Where PostOwner = ? And Deleted != True Order By DateCreated Desc;";

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
				String Content = resultSet.getString("Content");
				Date DateCreated = resultSet.getTimestamp("DateCreated");
				Thread ParentThread = ThreadDAO.get(user, String.valueOf(resultSet.getInt("ParentThread")));
				Post post = new Post(Id, Content, owner, ParentThread, DateCreated);
				post.setFlags(user);
				posts.add(post);
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

		return posts;
	}
	
	public static void update(Post post) {
		String sql = "Update Post Set "
				+ "Content = ?, DateCreated = ?, PostOwner = ?, ParentThread = ? "
				+ "Where Id = ?;";
		
		Connection connection = null;
		PreparedStatement statement = null;

		try {
			connection = ConnectionManager.getConnection();
			statement = connection.prepareStatement(sql);
			int index = 1;

			statement.setString(index++, post.getContent());
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			statement.setString(index++, dateFormat.format(post.getDateCreated()));
			statement.setInt(index++, post.getOwner().getId());
			statement.setInt(index++, post.getThread().getId());
			statement.setInt(index++, post.getId());

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
	
	public static void deletePhysical(int id) {
		String sql = "Delete From Post Where Id = ?;";
		
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
	
	public static void delete(int id) {
		String sql = "Update Post Set Deleted = true Where Id = ?;";
		
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
	
	public static void deletePhysical(String id) {
		deletePhysical(Integer.parseInt(id));
	}
	
	public static void delete(String id) {
		delete(Integer.parseInt(id));
	}
}
