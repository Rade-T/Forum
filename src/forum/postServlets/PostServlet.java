package forum.postServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.PostDAO;
import forum.model.Post;
import forum.model.User;

public class PostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PostServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String postId = request.getParameter("postId");
		User user = (User) request.getSession().getAttribute("user");
		
		try {
		
		Post post = PostDAO.get(user, postId);
		
		request.setAttribute("post", post);
		request.getRequestDispatcher("./post/post.jsp").forward(request, response);
		
		} catch (NullPointerException ex) {
			ex.printStackTrace();
			response.sendRedirect("./HomepageServlet");
			return;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
