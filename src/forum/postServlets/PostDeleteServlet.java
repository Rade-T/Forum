package forum.postServlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import forum.dao.PostDAO;
import forum.model.Post;
import forum.model.User;

public class PostDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public PostDeleteServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String postId = request.getParameter("postId");
		User user = (User) request.getSession().getAttribute("user");
		
		try {
		
		Post post = PostDAO.get(user, postId);
		
		if (!post.getFlag("canDelete")) {
			response.sendRedirect("./HomepageServlet");
			return;
		}
		
		if (post.getFlag("physicalDelete")) {
			PostDAO.deletePhysical(postId);
		} else {
			PostDAO.delete(postId);
		}
		
		response.sendRedirect("./ThreadServlet?threadId=" + post.getThread().getId());
		
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
