package forum.userServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import forum.dao.PostDAO;
import forum.dao.ThreadDAO;
import forum.dao.UserDAO;
import forum.model.Post;
import forum.model.Thread;
import forum.model.User;

public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("user");
		String userId = request.getParameter("userId");
		
		try {
		
		User user = UserDAO.get(userId);

		List<Thread> userThreads = ThreadDAO.getUserThreads(loggedInUser, user);
		List<Post> userPosts = PostDAO.getUserPosts(loggedInUser, user);
		List<Thread> sortedThreads = new ArrayList<>();
		List<Post> sortedPosts = new ArrayList<>();

		for (Thread thread : userThreads) {
			if (thread.getFlag("canSee")) {
				sortedThreads.add(thread);
			}
		}
		for (Post post : userPosts) {
			if (post.getFlag("canSee")) {
				sortedPosts.add(post);
			}
		}

		request.setAttribute("user", user);
		request.setAttribute("threads", sortedThreads);
		request.setAttribute("posts", sortedPosts);
		request.getRequestDispatcher("./user/user.jsp").forward(request, response);
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
