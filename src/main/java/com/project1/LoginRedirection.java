package com.project1;

import java.io.IOException;
import java.net.URI;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class LoginRedirection
 */
public class LoginRedirection extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public LoginRedirection() 
    {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String realm = request.getParameter("direction");
		if(realm.equalsIgnoreCase("Google"))
		{
			//REDIRECT THEM TO GOOGLE
		}
		else if(realm.equalsIgnoreCase("Paypal"))
		{
			//REDIRECT THEM TO Paypal
		}
		else if(realm.equalsIgnoreCase("Facebook"))
		{
			//REDIRECT THEM TO Facebook
		}
		
		// Fake code that accepts the name of a user that is automatically authenticated
		response.getWriter().write("<html><body>"
				+ "<form method=\"get\" action=\"ReturnGoogle\">"
				+ "Username: <input type=text name=\"user\"/>"
				+ "<button id=login type=submit>Login</button>"+
				"</form></body></html>");
		response.flushBuffer();
		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	

}
