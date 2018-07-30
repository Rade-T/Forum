package forum.forumServlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.ForumDAO;
import forum.dao.UserDAO;
import forum.model.Forum;
import forum.model.User;

public class ForumEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ForumEditServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String forumId = request.getParameter("forumId");
		User user = (User) request.getSession().getAttribute("user");
		
		try {
		
		switch (action) {
		case "find":
			findForum(request, response, user, forumId);
			break;
		case "edit":
			editForum(request, response, user);
			break;
		case "lock":
			lockForum(request, response, user, forumId);
			break;
		case "unlock":
			unlock(request, response, user, forumId);
			break;
		}
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}
	
	private void unlock(HttpServletRequest request, HttpServletResponse response, User user, String forumId) throws IOException {
		Forum forum = ForumDAO.get(user, forumId);
		forum.setLocked(false);
		ForumDAO.update(forum);
		response.sendRedirect("./ForumServlet?forumId=" + forumId);
	}

	private void lockForum(HttpServletRequest request, HttpServletResponse response, User user, String forumId) throws IOException {
		Forum forum = ForumDAO.get(user, forumId);
		forum.setLocked(true);
		ForumDAO.update(forum);
		response.sendRedirect("./ForumServlet?forumId=" + forumId);
	}

	private void findForum(HttpServletRequest request, HttpServletResponse response, User user, String forumId) throws ServletException, IOException {
		Forum forum = ForumDAO.get(user, forumId);
		request.setAttribute("forum", forum);
		request.getRequestDispatcher("./forum/editForum.jsp").forward(request, response);
	}
	
	protected void editForum(HttpServletRequest request, HttpServletResponse response, User user) throws ServletException, IOException {
		Forum forum = new Forum();
		int id = Integer.parseInt( request.getParameter("id") );
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		String type = request.getParameter("type");
		boolean locked = Boolean.parseBoolean(request.getParameter("locked"));
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateCreated = null;
		try {
			dateCreated = dateFormat.parse(request.getParameter("dateCreated"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Forum parent = ForumDAO.get(user, String.valueOf(request.getParameter("parent")) );
		User owner = UserDAO.get( String.valueOf(request.getParameter("owner")) );
		
		forum.setId(id);
		forum.setName(name);
		forum.setDateCreated(dateCreated);
		forum.setDescription(description);
		forum.setType(type);
		forum.setLocked(locked);
		forum.setParentForum(parent);
		forum.setOwner(owner);
		ForumDAO.update(forum);
		response.sendRedirect("./ForumServlet?forumId=" + String.valueOf(id));
	}

}
