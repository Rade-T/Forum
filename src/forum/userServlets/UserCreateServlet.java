package forum.userServlets;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.UserDAO;
import forum.model.User;

public class UserCreateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserCreateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("user");
		
		try {
		
		if (!"Administrator".equals(user.getRole())) {
			response.sendRedirect("./HomepageServlet");
			return;
		}
		
		String action = request.getParameter("action");
		switch(action) {
		case "forward":
			request.getRequestDispatcher("./user/createUser.jsp").forward(request, response);
			break;
		case "create":
			create(request, response);
			break;
		default:
			response.sendRedirect("./HomepageServlet");
			break;
		}
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}
	
	protected void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String role = request.getParameter("role");
		Date dateCreated = new Date();
		
		User user = new User();
		user.setName(name);
		user.setSurname(surname);
		user.setUserName(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setRole(role);
		user.setDateCreated(dateCreated);
		UserDAO.create(user);
		response.sendRedirect("./UserManagementServlet");
	}

}
