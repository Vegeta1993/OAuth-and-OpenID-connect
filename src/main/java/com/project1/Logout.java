package com.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.db.Chat;
import com.db.Connector;

import sun.net.www.http.HttpClient;

/**
 * Servlet implementation class Logout
 */
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Logout() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		String realm = session.getAttribute("realm").toString();
		if (realm.equals("google")) {
			String at = session.getAttribute("at").toString();
			String redirect_url = "http://192.168.12.16.nip.io:8080/project1/logout.jsp";
			String returnValue = "https://www.google.com/accounts/Logout?continue=https://appengine.google.com/_ah/logout?continue="
					+ redirect_url;
			try {
				response.sendRedirect(returnValue);
				return;
			} catch (Exception e) {
				// an error occurred, handle this
				e.printStackTrace();
			}
		}
		if (realm.equals("github")) {
			String clid = session.getAttribute("clid").toString();
			String returnValue1 = "https://github.com/settings/applications/440899#revoke_tokens";
			try {
				response.sendRedirect(returnValue1);
				return;
			} catch (Exception e) {
				// an error occurred, handle this
				e.printStackTrace();
			}

		}

		session.removeAttribute("realm");
		session.removeAttribute("email");
		session.removeAttribute("at");
		session.removeAttribute("user");
		session.removeAttribute("uid");
		session.invalidate();
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			cookie.setMaxAge(0);
			cookie.setValue(null);
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/index.jsp");
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
