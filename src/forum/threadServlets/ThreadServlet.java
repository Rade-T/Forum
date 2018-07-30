package forum.threadServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.PostDAO;
import forum.dao.ThreadDAO;
import forum.model.Post;
import forum.model.Thread;
import forum.model.User;

public class ThreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThreadServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		User user = (User) request.getSession().getAttribute("user");
		String threadId = request.getParameter("threadId");
		
		try {
		
		Thread t = ThreadDAO.get(user, threadId);
		
		List<Post> threadPosts = PostDAO.getThreadPosts(user, t);
		List<Post> visiblePosts = new ArrayList<>();
		
		for (Post post : threadPosts) {
			if (post.getFlag("canSee")) {
				visiblePosts.add(post);
			}
		}
		
		if (t.getFlag("canSee")) {
			request.setAttribute("thread", t);
			request.setAttribute("threadPosts", visiblePosts);
			request.getRequestDispatcher("./thread/thread.jsp").forward(request, response);
		} else {
			response.sendRedirect("./HomepageServlet");
		}
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}
