package forum.userServlets;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.UserDAO;
import forum.model.User;

public class UserManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserManagementServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		
		try {
		
		try {
			if (!"Administrator".equals(user.getRole())) {
				response.sendRedirect("./HomepageServlet");
				return;
			}
		} catch(NullPointerException e) {
			response.sendRedirect("./HomepageServlet");
			return;
		}
		
		List<User> users = UserDAO.get();
		request.setAttribute("users", users);
		request.getRequestDispatcher("./user/userManagement.jsp").forward(request, response);
		
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
