package forum;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import forum.dao.UserDAO;
import forum.model.User;

public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@SuppressWarnings("unchecked")
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		ObjectMapper objectMapper = new ObjectMapper();

		HttpSession session = request.getSession();
		User loggedInUser = (User) session.getAttribute("user");
		if (loggedInUser != null) {
			Map<String, Object> data = new HashMap<>();
			data.put("redirect", "./HomepageServlet");
			response.getWriter().write(objectMapper.writeValueAsString(data));
			return;
		}

		String user = request.getParameter("user");

		Map<String, Object> data = new HashMap<>();
		try {
			Map<String, Object> newUser;
			try {
				newUser = objectMapper.readValue(user, HashMap.class);
			} catch (Exception ex) {
				throw new Exception("Greska u parametrima!");
			}
			if (newUser.get("userName").equals("")) {
				throw new Exception("Korisnicko ime ne sme biti prazno!");
			}
			if (newUser.get("password").equals("")) {
				throw new Exception("Lozinka ne sme biti prazna!");
			}

			loggedInUser = UserDAO.get((String) newUser.get("userName"), (String) newUser.get("password"));
			if (loggedInUser == null) {
				throw new Exception("Neuspesna prijava!");
			}

			session.setAttribute("user", loggedInUser);

			data.put("redirect", "./HomepageServlet");

			data.put("status", "success");
			data.put("message", "Prijava uspesna!");
		} catch (Exception ex) {
			data.put("status", "failure");
			data.put("message", ex.getMessage());
		}
		response.getWriter().write(objectMapper.writeValueAsString(data));
	}

}
