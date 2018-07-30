package forum.forumServlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import forum.dao.ForumDAO;
import forum.model.Forum;
import forum.model.User;

public class ForumCreateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public ForumCreateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		try {

		if (user != null && user.getRole().equals("Administrator")) {
			if (request.getParameter("forumType").equals("rootForum")) {
				request.getRequestDispatcher("./forum/createForum.jsp").forward(request, response);
			}

			if (request.getParameter("forumType").equals("subForum")) {
				String parentId = request.getParameter("parentId");
				Forum forum = ForumDAO.get(user, parentId);
				request.setAttribute("parentForumId", parentId);
				request.setAttribute("type", forum.getType());
				request.getRequestDispatcher("./forum/createSubForum.jsp").forward(request, response);
			}
		}
		
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
			
		if (user != null && user.getRole().equals("Administrator")) {
			if (request.getParameter("action").equals("createForum")) {
				String name = (String) request.getParameter("name");
				String description = (String) request.getParameter("description");
				String forumType = (String) request.getParameter("forumType");
				Date dateCreated = new Date();
				ForumDAO.create(name, description, user, forumType, dateCreated);
				response.sendRedirect("./HomepageServlet");
				return;
			}

			if (request.getParameter("action").equals("createSubForum")) {
				Forum parent = ForumDAO.get(user, request.getParameter("parentForumId"));
				String name = (String) request.getParameter("name");
				String description = (String) request.getParameter("description");
				String forumType = (String) request.getParameter("forumType");

				if (parent.getType().equals("Open")) {
					if (forumType.equals("Public")) {
						response.sendRedirect("./HomepageServlet");
					}
				}
				if (parent.getType().equals("Closed")) {
					if (forumType.equals("Public") || forumType.equals("Open")) {
						response.sendRedirect("./HomepageServlet");
					}
				}

				Date dateCreated = new Date();
				ForumDAO.create(name, description, user, forumType, dateCreated, parent);
				response.sendRedirect("./HomepageServlet");
				return;
			}
			response.sendRedirect("./HomepageServlet");
		}
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}
}
