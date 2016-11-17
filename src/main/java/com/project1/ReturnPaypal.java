package com.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import com.db.Connector;
import com.db.User;
import com.nimbusds.oauth2.sdk.http.HTTPRequest;
import com.nimbusds.oauth2.sdk.http.HTTPResponse;

import sun.net.www.http.HttpClient;

/**
 * Servlet implementation class ReturnPaypal
 */
public class ReturnPaypal extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReturnPaypal() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String appId = "3fc8c836208f5da2ffa9";
		String redirectUrl = "http://localhost:8080/project1/ReturnPaypal";
		String appsec = "4bea03427df49fb2e128e5c82b10d367f8a32ae0";

		String code = request.getParameter("code");

		String returnValue = "https://github.com/login/oauth/access_token?client_id=" + appId + "&client_secret="
				+ appsec + "&code=" + code + "&redirect_uri=" + redirectUrl;
		String accesstoken = null;

		try {

			URL u = new URL(returnValue);
			URLConnection c = (URLConnection) u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			accesstoken = b.toString();
			if (accesstoken.startsWith("{"))
				throw new Exception("error on requesting token: " + accesstoken + " with code: " + code);

		}

		catch (Exception e) {
			// an error occurred, handle this
			e.printStackTrace();
		}

		String newToken = accesstoken.replaceAll("^access_token=", "");
		int i = newToken.indexOf("&");
		newToken = newToken.substring(0, i);

		accesstoken = newToken;

		String userUrl = "https://api.github.com/user?access_token=" + accesstoken;
		String graph = null;
		try {

			URL u = new URL(userUrl);
			URLConnection c = (URLConnection) u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			graph = b.toString();
		} catch (Exception e) {
			// an error occurred, handle this
			e.printStackTrace();
		}

		String email = null;
		String name = null;
		try {
			JSONObject json = new JSONObject(graph);
			name = json.getString("login");
			email = name + "@github.com";
		} catch (Exception e) {
			// an error occurred, handle this
			e.printStackTrace();
		}

		
		String realm = "GITHUB";
		int uid = 0;
		Session session = null;
		Transaction tx = null;
		try {
			Connector conn = new Connector();
			session = conn.configureSessionFactory().openSession();
			tx = session.beginTransaction();

			String hsql = "from User where email = :user_email and realm = :login_realm";
			Query query = session.createQuery(hsql);
			query.setParameter("user_email", email);
			query.setParameter("login_realm", realm);
			List<User> result = query.list();
			String yo = result.toString();
			if (result.size() != 0) {
				// USER IS EXIST, DO THE AUTHORIZATION METHOD
				for (User u : result) {
					System.out.println("Id: " + u.getId() + " | Name:" + u.getName() + " | Email:" + u.getEmail()
							+ " | Realm:" + u.getRealm());
					uid = u.getId();
					/**
					 * 
					 * 
					 * DO YOUR AUTHORIZATION MAGIC HERE
					 * 
					 * 
					 */
				}
			} else {
				// USER IS NOT EXIST, STORE IN DB
				try {
					User newUser = new User(0, name, email, realm);
					session.save(newUser);
				} catch (Exception ex) {
					ex.printStackTrace();
					tx.rollback();
				}

				/**
				 * 
				 * 
				 * DO YOUR AUTHORIZATION MAGIC HERE
				 * 
				 * 
				 */
			}
		} catch (Exception ex) {

			ex.printStackTrace();
		} finally {
			if (session != null && session.isOpen()) {
				tx.commit();
				session.flush();
				session.close();
			}
			response.sendRedirect("messageBoard.jsp?userid=" + uid);
		}
		/* ---- CHECK TO OUR DB END ---- */
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
