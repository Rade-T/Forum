package forum.userServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.ForumDAO;
import forum.dao.PostDAO;
import forum.dao.ThreadDAO;
import forum.dao.UserDAO;
import forum.model.User;

public class UserDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserDeleteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		
		try {
		
		try {
			if (!"Administrator".equals( user.getRole() )) {
				response.sendRedirect("./HomepageServlet");
				return;
			}
		} catch (NullPointerException e) {
			response.sendRedirect("./HomepageServlet");
			e.printStackTrace();
			return;
		}
		
		String userId = request.getParameter("userId");
		User toDelete = UserDAO.get(userId);
		
		if (ForumDAO.getUserForums(user, toDelete).isEmpty() &&
				ThreadDAO.getUserThreads(user, toDelete).isEmpty() &&
				PostDAO.getUserPosts(user, toDelete).isEmpty()) {
			UserDAO.delete(toDelete.getId());
			response.sendRedirect("./UserManagementServlet");
			return;
		} else {
			response.sendRedirect("./HomepageServlet");
			return;
		}
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
