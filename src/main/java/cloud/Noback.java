//This file is used to make sure back button is not used to go back to previous unclosed session.

package cloud;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Noback {
	public void noback(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		//Force cache not to stored and that every time it asks for a new cache.
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		//Backwards compatibility to HTTP 1.0
		response.setHeader("Pragma", "no-cache");

		String userName = (String) session.getAttribute("user");
		if (null == userName) {
			request.setAttribute("Error", "Session has ended.  Please login.");
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			try {
				rd.forward(request, response);
			} catch (ServletException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
