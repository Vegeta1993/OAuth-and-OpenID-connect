package com.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;
import com.db.User;
import com.db.Connector;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;

import antlr.Version;

/**
 * Servlet implementation class ReturnFacebook
 */
public class ReturnFacebook extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ReturnFacebook() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/**
		 * 
		 * 
		 * DO YOUR OPENID MAGIC HERE
		 * 
		 * 
		 */
		String redirectURI = "http://localhost:8080/project1/ReturnFacebook";
		String appsec = "d802b237e9816ccfbf04a850ac2040a9";
		String appId = "592725680924003";
		HttpSession httpSession = request.getSession();
		String faceCode = request.getParameter("code");
		String accesst = getFacebookAccessToken(faceCode);
		String email = getUserMailAddressFromJsonResponse(accesst, httpSession);
		String name = getNameFromJsonResponse(accesst, httpSession);

		/* ---- CHECK TO OUR DB BEGIN ---- */
		// String name = "Stewart Sentanoe"; //Change this parameter based what
		// you need
		// String email = "ss@facebook.com"; //Change this parameter based what
		// you need
		String realm = "FACEBOOK"; // Change this parameter based what you need

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
					uid = newUser.getId();
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

	private String getFacebookAccessToken(String faceCode) {
		String token = null;
		try {
			String g = "https://graph.facebook.com/oauth/access_token?client_id=592725680924003&redirect_uri="
					+ URLEncoder.encode("http://localhost:8080/project1/ReturnFacebook", "UTF-8")
					+ "&client_secret=d802b237e9816ccfbf04a850ac2040a9&code=" + faceCode;
			URL u = new URL(g);
			URLConnection c = (URLConnection) u.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String inputLine;
			StringBuffer b = new StringBuffer();
			while ((inputLine = in.readLine()) != null)
				b.append(inputLine + "\n");
			in.close();
			token = b.toString();
			if (token.startsWith("{"))
				throw new Exception("error on requesting token: " + token + " with code: " + faceCode);
		} catch (Exception e) {
			// an error occurred, handle this
			e.printStackTrace();
		}
		String newToken = token.replaceAll("^access_token=", "");
		int i = newToken.indexOf("&");
		newToken = newToken.substring(0, i);

		return newToken;
	}

	private String getUserMailAddressFromJsonResponse(String accessToken, HttpSession httpSession) {
		String graph = null;

		try {
			String g = "https://graph.facebook.com/me?fields=email&access_token=" + accessToken;
			URL u = new URL(g);
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
		}

		String email = null;
		try {
			JSONObject json = new JSONObject(graph);
			email = json.getString("email");
			return email;

		} catch (Exception e) {
			// an error occurred, handle this
			e.printStackTrace();
			return null;
		}
	}

	private String getNameFromJsonResponse(String accessToken, HttpSession httpSession) {
		String graph = null;

		try {
			String g = "https://graph.facebook.com/me?&access_token=" + accessToken;
			URL u = new URL(g);
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
		}

		String name = null;
		try {
			JSONObject json = new JSONObject(graph);
			name = json.getString("name");
			return name;

		} catch (Exception e) {
			// an error occurred, handle this
			e.printStackTrace();
			return null;
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
