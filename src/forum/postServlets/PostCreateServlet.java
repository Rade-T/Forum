package forum.postServlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import forum.dao.ForumDAO;
import forum.dao.PostDAO;
import forum.dao.ThreadDAO;
import forum.model.Forum;
import forum.model.User;
import forum.model.Thread;

public class PostCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PostCreateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		try {

		if (user == null) {
			response.sendRedirect("./HomepageServlet");
			return;
		}
		if (user.getRole().equals("Administrator") || user.getRole().equals("Moderator")) {
			String forumId = String.valueOf(request.getParameter("forumId"));
			String threadId = String.valueOf(request.getParameter("threadId"));
			request.setAttribute("forumId", forumId);
			request.setAttribute("threadId", threadId);
			request.getRequestDispatcher("./post/createPost.jsp").forward(request, response);
			return;
		} else {
			Forum forum = ForumDAO.get(user, request.getParameter("forumId"));
			String forumType = forum.getType();
			if (forumType.equals("Public") || forumType.equals("Open")) {
				if (!forum.isLocked()) {
					String forumId = String.valueOf(request.getParameter("forumId"));
					request.setAttribute("forumId", forumId);
					request.setAttribute("threadId", String.valueOf(request.getParameter("threadId")));
					request.getRequestDispatcher("./post/createPost.jsp").forward(request, response);
					return;
				}
			}
		}
		response.sendRedirect("./HomepageServlet");
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");

		try {
		
		if (user == null) {
			response.sendRedirect("./HomepageServlet");
			return;
		}

		String forumId = request.getParameter("forumId");
		String threadId = request.getParameter("threadId");
		Forum forum = ForumDAO.get(user, forumId);
		Thread thread = ThreadDAO.get(user, threadId);

		if (user.getRole().equals("User")) {
			if (forum.isLocked() || forum.getType().equals("Closed")) {
				response.sendRedirect("./HomepageServlet");
				return;
			}
		}

		String content = request.getParameter("content");
		Date dateCreated = new Date();
		PostDAO.Create(content, user, thread, dateCreated);
		response.sendRedirect("./ThreadServlet?threadId=" + threadId);
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}

}
