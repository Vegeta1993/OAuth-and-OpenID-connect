package cloud;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Noback {
	public void noback(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		response.setHeader("Cache-Control", "no-cache"); // Forces caches to
															// obtain a new copy
															// of the page from
															// the origin server
		response.setHeader("Cache-Control", "no-store"); // Directs caches not
															// to store the page
															// under any
															// circumstance
		response.setDateHeader("Expires", 0); // Causes the proxy cache to see
												// the page as "stale"
		response.setHeader("Pragma", "no-cache"); // HTTP 1.0 backward
													// compatibility
		String userName = (String) session.getAttribute("user");
		String usermail = (String) session.getAttribute("email");
		if (null == userName) {
			request.setAttribute("Error", "Session has ended.  Please login.");
			RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
