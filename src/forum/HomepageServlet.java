package forum;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import forum.dao.ForumDAO;
import forum.model.Forum;
import forum.model.User;

public class HomepageServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public HomepageServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		List<Forum> forums = ForumDAO.getRootForums(user);
		List<Forum> visible = new ArrayList<>();
		
		for (Forum forum : forums) {
			if (forum.getFlag("canSee")) {
				visible.add(forum);
			}
		}
		
		request.setAttribute("forums", visible);
		request.setAttribute("loggedInUser", user);

		if (request.getParameter("action").equals("search")) {
			List<Forum> searchedForums = new ArrayList<Forum>();
			switch (request.getParameter("searchCategory")) {
			case "name":
				String searchedName = request.getParameter("searchParameter").toLowerCase();
				for (Forum forum : forums) {
					if (forum.getName().toLowerCase().contains(searchedName) && forum.getFlag("canSee")) {
						searchedForums.add(forum);
					}
				}
				break;
			case "userName":
				String ownerUserName = request.getParameter("searchParameter").toLowerCase();
				for (Forum forum : forums) {
					if (forum.getOwner().getUserName().toLowerCase().equals(ownerUserName) && forum.getFlag("canSee")) {
						searchedForums.add(forum);
					}
				}
				break;
			/*
			 * case "dateCreated": Date searchedDate = ; for (Forum forum :
			 * forums) { if
			 * (forum.getOwner().getUserName().toLowerCase().equals(
			 * ownerUserName)) { searchedForums.add(forum); } } break;
			 */
			}
			request.setAttribute("forums", searchedForums);
		}

		request.getRequestDispatcher("./homepage.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
