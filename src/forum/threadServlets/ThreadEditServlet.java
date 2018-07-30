package forum.threadServlets;

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
import forum.dao.ThreadDAO;
import forum.dao.UserDAO;
import forum.model.Forum;
import forum.model.Thread;
import forum.model.User;

public class ThreadEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ThreadEditServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		String threadId = request.getParameter("threadId");
		User user = (User) request.getSession().getAttribute("user");
		
		try {
		
		switch (action) {
		case "find":
			findThread(request, response, user, threadId);
			break;
		case "edit":
			editThread(request, response);
			break;
		case "lock":
			lockThread(request, response, user, threadId);
			break;
		case "unlock":
			unlockThread(request, response, user, threadId);
			break;
		case "pin":
			pinThread(request, response, user, threadId);
			break;
		case "unpin":
			unpinThread(request, response, user, threadId);
			break;
		}
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}
	
	private void unpinThread(HttpServletRequest request, HttpServletResponse response, User user, String threadId) throws IOException {
		Thread thread = ThreadDAO.get(user, threadId);
		thread.setPinned(false);
		ThreadDAO.update(thread);
		response.sendRedirect("./ThreadServlet?threadId=" + threadId);
	}

	private void pinThread(HttpServletRequest request, HttpServletResponse response, User user, String threadId) throws IOException {
		Thread thread = ThreadDAO.get(user, threadId);
		thread.setPinned(true);
		ThreadDAO.update(thread);
		response.sendRedirect("./ThreadServlet?threadId=" + threadId);
	}

	private void unlockThread(HttpServletRequest request, HttpServletResponse response, User user, String threadId) throws IOException {
		Thread thread = ThreadDAO.get(user, threadId);
		thread.setLocked(false);
		ThreadDAO.update(thread);
		response.sendRedirect("./ThreadServlet?threadId=" + threadId);
	}

	private void lockThread(HttpServletRequest request, HttpServletResponse response, User user, String threadId) throws IOException {
		Thread thread = ThreadDAO.get(user, threadId);
		thread.setLocked(true);
		ThreadDAO.update(thread);
		response.sendRedirect("./ThreadServlet?threadId=" + threadId);
	}

	protected void findThread(HttpServletRequest request, HttpServletResponse response, User user, String threadId) throws ServletException, IOException {
		Thread thread = ThreadDAO.get(user, threadId);
		if (!thread.getFlag("canEdit")) {
			response.sendRedirect("./HomepageServlet");
			return;
		}
		request.setAttribute("thread", thread);
		request.getRequestDispatcher("./thread/editThread.jsp").forward(request, response);
	}

	protected void editThread(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Thread thread = new Thread();
		int id = Integer.parseInt( request.getParameter("id") );
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		User user = (User) request.getSession().getAttribute("user");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateCreated = null;
		try {
			dateCreated = dateFormat.parse(request.getParameter("dateCreated"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Forum parent = ForumDAO.get(user, String.valueOf(request.getParameter("parent")) );
		User owner = UserDAO.get( String.valueOf(request.getParameter("owner")) );
		
		thread.setId(id);
		thread.setName(name);
		thread.setDescription(description);
		thread.setDateCreated(dateCreated);
		thread.setForum(parent);
		thread.setOwner(owner);
		ThreadDAO.update(thread);
		response.sendRedirect("./ThreadServlet?threadId=" + String.valueOf(id));
	}
}
