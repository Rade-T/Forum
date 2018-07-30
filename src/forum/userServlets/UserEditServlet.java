package forum.userServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.UserDAO;
import forum.model.User;

public class UserEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public UserEditServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		User loggedIn = (User) request.getSession().getAttribute("user");
		String userId = request.getParameter("userId");
		
		try {
		
		User editUser = UserDAO.get(userId);
		
		if (loggedIn == null) {
			response.sendRedirect("./HomepageServlet");
			return;
		}
		
		switch(action) {
		case "find":
			request.setAttribute("editUser", editUser);
			request.getRequestDispatcher("./user/editUser.jsp").forward(request, response);
			return;
		case "edit":
			editUser(request, response, loggedIn);
			break;
		case "disable":
			disableUser(request, response, editUser);
			break;
		case "enable":
			enableUser(request, response, editUser);
			break;
		}
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}

	private void disableUser(HttpServletRequest request, HttpServletResponse response, User disabled) throws IOException {
		disabled.setDisabled(true);
		UserDAO.update(disabled);
		response.sendRedirect("./UserServlet?userId=" + disabled.getId());
	}
	
	private void enableUser(HttpServletRequest request, HttpServletResponse response, User enabled) throws IOException {
		enabled.setDisabled(false);
		UserDAO.update(enabled);
		response.sendRedirect("./UserServlet?userId=" + enabled.getId());
	}

	private void editUser(HttpServletRequest request, HttpServletResponse response, User loggedIn) throws IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String surname = request.getParameter("surname");
		String email = request.getParameter("email");
		
		User toEdit = UserDAO.get(id);
		toEdit.setId(Integer.parseInt(id));
		toEdit.setPassword(password);
		toEdit.setName(name);
		toEdit.setSurname(surname);
		toEdit.setEmail(email);
		
		UserDAO.update(toEdit);
		response.sendRedirect("./UserServlet?userId=" + id);
	}

}
