package forum.forumServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.ForumDAO;
import forum.model.Forum;
import forum.model.User;

public class ForumDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ForumDeleteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String forumId = request.getParameter("forumId");
		User user = (User) request.getSession().getAttribute("user");
		Forum forum = ForumDAO.get(user, forumId);
		
		try {
		
		if (!forum.getFlag("canDelete")) {
			response.sendRedirect("./HomepageServlet");
			return;
		}
		
		if (forum.getFlag("physicalDelete")) {
			ForumDAO.deletePhysical(forumId);
		} else {
			ForumDAO.delete(user, forum);
		}
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
		
		response.sendRedirect("./HomepageServlet");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
