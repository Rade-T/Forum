package forum.postServlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.PostDAO;
import forum.dao.ThreadDAO;
import forum.dao.UserDAO;
import forum.model.Post;
import forum.model.Thread;
import forum.model.User;

public class PostEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PostEditServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		User user = (User) request.getSession().getAttribute("user");
		
		try {
		
		switch (action) {
		case "find":
			String postId = request.getParameter("postId");
			findPost(request, response, user, postId);
			break;
		case "edit":
			editPost(request, response, user);
			break;
		}
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}
	
	protected void findPost(HttpServletRequest request, HttpServletResponse response, User user, String postId) throws ServletException, IOException {
		Post post = PostDAO.get(user, postId);
		request.setAttribute("post", post);
		request.getRequestDispatcher("./post/editPost.jsp").forward(request, response);
	}

	protected void editPost(HttpServletRequest request, HttpServletResponse response, User user) throws IOException {
		Post post = new Post();
		int id = Integer.parseInt( request.getParameter("id") );
		String content = request.getParameter("content");
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateCreated = null;
		try {
			dateCreated = dateFormat.parse(request.getParameter("dateCreated"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Thread parent = ThreadDAO.get(user, String.valueOf(request.getParameter("parent")) );
		User owner = UserDAO.get( String.valueOf(request.getParameter("owner")) );
		
		post.setId(id);
		post.setContent(content);
		post.setDateCreated(dateCreated);
		post.setThread(parent);
		post.setOwner(owner);
		PostDAO.update(post);
		response.sendRedirect("./PostServlet?postId=" + String.valueOf(id));
	}
}
