package forum.threadServlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import forum.dao.ForumDAO;
import forum.dao.ThreadDAO;
import forum.model.Forum;
import forum.model.User;

public class ThreadCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ThreadCreateServlet() {
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
			request.setAttribute("forumId", forumId);
			request.getRequestDispatcher("./thread/createThread.jsp").forward(request, response);
			return;
		} else {
			Forum forum = ForumDAO.get(user, request.getParameter("forumId"));
			String forumType = forum.getType();
			if (forumType.equals("Public") || forumType.equals("Open")) {
				if (!forum.isLocked()) {
					String forumId = String.valueOf(request.getParameter("forumId"));
					request.setAttribute("forumId", forumId);
					request.getRequestDispatcher("./thread/createThread.jsp").forward(request, response);
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
		Forum forum = ForumDAO.get(user, forumId);

		if (user.getRole().equals("User")) {
			if (forum.isLocked() || forum.getType().equals("Closed")) {
				response.sendRedirect("./HomepageServlet");
				return;
			}
		}

		String name = request.getParameter("name");
		String description = request.getParameter("description");
		Date dateCreated = new Date();
		ThreadDAO.Create(name, description, user, forum, dateCreated);
		response.sendRedirect("./ForumServlet?forumId=" + String.valueOf(forum.getId()));
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}

}
