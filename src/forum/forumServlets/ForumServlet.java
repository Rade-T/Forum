package forum.forumServlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.ForumDAO;
import forum.dao.ThreadDAO;
import forum.model.Forum;
import forum.model.Thread;
import forum.model.User;

public class ForumServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ForumServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		User user = (User)request.getSession().getAttribute("user");
		String forumId = request.getParameter("forumId");
		
		try {
		
		Forum f = ForumDAO.get(user, forumId);
		
		List<Forum> subForums = ForumDAO.getSubforums(f, user);
		List<Forum> visibleForums = new ArrayList<>();
		List<Thread> forumThreads = ThreadDAO.getForumThreads(user, f);
		List<Thread> visibleThreads = new ArrayList<>();
		
		for (Thread thread : forumThreads) {
			if (thread.getFlag("canSee")) {
				visibleThreads.add(thread);
			}
		}
		
		for (Forum forum : subForums) {
			if (forum.getFlag("canSee")) {
				visibleForums.add(forum);
			}
		}
		
		if (f.getFlag("canSee")) {
			request.setAttribute("forum", f);
			request.setAttribute("subForums", visibleForums);
			request.setAttribute("forumThreads", visibleThreads);
			request.getRequestDispatcher("./forum/forum.jsp").forward(request, response);
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
		doGet(request, response);
	}
	
	
}
