package forum.threadServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.ThreadDAO;
import forum.model.Thread;
import forum.model.User;

public class ThreadDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ThreadDeleteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String threadId = request.getParameter("threadId");
		User user = (User) request.getSession().getAttribute("user");
		
		try {
		
		Thread thread = ThreadDAO.get(user, threadId);
		
		if (!thread.getFlag("canDelete")) {
			response.sendRedirect("./HomepageServlet");
			return;
		}
		
		if (thread.getFlag("physicalDelete")) {
			ThreadDAO.deletePhysical(threadId);
		} else {
			ThreadDAO.delete(user, thread);
		}
		
		response.sendRedirect("./ForumServlet?forumId=" + thread.getForum().getId());
		
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
